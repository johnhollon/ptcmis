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
#include <cups/cups.h>
#include <cups/http.h>

namespace ptcmis_printer
{
    bool addJob(const std::string& printerName,
                const std::string& filePath,
                const std::string& jobName,
                int& outJobId,
                Errc& outErrc)
    {
        outJobId = -1;
        outErrc = ERR_OK;

        http_t* http = httpConnectEncrypt("localhost", ippPort(), HTTP_ENCRYPT_IF_REQUESTED);
        if (!http)
        {
            outErrc = ERR_PRINTER_OPEN;
            return false;
        }

        cups_option_t opt{};
        int numOpts = cupsAddOption("raw", "", 0, &opt);
        int jid = cupsPrintFile2(http, printerName.c_str(), filePath.c_str(), jobName.c_str(), numOpts, &opt);

        cupsFreeOptions(numOpts, &opt);
        httpClose(http);

        if (jid <= 0)
        {
            outErrc = ERR_PRINTER_OPEN;
            return false;
        }
        outJobId = jid;
        return true;
    }

    nlohmann::json getPrinters(Errc& outErrc)
    {
        using json = nlohmann::json;
        json res;
        res["printers"] = json::array();
        outErrc = ERR_OK;

        cups_dest_t* dests = nullptr;
        int numDest = cupsGetDests(&dests);
        for (int i = 0; i < numDest; ++i)
        {
            res["printers"].push_back(std::string(dests[i].name));
        }
        cupsFreeDests(numDest, dests);
        return res;
    }

    nlohmann::json getPrinterStatus(const std::string& printerName, Errc& outErrc)
    {
        using json = nlohmann::json;
        json res;
        outErrc = ERR_OK;

        http_t* http = httpConnectEncrypt("localhost", ippPort(), HTTP_ENCRYPT_IF_REQUESTED);
        if (!http)
        {
            outErrc = ERR_GET_PRINTER_STATUS;
            return res;
        }

        cups_dest_t* dest = cupsGetDest(printerName.c_str(), nullptr, 0, nullptr);
        if (!dest)
        {
            httpClose(http);
            outErrc = ERR_GET_PRINTER_STATUS;
            return res;
        }

        int pState = dest->printer_state;
        const char* pReasons = cupsGetOption("printer-state-reasons", dest->num_options, dest->options);

        cupsFreeDests(1, dest);
        httpClose(http);

        res["printer_state"] = pState;
        res["printer_state_reasons"] = pReasons ? std::string(pReasons) : "";
        res["queue_stopped"] = (pState == 5);
        return res;
    }
}
