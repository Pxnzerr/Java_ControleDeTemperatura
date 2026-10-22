# Controlador de Sensor de Temperatura Industrial

Sistema Java que demonstra **encapsulamento** aplicado a um ambiente industrial, garantindo integridade e segurança do maquinário.

## Conceitos de encapsulamento

| Componente | Proteção e Recursos |
|---|---|
| `SensorTemperatura` | Leituras privadas com validação interna (-40°C a 850°C), telemetria com histórico imutável (`Collections.unmodifiableList`), consulta de leituras recentes (`getUltimasLeituras`), médias, extremos, variação térmica e tendência (`TendenciaTermica`) |
| `TendenciaTermica` | Enum de comportamento térmico dinâmico (`AQUECIMENTO`, `RESFRIAMENTO`, `ESTAVEL`) baseado no histórico de medições |
| `AlarmeIndustrial` | Estado do alarme inacessível externamente; desativação exige trava liberada e rebloqueio automático |
| `TravaSeguranca` | Credencial privada; bloqueio permanente após 3 tentativas consecutivas inválidas |
| `NivelAlerta` | Enum de classificação operacional preditiva (`NORMAL`, `ATENCAO`, `CRITICO`, `INOPERANTE`) |
| `ControladorTemperatura` | Orquestra leituras, aciona alarmes, calcula margem de segurança térmica, classifica níveis de alerta operacional, permite ajuste seguro de limite e rearme sob credencial, e gera/exporta relatório de diagnóstico estruturado |

## Estrutura

```
.
├── src/main/java/com/industrial/sensor/
│   ├── SensorTemperatura.java            # Sensor com telemetria, extremos e tendência térmica
│   ├── TendenciaTermica.java             # Enum: AQUECIMENTO, RESFRIAMENTO, ESTAVEL
│   ├── AlarmeIndustrial.java             # Alarme com controle de trava
│   ├── TravaSeguranca.java               # Trava com credencial e bloqueio por falhas
│   ├── NivelAlerta.java                  # Enum: NORMAL, ATENCAO, CRITICO, INOPERANTE
│   ├── ControladorTemperatura.java       # Orquestrador com ajuste seguro, diagnóstico e exportação
│   └── Main.java                         # Demonstração interativa no terminal
├── test/com/industrial/sensor/
│   └── ControladorTemperaturaTest.java   # Suíte de testes automatizados (25 testes)
├── run.bat                               # Script de execução (Windows)
├── run.sh                                # Script de execução (Linux/macOS)
├── test.bat                              # Script de testes (Windows)
└── test.sh                               # Script de testes (Linux/macOS)
```

## Como Executar

### 1. Executando a Aplicação Principal

**No Windows:**
```cmd
run.bat
```

**No Linux / macOS:**
```bash
chmod +x run.sh && ./run.sh
```

**Ou manualmente via terminal:**
```bash
javac -encoding UTF-8 -d out src/main/java/com/industrial/sensor/*.java
java -Dfile.encoding=UTF-8 -cp out com.industrial.sensor.Main
```

---

### 2. Executando a Suíte de Testes Automatizados

**No Windows:**
```cmd
test.bat
```

**No Linux / macOS:**
```bash
chmod +x test.sh && ./test.sh
```

**Ou manualmente via terminal:**
```bash
javac -encoding UTF-8 -d out src/main/java/com/industrial/sensor/*.java test/com/industrial/sensor/*.java
java -Dfile.encoding=UTF-8 -cp out com.industrial.sensor.ControladorTemperaturaTest
```

## Requisitos

- Java 11 ou superior

## Autor

Arthur — Projeto sobre pilares da POO.

