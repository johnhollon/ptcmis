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

#include <iostream>
#include <string>
#include "nlohmann/json.hpp"
#include "ptcmis_printer.h"
#include "error_code.h"
#include "util_encoding.h"

using json = nlohmann::json;
using namespace ptcmis_printer;
using namespace ptcmis_printer::encoding;

int main()
{
    setup_console_utf8();

    std::ios::sync_with_stdio(false);
    std::cin.tie(nullptr);

    std::string line;
    while (std::getline(std::cin, line))
    {
        json out;
        Errc errc = ERR_OK;
        try
        {
            std::string real_input;
            if (is_stdin_console())
            {
                real_input = gbk_to_utf8(line);
            }
            else
            {
                real_input = line;
            }

            auto req = json::parse(real_input);
            std::string action = req.value("action", "");

            if (action == "get_printers")
            {
                out = getPrinters(errc);
            }
            else if (action == "get_printer_status")
            {
                std::string printerName = req["printer_name"].get<std::string>();
                out = getPrinterStatus(printerName, errc);
            }
            else if (action == "add_job")
            {
                std::string printerName = req["printer_name"].get<std::string>();
                std::string filePath = req["file_path"].get<std::string>();
                std::string jobName = req["job_name"].get<std::string>();
                int jobId = -1;
                (void)addJob(printerName, filePath, jobName, jobId, errc);
                out["job_id"] = jobId;
            }
            else
            {
                errc = ERR_UNKNOWN_ACTION;
            }
        }
        catch (const json::parse_error& /*e*/)
        {
            errc = ERR_PARSE_JSON;
        }
        catch (...)
        {
            errc = ERR_PARSE_JSON;
        }

        out["errc"] = static_cast<int>(errc);
        out["err_msg"] = errcToString(errc);
        std::cout << out.dump() << "\n";
        std::cout.flush();
    }
    return 0;
}
