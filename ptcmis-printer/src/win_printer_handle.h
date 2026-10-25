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
#ifdef _WIN32
#include <windows.h>

namespace ptcmis_printer
{
    struct WinPrinterHandle
    {
        HANDLE h = nullptr;

        ~WinPrinterHandle()
        {
            if (h != nullptr)
            {
                ClosePrinter(h);
                h = nullptr;
            }
        }

        WinPrinterHandle() = default;
        WinPrinterHandle(const WinPrinterHandle&) = delete;
        WinPrinterHandle& operator=(const WinPrinterHandle&) = delete;

        WinPrinterHandle(WinPrinterHandle&& other) noexcept
        {
            h = other.h;
            other.h = nullptr;
        }

        HANDLE get() const { return h; }
        HANDLE* ptr() { return &h; }
    };
}
#endif
