package com.industrial.sensor;

import java.util.Objects;

/**
 * Trava de segurança que exige credencial para liberar operações críticas.
 */
public class TravaSeguranca {

    private final String credencialAutorizada;
    private boolean liberada;
    private int tentativasFalhas;

    public TravaSeguranca(String credencialAutorizada) {
        if (credencialAutorizada == null || credencialAutorizada.length() < 4) {
            throw new IllegalArgumentException("Credencial deve possuir ao menos 4 caracteres.");
        }
        this.credencialAutorizada = credencialAutorizada;
        this.liberada = false;
        this.tentativasFalhas = 0;
    }

    public boolean isLiberada() {
        return liberada;
    }

    /**
     * Autentica o operador. Após três falhas consecutivas, a trava bloqueia novas tentativas.
     */
    public boolean autenticar(String credencialInformada) {
        if (tentativasFalhas >= 3) {
            return false;
        }
        if (Objects.equals(credencialAutorizada, credencialInformada)) {
            liberada = true;
            tentativasFalhas = 0;
            return true;
        }
        tentativasFalhas++;
        liberada = false;
        return false;
    }

    void bloquear() {
        liberada = false;
    }

    @Override
    public String toString() {
        return String.format("TravaSeguranca[liberada=%s, tentativasFalhas=%d]", liberada, tentativasFalhas);
    }
}
