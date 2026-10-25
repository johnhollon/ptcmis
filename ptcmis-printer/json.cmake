# Copyright (C) 2026 陈慧然
# (aka: Huiran Chen, chenhuiran, John Hollon, johnhollon)
# ORCID: https://orcid.org/0009-0004-5899-6928
# <chenhuiran@chenhuiran.com>
# <johnhollon@johnhollon.com>
#
# This file is part of 财神彩票.
#
# 财神彩票 is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# 财神彩票 is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
# GNU General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with this program. If not, see <https://www.gnu.org/licenses/>.


# ==============================================
# nlohmann json header-only 库配置
# 代码仓库: git@github.com:nlohmann/json.git
# tag: v3.11.3 ( @ 9cca280 )
# git submodule: third-parties-nlohmann-json
# ==============================================
set(JSON_SOURCE_DIR "${CMAKE_SOURCE_DIR}/third-parties-nlohmann-json")

if (EXISTS "${JSON_SOURCE_DIR}/CMakeLists.txt")
  message(STATUS "Found json CMakeLists.txt: ${JSON_SOURCE_DIR}")

  # ==============================================
  # 设置 json 编译选项
  # ==============================================
  # 禁用测试和示例程序
  set(JSON_BuildTests OFF CACHE BOOL "Build the unit tests" FORCE)
  # 禁用安装规则（由 install.cmake 统一管理）
  set(JSON_Install OFF CACHE BOOL "Install CMake targets" FORCE)
  # 使用多文件模式（非 amalgamated）
  set(JSON_MultipleHeaders ON CACHE BOOL "Use non-amalgamated version" FORCE)

  # ==============================================
  # 添加 json 子目录
  # ==============================================
  add_subdirectory(${JSON_SOURCE_DIR} ${CMAKE_BINARY_DIR}/json_build)

  # ==============================================
  # 导出头文件路径（供其他模块使用）
  # nlohmann_json 是 INTERFACE 库，已自动配置 include 路径
  # ==============================================
  message(STATUS "json header-only library configured (target: nlohmann_json)")
else ()
  # ==============================================
  # json CMakeLists.txt 不存在，终止配置（FATAL_ERROR）
  # ==============================================
  message(
    FATAL_ERROR
    "json CMakeLists.txt not found at: ${JSON_SOURCE_DIR}/CMakeLists.txt"
  )
endif ()