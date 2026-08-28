# Controlador de Sensor de Temperatura Industrial

Sistema Java que demonstra **encapsulamento** aplicado a um ambiente industrial, garantindo integridade e segurança do maquinário.

## Conceitos de encapsulamento

| Componente | Proteção |
|---|---|
| `SensorTemperatura` | Leituras privadas; validação interna rejeita valores fisicamente impossíveis (-40°C a 850°C) |
| `AlarmeIndustrial` | Estado do alarme inacessível externamente; desativação exige trava liberada |
| `TravaSeguranca` | Credencial privada; bloqueio após 3 tentativas inválidas |
| `ControladorTemperatura` | Orquestra leituras e alarmes sem expor estado interno crítico |

## Estrutura

```
.
├── src/main/java/com/industrial/sensor/
│   ├── SensorTemperatura.java
│   ├── AlarmeIndustrial.java
│   ├── TravaSeguranca.java
│   ├── ControladorTemperatura.java
│   └── Main.java
├── test/com/industrial/sensor/
│   └── ControladorTemperaturaTest.java   # Suíte de testes automatizados (15 testes)
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

