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
src/main/java/com/industrial/sensor/
├── SensorTemperatura.java
├── AlarmeIndustrial.java
├── TravaSeguranca.java
├── ControladorTemperatura.java
└── Main.java
```

## Executar

```bash
javac -d out src/main/java/com/industrial/sensor/*.java
java -cp out com.industrial.sensor.Main
```

## Requisitos

- Java 11 ou superior

## Autor

Arthur — Projeto sobre pilares da POO.
