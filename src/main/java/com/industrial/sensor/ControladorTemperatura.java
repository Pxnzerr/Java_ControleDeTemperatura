package com.industrial.sensor;

import java.util.Locale;

public class ControladorTemperatura {

    private final SensorTemperatura sensor;
    private final AlarmeIndustrial alarme;
    private final TravaSeguranca trava;
    private double limiteCritico;

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
     * Retorna a margem de segurança térmica atual em graus Celsius (°C).
     * Corresponde à diferença entre o limite crítico configurado e a temperatura atual do sensor.
     * Valores positivos indicam operação segura; zero ou negativo indicam risco/incidente iminente.
     */
    public double getMargemSeguranca() {
        return limiteCritico - sensor.getTemperaturaAtual();
    }

    /**
     * Verifica se o sistema opera dentro de uma faixa térmica estável e segura,
     * confirmando sensor operacional, alarme inativo e margem de segurança estritamente positiva.
     */
    public boolean isOperacaoSegura() {
        return sensor.isOperacional() && !alarme.isAtivo() && getMargemSeguranca() > 0.0;
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

    /**
     * Ajusta o limite crítico com segurança, exigindo autenticação do operador.
     * Retorna false se a autenticação falhar ou o novo limite for inválido.
     */
    public boolean ajustarLimiteCritico(double novoLimite, String credencial) {
        if (!trava.autenticar(credencial)) {
            return false;
        }
        if (novoLimite <= sensor.getTemperaturaAtual() || novoLimite > SensorTemperatura.getTemperaturaMaxima()) {
            trava.bloquear();
            return false;
        }
        this.limiteCritico = novoLimite;
        trava.bloquear();
        return true;
    }

    /**
     * Rearma o sensor e desativa alarmes residuais sob autenticação do operador.
     * Rejeita se a temperatura atual ainda estiver acima do limite crítico.
     */
    public boolean rearmarSistema(String credencial) {
        if (!trava.autenticar(credencial)) {
            return false;
        }
        if (sensor.getTemperaturaAtual() >= limiteCritico) {
            trava.bloquear();
            return false;
        }
        sensor.rearmar();
        alarme.desativar();
        trava.bloquear();
        return true;
    }

    /**
     * Gera um relatório estruturado de diagnóstico com métricas de telemetria e segurança.
     */
    public String gerarRelatorioDiagnostico() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== RELATÓRIO DIAGNÓSTICO DO SISTEMA ===\n");
        sb.append(String.format("Sensor: %s | Status: %s\n",
                sensor.getIdentificador(), sensor.isOperacional() ? "OPERACIONAL" : "INOPERANTE"));
        sb.append(String.format(Locale.US, "Temperatura Atual: %.1f°C | Média: %.1f°C (Total Leituras: %d)\n",
                sensor.getTemperaturaAtual(), sensor.getTemperaturaMedia(), sensor.getTotalLeituras()));
        sb.append(String.format(Locale.US, "Extremos Térmicos: Mín %.1f°C | Máx %.1f°C (Variação: %.1f°C)\n",
                sensor.getTemperaturaMinimaRegistrada(), sensor.getTemperaturaMaximaRegistrada(), sensor.getVariacaoTermica()));
        sb.append(String.format(Locale.US, "Limite Crítico Configurado: %.1f°C | Margem de Segurança: %.1f°C\n",
                limiteCritico, getMargemSeguranca()));
        sb.append(String.format("Alarme Industrial: %s%s\n",
                alarme.isAtivo() ? "ATIVO" : "OK",
                alarme.isAtivo() ? " (Motivo: " + alarme.getMotivo() + ")" : ""));
        sb.append("=========================================");
        return sb.toString();
    }

    @Override
    public String toString() {
        return String.format("ControladorTemperatura[sensor=%s, alarme=%s, limiteCritico=%.1f°C]",
                sensor.getIdentificador(), alarme.isAtivo() ? "ATIVO" : "OK", limiteCritico);
    }
}
