package com.industrial.sensor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
    private final List<Double> historicoLeituras;

    public SensorTemperatura(String identificador) {
        if (identificador == null || identificador.isBlank()) {
            throw new IllegalArgumentException("Identificador do sensor é obrigatório.");
        }
        this.identificador = identificador;
        this.temperaturaAtual = 25.0;
        this.operacional = true;
        this.historicoLeituras = new ArrayList<>();
        this.historicoLeituras.add(25.0);
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

    public static double getTemperaturaMinima() {
        return TEMPERATURA_MINIMA;
    }

    public static double getTemperaturaMaxima() {
        return TEMPERATURA_MAXIMA;
    }

    /**
     * Retorna uma cópia imutável do histórico de leituras válidas registradas.
     */
    public List<Double> getHistoricoLeituras() {
        return Collections.unmodifiableList(new ArrayList<>(historicoLeituras));
    }

    public int getTotalLeituras() {
        return historicoLeituras.size();
    }

    public double getTemperaturaMedia() {
        if (historicoLeituras.isEmpty()) {
            return 0.0;
        }
        double soma = 0.0;
        for (double t : historicoLeituras) {
            soma += t;
        }
        return soma / historicoLeituras.size();
    }

    public double getTemperaturaMaximaRegistrada() {
        if (historicoLeituras.isEmpty()) {
            return temperaturaAtual;
        }
        double max = historicoLeituras.get(0);
        for (double t : historicoLeituras) {
            if (t > max) {
                max = t;
            }
        }
        return max;
    }

    public double getTemperaturaMinimaRegistrada() {
        if (historicoLeituras.isEmpty()) {
            return temperaturaAtual;
        }
        double min = historicoLeituras.get(0);
        for (double t : historicoLeituras) {
            if (t < min) {
                min = t;
            }
        }
        return min;
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
        this.historicoLeituras.add(novaTemperatura);
        return true;
    }

    void marcarInoperante() {
        this.operacional = false;
    }

    /**
     * Restaura o sensor para o estado operacional (rearme pós-manutenção/autorização).
     */
    void rearmar() {
        this.operacional = true;
    }

    private boolean leituraValida(double temperatura) {
        return temperatura >= TEMPERATURA_MINIMA && temperatura <= TEMPERATURA_MAXIMA;
    }

    @Override
    public String toString() {
        return String.format("SensorTemperatura[id=%s, temp=%.1f°C, operacional=%s, totalLeituras=%d]",
                identificador, temperaturaAtual, operacional, historicoLeituras.size());
    }
}
