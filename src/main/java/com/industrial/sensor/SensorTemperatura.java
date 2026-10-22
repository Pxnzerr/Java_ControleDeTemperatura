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
    private double somaLeituras;

    public SensorTemperatura(String identificador) {
        if (identificador == null || identificador.isBlank()) {
            throw new IllegalArgumentException("Identificador do sensor é obrigatório.");
        }
        this.identificador = identificador;
        this.temperaturaAtual = 25.0;
        this.operacional = true;
        this.historicoLeituras = new ArrayList<>();
        this.historicoLeituras.add(25.0);
        this.somaLeituras = 25.0;
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

    /**
     * Retorna uma lista imutável com as N últimas leituras registradas pelo sensor.
     * Se a quantidade for maior que o total disponível, retorna todo o histórico.
     * Se a quantidade for menor ou igual a zero, retorna lista vazia.
     *
     * @param quantidade número de leituras recentes solicitadas
     * @return lista imutável contendo as leituras solicitadas
     */
    public List<Double> getUltimasLeituras(int quantidade) {
        if (quantidade <= 0) {
            return Collections.emptyList();
        }
        int total = historicoLeituras.size();
        int inicio = Math.max(0, total - quantidade);
        return Collections.unmodifiableList(new ArrayList<>(historicoLeituras.subList(inicio, total)));
    }

    public int getTotalLeituras() {
        return historicoLeituras.size();
    }

    public double getTemperaturaMedia() {
        if (historicoLeituras.isEmpty()) {
            return 0.0;
        }
        return somaLeituras / historicoLeituras.size();
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
     * Retorna a variação térmica (amplitude) registrada no sensor (máxima - mínima).
     * Indicador essencial para monitoramento de estresse térmico e oscilações do maquinário.
     */
    public double getVariacaoTermica() {
        return getTemperaturaMaximaRegistrada() - getTemperaturaMinimaRegistrada();
    }

    /**
     * Determina a tendência térmica do maquinário comparando a temperatura atual
     * com a medição imediatamente anterior.
     * Retorna ESTAVEL caso haja apenas uma leitura ou oscilação menor que 0.05°C.
     */
    public TendenciaTermica getTendenciaTermica() {
        int total = historicoLeituras.size();
        if (total < 2) {
            return TendenciaTermica.ESTAVEL;
        }
        double penultima = historicoLeituras.get(total - 2);
        double diferenca = temperaturaAtual - penultima;
        if (diferenca > 0.05) {
            return TendenciaTermica.AQUECIMENTO;
        } else if (diferenca < -0.05) {
            return TendenciaTermica.RESFRIAMENTO;
        }
        return TendenciaTermica.ESTAVEL;
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
        this.somaLeituras += novaTemperatura;
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
