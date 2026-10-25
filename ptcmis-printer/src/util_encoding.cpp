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

#include "util_encoding.h"

#ifdef _WIN32
#include <windows.h>
#include <cstdio>
#include <io.h>
#else
#include <unistd.h>
#include <cstdio>
#endif

namespace ptcmis_printer::encoding
{
    bool is_stdin_console()
    {
#ifdef _WIN32
        return _isatty(_fileno(stdin)) != 0;
#else
        return isatty(STDIN_FILENO) != 0;
#endif
    }

    std::string gbk_to_utf8(const std::string& input)
    {
#ifdef _WIN32
        if (input.empty())
        {
            return "";
        }
        int wchar_len = MultiByteToWideChar(CP_ACP, 0, input.c_str(), -1, nullptr, 0);
        if (wchar_len <= 0)
        {
            return input;
        }
        std::wstring wbuf(wchar_len, 0);
        MultiByteToWideChar(CP_ACP, 0, input.c_str(), -1, wbuf.data(), wchar_len);

        int utf8_len = WideCharToMultiByte(CP_UTF8, 0, wbuf.c_str(), -1, nullptr, 0, nullptr, nullptr);
        if (utf8_len <= 0)
        {
            return input;
        }
        std::string out(utf8_len, 0);
        WideCharToMultiByte(CP_UTF8, 0, wbuf.c_str(), -1, out.data(), utf8_len, nullptr, nullptr);
        return out;
#else
        return input;
#endif
    }

    void setup_console_utf8()
    {
#ifdef _WIN32
        SetConsoleOutputCP(CP_UTF8);
#endif
    }
}
