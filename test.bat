@echo off
setlocal
cd /d "%~dp0"
echo [ControladorTemperatura] Compilando arquivos principais e testes...
if not exist "out" mkdir out
javac -encoding UTF-8 -d out src/main/java/com/industrial/sensor/*.java test/com/industrial/sensor/*.java
if %ERRORLEVEL% NEQ 0 (
    echo [ERRO] Falha na compilacao dos testes.
    exit /b %ERRORLEVEL%
)
echo [ControladorTemperatura] Executando suite de testes automatizados...
java -Dfile.encoding=UTF-8 -cp out com.industrial.sensor.ControladorTemperaturaTest
endlocal
