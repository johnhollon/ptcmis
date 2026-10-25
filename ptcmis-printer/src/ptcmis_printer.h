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
#include <string>
#include "nlohmann/json.hpp"
#include "error_code.h"

namespace ptcmis_printer {

    /**
     * @brief 提交打印任务（读取本地文件RAW打印）
     * @param printerName 打印机名称
     * @param filePath 待打印文件路径
     * @param jobName 打印任务名
     * @param outJobId 输出任务ID
     * @param outErrc 错误码
     * @return true成功 / false失败
     */
    bool addJob(const std::string& printerName,
                const std::string& filePath,
                const std::string& jobName,
                int& outJobId,
                Errc& outErrc);

    /**
     * @brief 获取本机打印机列表
     * @param outErrc 错误码
     * @return json {"printers":["printerA","printerB"]}
     */
    nlohmann::json getPrinters(Errc& outErrc);

    /**
     * @brief 获取指定打印机状态
     * @param printerName 打印机名称
     * @param outErrc 错误码
     * @return json 打印机状态对象
     */
    nlohmann::json getPrinterStatus(const std::string& printerName, Errc& outErrc);

}
