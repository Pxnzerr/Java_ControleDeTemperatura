package com.industrial.sensor;

public class ControladorTemperatura {

    private final SensorTemperatura sensor;
    private final AlarmeIndustrial alarme;
    private final TravaSeguranca trava;
    private final double limiteCritico;

    public ControladorTemperatura(SensorTemperatura sensor, AlarmeIndustrial alarme,
                                    TravaSeguranca trava, double limiteCritico) {
        if (limiteCritico <= sensor.getTemperaturaAtual()) {
            throw new IllegalArgumentException("Limite crítico deve ser superior à temperatura inicial.");
        }
        this.sensor = sensor;
        this.alarme = alarme;
        this.trava = trava;
        this.limiteCritico = limiteCritico;
    }

    public SensorTemperatura getSensor() {
        return sensor;
    }

    public AlarmeIndustrial getAlarme() {
        return alarme;
    }

    public TravaSeguranca getTrava() {
        return trava;
    }

    public double getLimiteCritico() {
        return limiteCritico;
    }

    /**
     * Processa leitura do sensor e aciona alarme quando o limite crítico é ultrapassado.
     */
    public boolean processarLeitura(double temperatura) {
        if (!sensor.registrarLeitura(temperatura)) {
            return false;
        }
        if (temperatura >= limiteCritico) {
            alarme.disparar("Temperatura crítica: " + temperatura + "°C");
            sensor.marcarInoperante();
        }
        return true;
    }

    public boolean autenticarOperador(String credencial) {
        return trava.autenticar(credencial);
    }

    public boolean silenciarAlarme() {
        return alarme.desativar();
    }
}
