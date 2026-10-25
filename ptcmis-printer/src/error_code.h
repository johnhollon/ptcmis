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

#pragma once

namespace ptcmis_printer
{
    enum Errc : int
    {
        ERR_OK = 0,
        ERR_PARSE_JSON = 1,
        ERR_UNKNOWN_ACTION = 2,
        ERR_MISS_REQUIRED_FIELD = 3,
        ERR_PRINTER_OPEN = 4,
        ERR_FILE_OPEN = 5,
        ERR_PRINT_WRITE = 6,
        ERR_GET_PRINTER = 8,
        ERR_GET_PRINTER_STATUS = 9,
    };

    static inline const char* errcToString(Errc e)
    {
        switch (e)
        {
        case ERR_OK: return "ok";
        case ERR_PARSE_JSON: return "json parse exception";
        case ERR_UNKNOWN_ACTION: return "unknown action";
        case ERR_MISS_REQUIRED_FIELD: return "missing required field";
        case ERR_PRINTER_OPEN: return "open printer failed";
        case ERR_FILE_OPEN: return "open print file failed";
        case ERR_PRINT_WRITE: return "write to printer failed";
        case ERR_GET_PRINTER: return "get printers failed";
        case ERR_GET_PRINTER_STATUS: return "get printer status failed";
        default: return "unknown error";
        }
    }
}
