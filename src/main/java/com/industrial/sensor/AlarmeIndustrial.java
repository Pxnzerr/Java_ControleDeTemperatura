package com.industrial.sensor;

public class AlarmeIndustrial {

    private final TravaSeguranca trava;
    private boolean ativo;
    private String motivo;

    public AlarmeIndustrial(TravaSeguranca trava) {
        this.trava = trava;
        this.ativo = false;
        this.motivo = "";
    }

    public boolean isAtivo() {
        return ativo;
    }

    public String getMotivo() {
        return motivo;
    }

    void disparar(String motivo) {
        this.ativo = true;
        this.motivo = motivo;
    }

    /**
     * Desativa o alarme apenas quando a trava de segurança está liberada.
     * Impede silenciamento não autorizado do maquinário.
     */
    public boolean desativar() {
        if (!trava.isLiberada()) {
            return false;
        }
        this.ativo = false;
        this.motivo = "";
        trava.bloquear();
        return true;
    }
}
