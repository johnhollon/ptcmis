cmake_minimum_required(VERSION 3.18.1)
project(ptcmis_printer VERSION 1.0.0 LANGUAGES C CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# ==============================================
# 项目源码定义
# ==============================================
set(ptcmis_printer_SOURCES
        src/main.cpp
        src/util_encoding.cpp
)
if (WIN32)
    list(APPEND ptcmis_printer_SOURCES src/ptcmis_printer_win.cpp)
elseif (UNIX)
    list(APPEND ptcmis_printer_SOURCES src/ptcmis_printer_linux.cpp)
endif ()

# ==============================================
# 头文件搜索路径
# ==============================================
include_directories(
        ${CMAKE_SOURCE_DIR}/src
        ${CMAKE_SOURCE_DIR}/thirdparty
)

# ==============================================
# Release 全局配置：NDEBUG、符号隐藏、LTO
# ==============================================
if (CMAKE_BUILD_TYPE STREQUAL "Release")
    add_compile_definitions(NDEBUG)
    set(CMAKE_C_VISIBILITY_PRESET hidden)
    set(CMAKE_CXX_VISIBILITY_PRESET hidden)
    set(CMAKE_VISIBILITY_INLINES_HIDDEN ON)

    # MinGW 不支持 LTO，跳过
    if (NOT MINGW)
        include(CheckIPOSupported)
        check_ipo_supported(RESULT ipo_supported OUTPUT ipo_output)
        if (ipo_supported)
            message(STATUS "LTO enabled for optimized binary")
            set(CMAKE_INTERPROCEDURAL_OPTIMIZATION TRUE)
        else ()
            message(STATUS "LTO not supported: ${ipo_output}")
        endif ()
    endif ()
endif ()

# ==============================================
# 引入第三方静态库配置
# ==============================================
# nlohmann‑json 静态库配置（由 third‑parties/json.cmake 定义）
include(${CMAKE_SOURCE_DIR}/json.cmake)

# ==============================================
# 创建可执行目标
# ==============================================
add_executable(ptcmis_printer ${ptcmis_printer_SOURCES})

# Windows 7 目标版本
if (WIN32)
    set(CMAKE_SYSTEM_VERSION 6.1)
endif ()

# ==============================================
# Release 目标级编译/链接标志
# ==============================================
if (CMAKE_BUILD_TYPE STREQUAL "Release")
    if (MSVC)
        target_compile_options(ptcmis_printer PRIVATE
                /O2 /GL /GS- /Gy
                /W4 /WX- /utf-8 /Zc:__cplusplus
        )
        set_target_properties(ptcmis_printer PROPERTIES
                LINK_FLAGS "/LTCG /OPT:REF /OPT:ICF /DEBUG:NONE"
        )
        target_link_libraries(ptcmis_printer PRIVATE winspool.lib)
    else ()
        # MinGW‑GCC / Linux‑GCC
        target_compile_options(ptcmis_printer PRIVATE
                -O2 -ffunction-sections -fdata-sections
                -Wall -Wextra -Wpedantic
        )
        target_link_options(ptcmis_printer PRIVATE
                -Wl,--gc-sections -Wl,--strip-all -Wl,--as-needed
        )
        if (MINGW)
            # MinGW：静态链接 libgcc / libstdc++，生成独立单exe
            target_link_options(ptcmis_printer PRIVATE
                    -static
                    -static-libgcc
                    -static-libstdc++
            )
            target_link_libraries(ptcmis_printer PRIVATE -lwinspool)
        endif ()
    endif ()
else ()
    # Debug模式，不做静态链接，方便调试
    if (NOT MSVC)
        target_compile_options(ptcmis_printer PRIVATE -g)
    endif ()
endif ()

# ==============================================
# 链接依赖库
# ==============================================
target_link_libraries(ptcmis_printer PRIVATE nlohmann_json)

# ==============================================
# Linux CUPS 依赖
# ==============================================
if (UNIX AND NOT MINGW)
    find_package(CUPS REQUIRED)
    target_include_directories(ptcmis_printer PRIVATE ${CUPS_INCLUDE_DIRS})
    target_link_libraries(ptcmis_printer PRIVATE ${CUPS_LIBRARIES})
endif ()

# ==============================================
# 输出目录配置
# ==============================================
set_target_properties(ptcmis_printer PROPERTIES
        RUNTIME_OUTPUT_DIRECTORY_RELEASE ${CMAKE_BINARY_DIR}/bin/release
        RUNTIME_OUTPUT_DIRECTORY_DEBUG ${CMAKE_BINARY_DIR}/bin/debug
)
