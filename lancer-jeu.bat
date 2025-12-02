@echo off
cd /d "%~dp0"
cd assets
java -jar ..\lwjgl3\build\libs\BarCassonne-1.0.0-win.jar
pause

