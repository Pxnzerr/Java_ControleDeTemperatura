package com.industrial.sensor;

/**
 * Níveis operacionais de alerta para monitoramento preditivo do maquinário.
 */
public enum NivelAlerta {
    NORMAL("Operação estável e dentro da faixa segura"),
    ATENCAO("Temperatura próxima ao limite crítico"),
    CRITICO("Limite crítico atingido ou superado"),
    INOPERANTE("Sensor inoperante ou desligado por segurança");

    private final String descricao;

    NivelAlerta(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
