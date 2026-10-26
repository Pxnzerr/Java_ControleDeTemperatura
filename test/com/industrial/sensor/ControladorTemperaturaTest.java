package com.industrial.sensor;

import java.util.List;
import java.util.Objects;

/**
 * Suíte de Testes Automatizados para o Controlador de Sensor de Temperatura Industrial.
 * Valida 100% das regras de encapsulamento, integridade física, alarmes e travas de segurança.
 */
public class ControladorTemperaturaTest {

    private static int testesExecutados = 0;
    private static int testesPassaram = 0;
    private static int testesFalharam = 0;

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("   SUITE DE TESTES: CONTROLADOR DE SENSOR DE TEMPERATURA");
        System.out.println("==================================================================\n");

        executarTeste("Sensor: Criação e valores iniciais padrão", ControladorTemperaturaTest::testSensorCriacaoEValoresIniciais);
        executarTeste("Sensor: Rejeição de identificador nulo ou vazio", ControladorTemperaturaTest::testSensorValidacaoIdentificador);
        executarTeste("Sensor: Registro de leitura válida em diferentes faixas", ControladorTemperaturaTest::testSensorRegistroLeituraValida);
        executarTeste("Sensor: Rejeição de leituras fora dos limites físicos (-40°C a 850°C)", ControladorTemperaturaTest::testSensorRejeicaoLeituraForaDosLimites);
        executarTeste("Sensor: Inoperante rejeita novas leituras", ControladorTemperaturaTest::testSensorInoperanteRejeitaLeituras);
        executarTeste("Trava: Validação de tamanho mínimo da credencial", ControladorTemperaturaTest::testTravaSegurancaValidacaoCredencial);
        executarTeste("Trava: Autenticação bem-sucedida", ControladorTemperaturaTest::testTravaSegurancaAutenticacaoSucesso);
        executarTeste("Trava: Bloqueio permanente após 3 tentativas inválidas consecutivas", ControladorTemperaturaTest::testTravaSegurancaBloqueioAposTresFalhas);
        executarTeste("Alarme: Disparo e impedimento de silenciamento sem trava liberada", ControladorTemperaturaTest::testAlarmeDisparoEBloqueioSilenciamento);
        executarTeste("Alarme: Silenciamento autorizado com trava liberada e rebloqueio automático", ControladorTemperaturaTest::testAlarmeSilenciamentoComTrava);
        executarTeste("Controlador: Processamento normal de leituras dentro da faixa segura", ControladorTemperaturaTest::testControladorProcessamentoNormal);
        executarTeste("Controlador: Disparo de alarme crítico e desativação do sensor", ControladorTemperaturaTest::testControladorDisparoCritico);
        executarTeste("Controlador: Fluxo completo de autenticação do operador e silenciamento", ControladorTemperaturaTest::testControladorFluxoAutenticacaoESilenciamento);
        executarTeste("Controlador: Validação de limite crítico superior à temperatura inicial", ControladorTemperaturaTest::testControladorValidacaoLimiteCritico);
        executarTeste("Controlador: Ajuste seguro de limite crítico com credencial", ControladorTemperaturaTest::testControladorAjusteSeguroLimiteCritico);
        executarTeste("Controlador: Rearme do sistema e restauração operacional pós-incidente", ControladorTemperaturaTest::testControladorRearmeSistema);
        executarTeste("Controlador: Geração de relatório de diagnóstico estruturado", ControladorTemperaturaTest::testControladorRelatorioDiagnostico);
        executarTeste("Sensor: Telemetria histórica, médias e imutabilidade de leituras", ControladorTemperaturaTest::testSensorHistoricoEEstatisticasTermicas);
        executarTeste("Sensor: Variação térmica (amplitude) e cálculo de oscilações", ControladorTemperaturaTest::testSensorVariacaoTermica);
        executarTeste("Sensor: Detecção dinâmica de tendência térmica (aquecimento, resfriamento, estável)", ControladorTemperaturaTest::testSensorTendenciaTermica);
        executarTeste("Controlador: Margem de segurança térmica e status de operação segura", ControladorTemperaturaTest::testControladorMargemSegurancaEOperacaoSegura);
        executarTeste("Controlador: Classificação preditiva de níveis de alerta operacional", ControladorTemperaturaTest::testControladorNivelAlerta);
        executarTeste("Controlador: Exportação do relatório de diagnóstico para arquivo", ControladorTemperaturaTest::testControladorExportacaoRelatorio);
        executarTeste("Sensor: Consulta de últimas N leituras e imutabilidade de telemetria", ControladorTemperaturaTest::testSensorUltimasLeituras);
        executarTeste("Modelos: Representação textual toString() informativa", ControladorTemperaturaTest::testModelosToString);

