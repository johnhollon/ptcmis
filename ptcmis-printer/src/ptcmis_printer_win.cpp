// Copyright (C) 2026 陈慧然
// (aka: Huiran Chen, chenhuiran, John Hollon, johnhollon)
// ORCID: https://orcid.org/0009-0004-5899-6928
// <chenhuiran@chenhuiran.com>
// <johnhollon@johnhollon.com>
//
// This file is part of PTCMIS.
//
// PTCMIS is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// PTCMIS is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see <https://www.gnu.org/licenses/>.
//

#include "ptcmis_printer.h"
#include "error_code.h"
#include "win_printer_handle.h"
#include <windows.h>
#include <vector>
#include <fstream>
#include <cstring>

namespace ptcmis_printer
{
    static bool winStreamFileToPrinter(HANDLE hPrinter, const std::string& filePath, Errc& outErrc)
    {
        std::ifstream ifs(filePath, std::ios::binary);
        if (!ifs.is_open())
        {
            outErrc = ERR_FILE_OPEN;
            return false;
        }
        const size_t BLOCK_SIZE = 4096;
        std::vector<char> buf(BLOCK_SIZE);
        DWORD written = 0;
        while (ifs.read(buf.data(), BLOCK_SIZE), ifs.gcount() > 0)
        {
            if (!WritePrinter(hPrinter, buf.data(), static_cast<DWORD>(ifs.gcount()), &written))
            {
                outErrc = ERR_PRINT_WRITE;
                return false;
            }
        }
        outErrc = ERR_OK;
        return true;
    }

    bool addJob(const std::string& printerName,
                const std::string& filePath,
                const std::string& jobName,
                int& outJobId,
                Errc& outErrc)
    {
        outJobId = -1;
        outErrc = ERR_OK;

        WinPrinterHandle wph;
        std::vector<char> printerBuf(printerName.size() + 1);
        std::memcpy(printerBuf.data(), printerName.c_str(), printerName.size() + 1);

        if (!OpenPrinterA(printerBuf.data(), wph.ptr(), nullptr))
        {
            outErrc = ERR_PRINTER_OPEN;
            return false;
        }
        HANDLE hPrinter = wph.get();

        std::vector<char> jobNameBuf(jobName.size() + 1);
        std::memcpy(jobNameBuf.data(), jobName.c_str(), jobName.size() + 1);

        DOC_INFO_1A docInfo{};
        docInfo.pDocName = jobNameBuf.data();
        static char rawStr[] = "RAW";
        docInfo.pDatatype = rawStr;
        docInfo.pOutputFile = nullptr;

        DWORD dwJobId = StartDocPrinterA(hPrinter, 1, reinterpret_cast<LPBYTE>(&docInfo));
        if (dwJobId == 0)
        {
            outErrc = ERR_PRINTER_OPEN;
            return false;
        }

        bool ok = winStreamFileToPrinter(hPrinter, filePath, outErrc);

        EndDocPrinter(hPrinter);
        // wph 离开作用域自动 ClosePrinter

        if (!ok)
            return false;

        outJobId = static_cast<int>(dwJobId);
        return true;
    }

    nlohmann::json getPrinters(Errc& outErrc)
    {
        using json = nlohmann::json;
        json res;
        res["printers"] = json::array();
        outErrc = ERR_OK;

        DWORD bytesNeeded = 0, count = 0;
        EnumPrintersA(PRINTER_ENUM_LOCAL, nullptr, 2, nullptr, 0, &bytesNeeded, &count);
        if (bytesNeeded <= 0)
        {
            outErrc = ERR_GET_PRINTER;
            return res;
        }

        std::vector<uint8_t> buf(bytesNeeded);
        if (!EnumPrintersA(PRINTER_ENUM_LOCAL, nullptr, 2, buf.data(), bytesNeeded, &bytesNeeded, &count))
        {
            outErrc = ERR_GET_PRINTER;
            return res;
        }

        auto* pi2 = reinterpret_cast<PRINTER_INFO_2A*>(buf.data());
        for (DWORD i = 0; i < count; ++i)
        {
            res["printers"].push_back(std::string(pi2[i].pPrinterName));
        }
        return res;
    }

    nlohmann::json getPrinterStatus(const std::string& printerName, Errc& outErrc)
    {
        using json = nlohmann::json;
        json res;
        outErrc = ERR_OK;

        WinPrinterHandle wph;
        std::vector<char> printerBuf(printerName.size() + 1);
        std::memcpy(printerBuf.data(), printerName.c_str(), printerName.size() + 1);

        if (!OpenPrinterA(printerBuf.data(), wph.ptr(), nullptr))
        {
            outErrc = ERR_GET_PRINTER_STATUS;
            return res;
        }
        HANDLE hPrinter = wph.get();

        DWORD bytesNeeded = 0;
        GetPrinterA(hPrinter, 2, nullptr, 0, &bytesNeeded);
        std::vector<uint8_t> buf(bytesNeeded);

        if (!GetPrinterA(hPrinter, 2, buf.data(), bytesNeeded, &bytesNeeded))
        {
            outErrc = ERR_GET_PRINTER_STATUS;
            return res;
        }

        auto* pi2 = reinterpret_cast<PRINTER_INFO_2A*>(buf.data());
        DWORD status = pi2->Status;
        // wph 离开作用域自动 ClosePrinter

        res["printer_state"] = static_cast<int>(status);
        res["offline"] = (status & PRINTER_STATUS_OFFLINE) != 0;
        res["has_hardware_error"] = (status & (PRINTER_STATUS_ERROR | PRINTER_STATUS_PAPER_OUT)) != 0;
        return res;
    }
}
