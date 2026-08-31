package com.industrial.sensor;

public class Main {

    public static void main(String[] args) {
        SensorTemperatura sensor = new SensorTemperatura("SNS-IND-001");
        TravaSeguranca trava = new TravaSeguranca("4821");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 120.0);

        System.out.println("=== Controlador de Sensor de Temperatura Industrial ===\n");

        demonstrarLeituraValida(controlador);
        demonstrarLeituraInvalida(controlador);
        demonstrarAlarmeSemAutenticacao(controlador);
        demonstrarAlarmeComAutenticacao(controlador);
        demonstrarRelatorioDiagnostico(controlador);
    }

    private static void demonstrarLeituraValida(ControladorTemperatura controlador) {
        System.out.println("[1] Leitura válida (85°C)");
        boolean aceita = controlador.processarLeitura(85.0);
        System.out.println("   Aceita: " + aceita);
        System.out.println("   Temperatura: " + controlador.getSensor().getTemperaturaAtual() + "°C");
        System.out.println("   Alarme: " + (controlador.getAlarme().isAtivo() ? "ATIVO" : "inativo") + "\n");
    }

    private static void demonstrarLeituraInvalida(ControladorTemperatura controlador) {
        System.out.println("[2] Leitura fisicamente impossível (9999°C)");
        boolean aceita = controlador.processarLeitura(9999.0);
        System.out.println("   Aceita: " + aceita);
        System.out.println("   Temperatura mantida: " + controlador.getSensor().getTemperaturaAtual() + "°C\n");
    }

    private static void demonstrarAlarmeSemAutenticacao(ControladorTemperatura controlador) {
        System.out.println("[3] Temperatura crítica (130°C) — alarme disparado");
        controlador.processarLeitura(130.0);
        System.out.println("   Alarme: ATIVO — " + controlador.getAlarme().getMotivo());

        System.out.println("   Tentativa de silenciar sem credencial:");
        boolean silenciado = controlador.silenciarAlarme();
        System.out.println("   Silenciado: " + silenciado);
        System.out.println("   Alarme: " + (controlador.getAlarme().isAtivo() ? "ATIVO" : "inativo") + "\n");
    }

    private static void demonstrarAlarmeComAutenticacao(ControladorTemperatura controlador) {
        System.out.println("[4] Autenticação do operador e silenciamento autorizado");
        boolean autenticado = controlador.autenticarOperador("4821");
        System.out.println("   Autenticado: " + autenticado);

        boolean silenciado = controlador.silenciarAlarme();
        System.out.println("   Silenciado: " + silenciado);
        System.out.println("   Alarme: " + (controlador.getAlarme().isAtivo() ? "ATIVO" : "inativo") + "\n");
    }

    private static void demonstrarRelatorioDiagnostico(ControladorTemperatura controlador) {
        System.out.println("[5] Relatório de Diagnóstico Industrial e Telemetria:");
        System.out.println(controlador.gerarRelatorioDiagnostico());
    }
}
