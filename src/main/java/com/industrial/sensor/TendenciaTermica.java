package com.industrial.sensor;

/**
 * Tendência de comportamento térmico baseada no histórico recente de leituras do sensor.
 */
public enum TendenciaTermica {
    AQUECIMENTO("Tendência de elevação de temperatura"),
    RESFRIAMENTO("Tendência de redução de temperatura"),
    ESTAVEL("Temperatura em equilíbrio estável");

    private final String descricao;

    TendenciaTermica(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
