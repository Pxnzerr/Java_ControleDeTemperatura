#!/usr/bin/env bash
set -e
DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
cd "$DIR"

echo "[ControladorTemperatura] Compilando arquivos principais e testes..."
mkdir -p out
javac -encoding UTF-8 -d out src/main/java/com/industrial/sensor/*.java test/com/industrial/sensor/*.java

echo "[ControladorTemperatura] Executando suite de testes automatizados..."
java -Dfile.encoding=UTF-8 -cp out com.industrial.sensor.ControladorTemperaturaTest
