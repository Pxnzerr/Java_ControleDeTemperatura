package com.industrial.sensor;

/**
 * Sensor industrial com leituras protegidas por validação interna.
 * Valores fora do intervalo físico do equipamento são rejeitados.
 */
public class SensorTemperatura {

    private static final double TEMPERATURA_MINIMA = -40.0;
    private static final double TEMPERATURA_MAXIMA = 850.0;

    private final String identificador;
    private double temperaturaAtual;
    private boolean operacional;

    public SensorTemperatura(String identificador) {
        if (identificador == null || identificador.isBlank()) {
            throw new IllegalArgumentException("Identificador do sensor é obrigatório.");
        }
        this.identificador = identificador;
        this.temperaturaAtual = 25.0;
        this.operacional = true;
    }

    public String getIdentificador() {
        return identificador;
    }

    public double getTemperaturaAtual() {
        return temperaturaAtual;
    }

    public boolean isOperacional() {
        return operacional;
    }

    /**
     * Registra leitura somente se estiver dentro dos limites físicos do maquinário.
     * Retorna false quando a leitura é rejeitada por segurança.
     */
    public boolean registrarLeitura(double novaTemperatura) {
        if (!operacional) {
            return false;
        }
        if (!leituraValida(novaTemperatura)) {
            return false;
        }
        this.temperaturaAtual = novaTemperatura;
        return true;
    }

    void marcarInoperante() {
        this.operacional = false;
    }

    private boolean leituraValida(double temperatura) {
        return temperatura >= TEMPERATURA_MINIMA && temperatura <= TEMPERATURA_MAXIMA;
    }
}
