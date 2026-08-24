@echo off
setlocal
cd /d "%~dp0"
echo [ControladorTemperatura] Compilando arquivos Java...
if not exist "out" mkdir out
javac -encoding UTF-8 -d out src/main/java/com/industrial/sensor/*.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha na compilacao.
    exit /b %ERRORLEVEL%
)
echo [ControladorTemperatura] Executando aplicacao...
java -Dfile.encoding=UTF-8 -cp out com.industrial.sensor.Main
endlocal
