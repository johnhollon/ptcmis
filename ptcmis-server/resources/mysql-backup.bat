@echo off
setlocal enabledelayedexpansion

:: ==========================================
::  SETTINGS
:: ==========================================
set "MYSQL_BIN=D:\ptcmis\ptcmis-db\bin"
set "MYSQL_USER=ptcmis"
set "MYSQL_PASS=ptcmis"
set "MYSQL_PORT=9007"
set "DB_NAME=ptcmis"
set "BACKUP_ROOT=D:\ptcmis\ptcmis-db\backups"
set "LOG_FILE=%BACKUP_ROOT%\backup_log.txt"
set "RETENTION_DAY=7"
set "ZIP_PATH=C:\Program Files\7-Zip\7z.exe"
set "DB_CHARSET=gb18030"

if not exist "%BACKUP_ROOT%" mkdir "%BACKUP_ROOT%"

echo ====================================== >> "%LOG_FILE%"
echo Starting BACKUP... %date% %time% >> "%LOG_FILE%"

set "HH=%time: =0%"
set "HH=%HH:~0,2%"
set "MIN=%time:~3,2%"
set "SEC=%time:~6,2%"
set "TS=%date:~0,4%%date:~5,2%%date:~8,2%_%HH%%MIN%%SEC%"

set "SQL_FILE=%BACKUP_ROOT%\%DB_NAME%_%TS%.sql"
set "ZIP_FILE=%SQL_FILE%.zip"

"%MYSQL_BIN%\mysqldump.exe" -P%MYSQL_PORT% -u%MYSQL_USER% -p%MYSQL_PASS% --default-character-set=%DB_CHARSET% --no-tablespaces %DB_NAME% --result-file="%SQL_FILE%" 2>>"%LOG_FILE%"

if %errorlevel% equ 0 (
    echo SQL EXPORTED SUCCESS: %SQL_FILE% >> "%LOG_FILE%"

    "%ZIP_PATH%" a -tzip "%ZIP_FILE%" "%SQL_FILE%" >> "%LOG_FILE%" 2>&1
    
    if !errorlevel! equ 0 (
        echo COMPRESS SUCCESS. DELETE SQL FILE... >> "%LOG_FILE%"
        del "%SQL_FILE%"
    ) else (
        echo COMPRESS FAILED. >> "%LOG_FILE%"
    )
) else (
    echo SQL EXPORTED FAILED. >> "%LOG_FILE%"
)

echo CLEAR %RETENTION_DAY% DAYS OLD BACKUP FILES >> "%LOG_FILE%"
forfiles /p "%BACKUP_ROOT%" /m *.zip /d -%RETENTION_DAY% /c "cmd /c del /f @path" >> "%LOG_FILE%" 2>&1
forfiles /p "%BACKUP_ROOT%" /m *.sql /d -%RETENTION_DAY% /c "cmd /c del /f @path" >> "%LOG_FILE%" 2>&1

echo BACKUP FINISHED. %date% %time% >> "%LOG_FILE%"
echo. >> "%LOG_FILE%"
endlocal