        System.out.println("\n==================================================================");
        System.out.printf("   RESULTADO: %d Executados | %d Passaram | %d Falharam%n",
                testesExecutados, testesPassaram, testesFalharam);
        System.out.println("==================================================================");

        if (testesFalharam > 0) {
            System.exit(1);
        }
    }

    private static void executarTeste(String descricao, Runnable teste) {
        testesExecutados++;
        try {
            teste.run();
            testesPassaram++;
            System.out.printf("  [OK] %s%n", descricao);
        } catch (AssertionError | Exception e) {
            testesFalharam++;
            System.out.printf("  [FALHA] %s -> %s%n", descricao, e.getMessage());
        }
    }

    private static void assertTrue(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError(mensagem);
        }
    }

    private static void assertFalse(boolean condicao, String mensagem) {
        if (condicao) {
            throw new AssertionError(mensagem);
        }
    }

    private static void assertEquals(Object esperado, Object atual, String mensagem) {
        if (!Objects.equals(esperado, atual)) {
            throw new AssertionError(String.format("%s [Esperado: %s, Atual: %s]", mensagem, esperado, atual));
        }
    }

    private static void assertEqualsDouble(double esperado, double atual, double delta, String mensagem) {
        if (Math.abs(esperado - atual) > delta) {
            throw new AssertionError(String.format("%s [Esperado: %.2f, Atual: %.2f]", mensagem, esperado, atual));
        }
    }

    // --- TESTES DE UNIDADE ---

    private static void testSensorCriacaoEValoresIniciais() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-001");
        assertEquals("SNS-001", sensor.getIdentificador(), "Identificador deve corresponder ao informado");
        assertEqualsDouble(25.0, sensor.getTemperaturaAtual(), 0.001, "Temperatura inicial padrão deve ser 25.0°C");
        assertTrue(sensor.isOperacional(), "Sensor recém-criado deve estar operacional");
        assertEqualsDouble(-40.0, SensorTemperatura.getTemperaturaMinima(), 0.001, "Temperatura mínima deve ser -40°C");
        assertEqualsDouble(850.0, SensorTemperatura.getTemperaturaMaxima(), 0.001, "Temperatura máxima deve ser 850°C");
    }

    private static void testSensorValidacaoIdentificador() {
        boolean excecaoNulo = false;
        try {
            new SensorTemperatura(null);
        } catch (IllegalArgumentException e) {
            excecaoNulo = true;
        }
        assertTrue(excecaoNulo, "Deve rejeitar identificador nulo com IllegalArgumentException");

        boolean excecaoVazio = false;
        try {
            new SensorTemperatura("   ");
        } catch (IllegalArgumentException e) {
            excecaoVazio = true;
        }
        assertTrue(excecaoVazio, "Deve rejeitar identificador vazio com IllegalArgumentException");
    }

    private static void testSensorRegistroLeituraValida() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-002");

        assertTrue(sensor.registrarLeitura(-40.0), "Limite inferior (-40°C) deve ser aceito");
        assertEqualsDouble(-40.0, sensor.getTemperaturaAtual(), 0.001, "Temperatura atual deve ser -40°C");

        assertTrue(sensor.registrarLeitura(850.0), "Limite superior (850°C) deve ser aceito");
        assertEqualsDouble(850.0, sensor.getTemperaturaAtual(), 0.001, "Temperatura atual deve ser 850°C");

        assertTrue(sensor.registrarLeitura(150.5), "Leitura intermediária (150.5°C) deve ser aceita");
        assertEqualsDouble(150.5, sensor.getTemperaturaAtual(), 0.001, "Temperatura atual deve ser 150.5°C");
    }

    private static void testSensorRejeicaoLeituraForaDosLimites() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-003");
        sensor.registrarLeitura(50.0);

        assertFalse(sensor.registrarLeitura(-40.1), "Leitura abaixo do limite físico (-40.1°C) deve ser rejeitada");
        assertEqualsDouble(50.0, sensor.getTemperaturaAtual(), 0.001, "Temperatura deve ser mantida inalterada");

        assertFalse(sensor.registrarLeitura(850.1), "Leitura acima do limite físico (850.1°C) deve ser rejeitada");
        assertEqualsDouble(50.0, sensor.getTemperaturaAtual(), 0.001, "Temperatura deve ser mantida inalterada");

        assertFalse(sensor.registrarLeitura(9999.0), "Leitura extrema (9999°C) deve ser rejeitada");
        assertEqualsDouble(50.0, sensor.getTemperaturaAtual(), 0.001, "Temperatura deve ser mantida inalterada");
    }

    private static void testSensorInoperanteRejeitaLeituras() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-004");
        sensor.marcarInoperante();

        assertFalse(sensor.isOperacional(), "Sensor deve estar inoperante");
        assertFalse(sensor.registrarLeitura(30.0), "Sensor inoperante não deve aceitar novas leituras");
        assertEqualsDouble(25.0, sensor.getTemperaturaAtual(), 0.001, "Temperatura não deve ser alterada");
    }

    private static void testTravaSegurancaValidacaoCredencial() {
        boolean excecaoNulo = false;
        try {
            new TravaSeguranca(null);
        } catch (IllegalArgumentException e) {
            excecaoNulo = true;
        }
        assertTrue(excecaoNulo, "Deve rejeitar credencial nula");

        boolean excecaoCurta = false;
        try {
            new TravaSeguranca("123");
        } catch (IllegalArgumentException e) {
            excecaoCurta = true;
        }
        assertTrue(excecaoCurta, "Deve rejeitar credencial com menos de 4 caracteres");
    }

    private static void testTravaSegurancaAutenticacaoSucesso() {
        TravaSeguranca trava = new TravaSeguranca("AUTH-99");
        assertFalse(trava.isLiberada(), "Trava deve iniciar bloqueada");

        boolean autenticado = trava.autenticar("AUTH-99");
        assertTrue(autenticado, "Autenticação com credencial correta deve retornar true");
        assertTrue(trava.isLiberada(), "Trava deve ficar liberada após autenticação");
    }

    private static void testTravaSegurancaBloqueioAposTresFalhas() {
        TravaSeguranca trava = new TravaSeguranca("SENHA-CORRETA");

        assertFalse(trava.autenticar("ERRADA_1"), "1ª tentativa inválida deve falhar");
        assertFalse(trava.isLiberada(), "Trava não deve ser liberada");

        assertFalse(trava.autenticar("ERRADA_2"), "2ª tentativa inválida deve falhar");
        assertFalse(trava.isLiberada(), "Trava não deve ser liberada");

        assertFalse(trava.autenticar("ERRADA_3"), "3ª tentativa inválida deve falhar");
        assertFalse(trava.isLiberada(), "Trava não deve ser liberada");

        // 4ª tentativa mesmo com a credencial correta DEVE FALHAR devido ao bloqueio
        assertFalse(trava.autenticar("SENHA-CORRETA"), "Tentativa após 3 falhas deve ser bloqueada permanentemente");
        assertFalse(trava.isLiberada(), "Trava deve permanecer bloqueada");
    }

    private static void testAlarmeDisparoEBloqueioSilenciamento() {
        TravaSeguranca trava = new TravaSeguranca("5544");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);

        assertFalse(alarme.isAtivo(), "Alarme deve iniciar inativo");
        alarme.disparar("Superaquecimento");
        assertTrue(alarme.isAtivo(), "Alarme deve estar ativo após disparo");
        assertEquals("Superaquecimento", alarme.getMotivo(), "Motivo do alarme deve corresponder");

        // Tentativa de silenciar sem destravar a trava de segurança
        boolean silenciado = alarme.desativar();
        assertFalse(silenciado, "Não deve ser possível silenciar o alarme sem liberar a trava de segurança");
        assertTrue(alarme.isAtivo(), "Alarme deve continuar ativo");
    }

    private static void testAlarmeSilenciamentoComTrava() {
        TravaSeguranca trava = new TravaSeguranca("5544");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        alarme.disparar("Perigo térmico");

        trava.autenticar("5544");
        assertTrue(trava.isLiberada(), "Trava deve estar liberada para silenciamento");

        boolean silenciado = alarme.desativar();
        assertTrue(silenciado, "Silenciamento autorizado deve retornar true");
        assertFalse(alarme.isAtivo(), "Alarme deve estar inativo após desativação");
        assertFalse(trava.isLiberada(), "Trava deve ser automaticamente rebloqueada após o silenciamento");
    }

    private static void testControladorProcessamentoNormal() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-NORM");
        TravaSeguranca trava = new TravaSeguranca("8888");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 100.0);

        boolean processado = controlador.processarLeitura(75.0);
        assertTrue(processado, "Leitura dentro do limite seguro deve ser processada com sucesso");
        assertEqualsDouble(75.0, controlador.getSensor().getTemperaturaAtual(), 0.001, "Temperatura do sensor deve atualizar");
        assertFalse(controlador.getAlarme().isAtivo(), "Alarme não deve ser disparado em temperatura segura");
        assertTrue(controlador.getSensor().isOperacional(), "Sensor deve permanecer operacional");
    }

    private static void testControladorDisparoCritico() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-CRIT");
        TravaSeguranca trava = new TravaSeguranca("8888");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 100.0);

        boolean processado = controlador.processarLeitura(100.0);
        assertTrue(processado, "Leitura crítica deve ser registrada");
        assertTrue(controlador.getAlarme().isAtivo(), "Alarme deve ser ativado imediatamente ao atingir o limite crítico");
        assertFalse(controlador.getSensor().isOperacional(), "Sensor deve ser marcado como inoperante por segurança");
    }

    private static void testControladorFluxoAutenticacaoESilenciamento() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-FLOW");
        TravaSeguranca trava = new TravaSeguranca("4821");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 120.0);

        controlador.processarLeitura(135.0);
        assertTrue(controlador.getAlarme().isAtivo(), "Alarme deve estar ativo");

        // Silenciar sem autenticar falha
        assertFalse(controlador.silenciarAlarme(), "Silenciamento sem credencial deve ser rejeitado");
        assertTrue(controlador.getAlarme().isAtivo(), "Alarme continua ativo");

        // Autentica operador e silencia com sucesso
        assertTrue(controlador.autenticarOperador("4821"), "Autenticação do operador deve ser bem-sucedida");
        assertTrue(controlador.silenciarAlarme(), "Silenciamento após autenticação deve ter sucesso");
        assertFalse(controlador.getAlarme().isAtivo(), "Alarme deve estar desligado");
    }

    private static void testControladorValidacaoLimiteCritico() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-VAL"); // temp inicial = 25.0°C
        TravaSeguranca trava = new TravaSeguranca("1234");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);

        boolean excecaoLancada = false;
        try {
            new ControladorTemperatura(sensor, alarme, trava, 25.0);
        } catch (IllegalArgumentException e) {
            excecaoLancada = true;
        }
        assertTrue(excecaoLancada, "Deve rejeitar limite crítico menor ou igual à temperatura inicial");
    }

    private static void testSensorHistoricoEEstatisticasTermicas() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-HIST");
        assertEquals(1, sensor.getTotalLeituras(), "Sensor deve iniciar com 1 leitura registrada (temperatura inicial)");
        assertEqualsDouble(25.0, sensor.getTemperaturaMedia(), 0.001, "Média inicial deve ser 25.0°C");

        sensor.registrarLeitura(35.0);
        sensor.registrarLeitura(45.0);
        sensor.registrarLeitura(15.0);

        sensor.registrarLeitura(20.0);
        assertEquals(5, sensor.getTotalLeituras(), "Total de leituras válidas deve ser 5");
        assertEqualsDouble(15.0, sensor.getTemperaturaMinimaRegistrada(), 0.001, "Mínima mantida em 15.0°C");
        assertEqualsDouble(45.0, sensor.getTemperaturaMaximaRegistrada(), 0.001, "Máxima mantida em 45.0°C");
        assertEqualsDouble(28.0, sensor.getTemperaturaMedia(), 0.001, "Média deve ser (25+35+45+15+20)/5 = 28.0°C");

        // Validação de encapsulamento e imutabilidade da lista de histórico
        boolean modificacaoBloqueada = false;
        try {
            sensor.getHistoricoLeituras().add(999.0);
        } catch (UnsupportedOperationException e) {
            modificacaoBloqueada = true;
        }
        assertTrue(modificacaoBloqueada, "Histórico retornado pelo sensor deve ser imutável para proteger o encapsulamento");
    }

    private static void testControladorAjusteSeguroLimiteCritico() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-AJUSTE");
        TravaSeguranca trava = new TravaSeguranca("7777");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 100.0);

        // Credencial inválida deve falhar
        boolean ajustadoInvalido = controlador.ajustarLimiteCritico(150.0, "0000");
        assertFalse(ajustadoInvalido, "Ajuste com credencial errada deve ser rejeitado");
        assertEqualsDouble(100.0, controlador.getLimiteCritico(), 0.001, "Limite crítico não deve ter mudado");

        // Limite menor ou igual à temperatura atual deve falhar
        boolean ajustadoAbaixo = controlador.ajustarLimiteCritico(20.0, "7777");
        assertFalse(ajustadoAbaixo, "Ajuste com limite inferior à temperatura atual deve ser rejeitado");

        // Ajuste válido com credencial correta
        boolean ajustadoSucesso = controlador.ajustarLimiteCritico(180.0, "7777");
        assertTrue(ajustadoSucesso, "Ajuste válido com credencial correta deve ter sucesso");
        assertEqualsDouble(180.0, controlador.getLimiteCritico(), 0.001, "Limite crítico deve ser atualizado para 180.0°C");
        assertFalse(controlador.getTrava().isLiberada(), "Trava de segurança deve permanecer bloqueada após a operação");
    }

    private static void testControladorRearmeSistema() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-REARME");
        TravaSeguranca trava = new TravaSeguranca("3333");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 90.0);

        controlador.processarLeitura(95.0); // Dispara alarme e inativa sensor
        assertTrue(controlador.getAlarme().isAtivo(), "Alarme deve estar ativo");
        assertFalse(controlador.getSensor().isOperacional(), "Sensor deve estar inoperante");

        // Rearme sem autenticação deve falhar
        assertFalse(controlador.rearmarSistema("errada"), "Rearme sem autenticação deve falhar");

        // Rearme com temperatura ainda crítica deve ser impedido por segurança
        assertFalse(controlador.rearmarSistema("3333"), "Rearme com temperatura acima do limite crítico deve ser bloqueado");

        // Ajustando o limite acima da temperatura atual para permitir rearme seguro
        controlador.ajustarLimiteCritico(120.0, "3333");
        boolean rearmado = controlador.rearmarSistema("3333");
        assertTrue(rearmado, "Rearme com condições seguras e credencial válida deve ter sucesso");
        assertTrue(controlador.getSensor().isOperacional(), "Sensor deve voltar ao estado operacional");
        assertFalse(controlador.getAlarme().isAtivo(), "Alarme deve ser desativado após o rearme");
    }

    private static void testControladorRelatorioDiagnostico() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-DIAG");
        TravaSeguranca trava = new TravaSeguranca("1234");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 100.0);

        controlador.processarLeitura(40.0);
        controlador.processarLeitura(60.0);

        String relatorio = controlador.gerarRelatorioDiagnostico();
        assertTrue(relatorio.contains("RELATÓRIO DIAGNÓSTICO DO SISTEMA"), "Relatório deve conter cabeçalho");
        assertTrue(relatorio.contains("SNS-DIAG"), "Relatório deve conter identificador do sensor");
        assertTrue(relatorio.contains("OPERACIONAL"), "Relatório deve conter status operacional");
        assertTrue(relatorio.contains("60.0°C"), "Relatório deve conter temperatura atual");
        assertTrue(relatorio.contains("Variação: 35.0°C"), "Relatório deve conter variação térmica calculada");
        assertTrue(relatorio.contains("Margem de Segurança: 40.0°C"), "Relatório deve conter margem de segurança térmica");
        assertTrue(relatorio.contains("Nível de Alerta: NORMAL"), "Relatório deve conter o nível de alerta operacional");
        assertTrue(relatorio.contains("Tendência Térmica: AQUECIMENTO"), "Relatório deve conter a tendência térmica recente");
    }

    private static void testSensorTendenciaTermica() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-TREND");
        // Inicial com apenas 1 leitura (25.0°C) -> ESTAVEL
        assertEquals(TendenciaTermica.ESTAVEL, sensor.getTendenciaTermica(), "Com 1 leitura, tendência deve ser ESTAVEL");

        // Nova leitura subindo para 40.0°C -> AQUECIMENTO
        sensor.registrarLeitura(40.0);
        assertEquals(TendenciaTermica.AQUECIMENTO, sensor.getTendenciaTermica(), "Subida de temperatura deve indicar AQUECIMENTO");

        // Nova leitura caindo para 30.0°C -> RESFRIAMENTO
        sensor.registrarLeitura(30.0);
        assertEquals(TendenciaTermica.RESFRIAMENTO, sensor.getTendenciaTermica(), "Queda de temperatura deve indicar RESFRIAMENTO");

        // Oscilação desprezível (< 0.05°C) -> ESTAVEL
        sensor.registrarLeitura(30.02);
        assertEquals(TendenciaTermica.ESTAVEL, sensor.getTendenciaTermica(), "Oscilação desprezível deve indicar ESTAVEL");
    }

    private static void testControladorNivelAlerta() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-ALERTA");
        TravaSeguranca trava = new TravaSeguranca("1234");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 100.0);

        // Inicial: 25.0°C < 80.0°C (80% de 100.0) -> NORMAL
        assertEquals(NivelAlerta.NORMAL, controlador.getNivelAlerta(), "Temperatura segura inicial deve ter nível NORMAL");

        // 80.0°C >= 80% do limite -> ATENCAO
        controlador.processarLeitura(80.0);
        assertEquals(NivelAlerta.ATENCAO, controlador.getNivelAlerta(), "Temperatura atingindo 80% do limite deve ter nível ATENCAO");

        // 100.0°C >= limite crítico -> CRITICO (alarme ativo)
        controlador.processarLeitura(100.0);
        assertEquals(NivelAlerta.CRITICO, controlador.getNivelAlerta(), "Alarme ativo por superaquecimento deve indicar CRITICO");

        // Silenciando alarme mantendo sensor inoperante -> INOPERANTE
        controlador.autenticarOperador("1234");
        controlador.silenciarAlarme();
        assertEquals(NivelAlerta.INOPERANTE, controlador.getNivelAlerta(), "Sensor inoperante com alarme desativado deve indicar INOPERANTE");
    }

    private static void testSensorVariacaoTermica() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-VAR");
        assertEqualsDouble(0.0, sensor.getVariacaoTermica(), 0.001, "Com apenas leitura inicial, variação deve ser 0.0°C");

        sensor.registrarLeitura(60.0);
        sensor.registrarLeitura(10.0);
        // Leituras: 25.0, 60.0, 10.0 -> Máx = 60.0, Mín = 10.0 -> Variação = 50.0
        assertEqualsDouble(50.0, sensor.getVariacaoTermica(), 0.001, "Variação deve ser a diferença entre máxima e mínima (60.0 - 10.0 = 50.0°C)");
    }

    private static void testControladorMargemSegurancaEOperacaoSegura() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-MARGEM");
        TravaSeguranca trava = new TravaSeguranca("6543");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 100.0);

        // Temp inicial = 25.0, Limite = 100.0 -> Margem = 75.0
        assertEqualsDouble(75.0, controlador.getMargemSeguranca(), 0.001, "Margem de segurança inicial deve ser 75.0°C");
        assertTrue(controlador.isOperacaoSegura(), "Operação inicial deve ser segura");

        // Processa leitura dentro da margem
        controlador.processarLeitura(80.0);
        assertEqualsDouble(20.0, controlador.getMargemSeguranca(), 0.001, "Margem deve ser atualizada para 20.0°C");
        assertTrue(controlador.isOperacaoSegura(), "Operação deve continuar segura");

        // Ultrapassa limite crítico
        controlador.processarLeitura(105.0);
        assertEqualsDouble(-5.0, controlador.getMargemSeguranca(), 0.001, "Margem deve ser negativa (-5.0°C) em situação crítica");
        assertFalse(controlador.isOperacaoSegura(), "Operação não deve ser segura com alarme ativo e limite excedido");
    }

    private static void testControladorExportacaoRelatorio() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-EXP");
        TravaSeguranca trava = new TravaSeguranca("1234");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 110.0);

        controlador.processarLeitura(55.0);

        // Validação de caminho nulo
        boolean erroCaminhoNulo = false;
        try {
            controlador.exportarRelatorio(null);
        } catch (IllegalArgumentException e) {
            erroCaminhoNulo = true;
        } catch (Exception e) {
            // outras exceções
        }
        assertTrue(erroCaminhoNulo, "Deve lançar IllegalArgumentException para caminho nulo");

        // Gravação em arquivo temporário e validação de conteúdo
        try {
            java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("diag_test_", ".txt");
            try {
                controlador.exportarRelatorio(tempFile);
                assertTrue(java.nio.file.Files.exists(tempFile), "Arquivo de relatório exportado deve existir");
                String conteudo = java.nio.file.Files.readString(tempFile, java.nio.charset.StandardCharsets.UTF_8);
                assertTrue(conteudo.contains("SNS-EXP"), "Arquivo deve conter o identificador do sensor");
                assertTrue(conteudo.contains("55.0°C"), "Arquivo deve conter a temperatura registrada");
                assertTrue(conteudo.contains("RELATÓRIO DIAGNÓSTICO DO SISTEMA"), "Arquivo deve conter o cabeçalho do relatório");
                assertTrue(conteudo.contains("Nível de Alerta:"), "Arquivo deve conter o nível de alerta");
                assertTrue(conteudo.contains("Tendência Térmica:"), "Arquivo deve conter a tendência térmica");
            } finally {
                java.nio.file.Files.deleteIfExists(tempFile);
            }
        } catch (java.io.IOException e) {
            throw new AssertionError("Falha inesperada de I/O no teste de exportação: " + e.getMessage());
        }
    }

    private static void testSensorUltimasLeituras() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-RECENT");
        TravaSeguranca trava = new TravaSeguranca("1234");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 100.0);

        // Inicial: apenas leitura inicial 25.0°C
        assertEquals(1, sensor.getUltimasLeituras(5).size(), "Com apenas 1 leitura, deve retornar 1 elemento");
        assertEqualsDouble(25.0, sensor.getUltimasLeituras(1).get(0), 0.001, "A leitura deve ser 25.0°C");

        // Quantidade <= 0 retorna lista vazia
        assertTrue(sensor.getUltimasLeituras(0).isEmpty(), "Quantidade 0 deve retornar lista vazia");
        assertTrue(sensor.getUltimasLeituras(-3).isEmpty(), "Quantidade negativa deve retornar lista vazia");

        // Adiciona mais leituras
        sensor.registrarLeitura(30.0);
        sensor.registrarLeitura(40.0);
        sensor.registrarLeitura(50.0);
        // Histórico: [25.0, 30.0, 40.0, 50.0]

        // Obter últimas 2 leituras
        List<Double> ultimas2 = sensor.getUltimasLeituras(2);
        assertEquals(2, ultimas2.size(), "Deve retornar exatamente 2 leituras");
        assertEqualsDouble(40.0, ultimas2.get(0), 0.001, "Penúltima leitura deve ser 40.0°C");
        assertEqualsDouble(50.0, ultimas2.get(1), 0.001, "Última leitura deve ser 50.0°C");

        // Obter via ControladorTemperatura
        List<Double> recentesControlador = controlador.obterLeiturasRecentes(3);
        assertEquals(3, recentesControlador.size(), "Controlador deve repassar as 3 leituras");
        assertEqualsDouble(30.0, recentesControlador.get(0), 0.001, "Primeira das 3 deve ser 30.0°C");

        // Validar imutabilidade da lista retornada
        boolean bloqueouModificacao = false;
        try {
            ultimas2.add(99.0);
        } catch (UnsupportedOperationException e) {
            bloqueouModificacao = true;
        }
        assertTrue(bloqueouModificacao, "Lista de últimas leituras deve ser imutável");
    }

    private static void testModelosToString() {
        SensorTemperatura sensor = new SensorTemperatura("SNS-TST");
        TravaSeguranca trava = new TravaSeguranca("9999");
        AlarmeIndustrial alarme = new AlarmeIndustrial(trava);
        ControladorTemperatura controlador = new ControladorTemperatura(sensor, alarme, trava, 150.0);

        assertTrue(sensor.toString().contains("SNS-TST"), "toString do sensor deve conter o identificador");
        assertTrue(trava.toString().contains("TravaSeguranca"), "toString da trava deve conter nome da classe");
        assertTrue(alarme.toString().contains("AlarmeIndustrial"), "toString do alarme deve conter nome da classe");
        assertTrue(controlador.toString().contains("ControladorTemperatura"), "toString do controlador deve conter nome da classe");
    }
}
