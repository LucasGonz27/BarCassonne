@echo off
call gradlew.bat test
if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%

call gradlew.bat jacocoTestReport
if %ERRORLEVEL% NEQ 0 exit /b %ERRORLEVEL%

if exist "core\build\reports\jacoco\test\html\index.html" (
    start "" "core\build\reports\jacoco\test\html\index.html"
)

