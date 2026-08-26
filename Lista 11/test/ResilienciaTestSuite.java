package br.com.nexustech.test;

import br.com.nexustech.application.service.CofreGabaritoService;
import br.com.nexustech.application.service.RedeService;
import br.com.nexustech.domain.exception.BanidoException;
import br.com.nexustech.domain.exception.NivelInsuficienteException;
import br.com.nexustech.domain.model.EstatisticasCombate;
import br.com.nexustech.domain.model.Inventario;
import br.com.nexustech.domain.model.Jogador;
import br.com.nexustech.domain.model.Masmorra;
import br.com.nexustech.domain.service.Matchmaker;
import br.com.nexustech.domain.service.ModoCasual;
import br.com.nexustech.domain.service.ModoJogo;
import br.com.nexustech.domain.service.ModoRanqueado;
import br.com.nexustech.infrastructure.crypto.AesPbkdf2Decryptor;
import br.com.nexustech.infrastructure.crypto.AesPbkdf2Decryptor.ResultadoDecriptacao;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * Suíte de Testes Automatizados para verificação rigorosa de todos os exercícios da Lista 11.
 * Executável nativamente sem necessidade de bibliotecas externas.
 */
public class ResilienciaTestSuite {

    private static int totalTestes = 0;
    private static int testesAprovados = 0;
    private static int testesReprovados = 0;

    public static void main(String[] args) {
        System.out.println("========================================================");
        System.out.println("   INICIANDO BATERIA DE TESTES - PROTOCOLO DE RESILIÊNCIA");
        System.out.println("========================================================");

        // Nível 1
        executarTeste("Ex 1: Divisão por Zero lança ArithmeticException", ResilienciaTestSuite::testEx1_DivisaoPorZeroLancaArithmeticException);
        executarTeste("Ex 2: Tratamento de K/D para Jogador Invicto", ResilienciaTestSuite::testEx2_TratamentoKdJogadorInvicto);
        executarTeste("Ex 2: Cálculo Normal de K/D", ResilienciaTestSuite::testEx2_CalculoNormalKd);
        executarTeste("Ex 3: Acesso fora do índice do Inventário lança ArrayIndexOutOfBoundsException", ResilienciaTestSuite::testEx3_AcessoForaIndiceLancaException);
        executarTeste("Ex 3: Inserção resiliente no Inventário", ResilienciaTestSuite::testEx3_InsercaoResilienteInventario);
        executarTeste("Ex 3: Inserção válida em slots normais do Inventário", ResilienciaTestSuite::testEx3_InsercaoValidaSlots);
        executarTeste("Ex 4: Programação Defensiva para Jogador Nulo", ResilienciaTestSuite::testEx4_ProgramacaoDefensivaJogadorNulo);
        executarTeste("Ex 4: Programação Defensiva para Jogador Conectado", ResilienciaTestSuite::testEx4_ProgramacaoDefensivaJogadorConectado);

        // Nível 2
        executarTeste("Ex 5: Queda de Rede lança Checked Exception", ResilienciaTestSuite::testEx5_QuedaRedeLancaCheckedException);
        executarTeste("Ex 6 e 7: Execução garantida do bloco Finally", ResilienciaTestSuite::testEx6e7_BlocoFinallyExecutaSempre);

        // Nível 3
        executarTeste("Ex 8: NivelInsuficienteException herda de RuntimeException", ResilienciaTestSuite::testEx8_NivelInsuficienteExceptionHerancaEMensagem);
        executarTeste("Ex 9: Masmorra bloqueia jogador de nível inferior a 50", ResilienciaTestSuite::testEx9_MasmorraBloqueiaNivelInferior);
        executarTeste("Ex 9: Masmorra autoriza jogador de nível maior ou igual a 50", ResilienciaTestSuite::testEx9_MasmorraAutorizaNivelSuficiente);

        // Nível Boss
        executarTeste("Boss: Modos de Jogo implementam contrato ModoJogo", ResilienciaTestSuite::testBoss_ModosJogoPolimorfismo);
        executarTeste("Boss: BanidoException herda de Exception e tem mensagem correta", ResilienciaTestSuite::testBoss_BanidoExceptionHeranca);
        executarTeste("Boss: Matchmaker bloqueia jogador banido com BanidoException", ResilienciaTestSuite::testBoss_MatchmakerBloqueiaBanido);
        executarTeste("Boss: Matchmaker autoriza jogador regular nos modos Casual e Ranqueado", ResilienciaTestSuite::testBoss_MatchmakerAutorizaRegular);

        // Desafio Extra
        executarTeste("Desafio Extra: Quebra de Força Bruta AES-256-CBC PBKDF2", ResilienciaTestSuite::testDesafioExtra_QuebraForcaBruta);

        // Relatório Final
        imprimirRelatorioFinal();

        if (testesReprovados > 0) {
            System.exit(1);
        }
    }

    private static void executarTeste(String nomeTeste, RunnableComExcecao teste) {
        totalTestes++;
        try {
            teste.run();
            testesAprovados++;
            System.out.printf("  [PASSOU] %s\n", nomeTeste);
        } catch (Throwable t) {
            testesReprovados++;
            System.err.printf("  [FALHOU] %s -> %s\n", nomeTeste, t.getMessage());
            t.printStackTrace();
        }
    }

    // ==========================================
    // IMPLEMENTAÇÃO DOS TESTES UNITÁRIOS
    // ==========================================

    private static void testEx1_DivisaoPorZeroLancaArithmeticException() {
        EstatisticasCombate stats = new EstatisticasCombate(15, 0);
        boolean excecaoLancada = false;
        try {
            stats.calcularKdSemTratamento();
        } catch (ArithmeticException e) {
            excecaoLancada = true;
        }
        afirmarVerdadeiro(excecaoLancada, "Deveria ter lançado ArithmeticException ao dividir por zero");
    }

    private static void testEx2_TratamentoKdJogadorInvicto() {
        EstatisticasCombate stats = new EstatisticasCombate(15, 0);
        String resultado = stats.calcularKdResiliente();
        afirmarIgual("Taxa K/D: Jogador Invicto!", resultado, "Mensagem de jogador invicto incorreta");
    }

    private static void testEx2_CalculoNormalKd() {
        EstatisticasCombate stats = new EstatisticasCombate(15, 3);
        String resultado = stats.calcularKdResiliente();
        afirmarIgual("Taxa K/D: 5", resultado, "Cálculo normal de K/D incorreto");
    }

    private static void testEx3_AcessoForaIndiceLancaException() {
        Inventario inv = new Inventario();
        boolean excecaoLancada = false;
        try {
            inv.equiparItemSemTratamento(5, "Espada");
        } catch (ArrayIndexOutOfBoundsException e) {
            excecaoLancada = true;
        }
        afirmarVerdadeiro(excecaoLancada, "Deveria ter lançado ArrayIndexOutOfBoundsException no índice 5");
    }

    private static void testEx3_InsercaoResilienteInventario() {
        Inventario inv = new Inventario();
        String status = inv.equiparItemResiliente(5, "Espada");
        afirmarIgual("Inventário cheio!", status, "Mensagem de inventário cheio incorreta");
    }

    private static void testEx3_InsercaoValidaSlots() {
        Inventario inv = new Inventario();
        String status0 = inv.equiparItemResiliente(0, "Poção de Vida");
        String status1 = inv.equiparItemResiliente(1, "Escudo");
        String status2 = inv.equiparItemResiliente(2, "Armadura");

        afirmarVerdadeiro(status0.contains("sucesso"), "Deveria equipar slot 0");
        afirmarVerdadeiro(status1.contains("sucesso"), "Deveria equipar slot 1");
        afirmarVerdadeiro(status2.contains("sucesso"), "Deveria equipar slot 2");
        afirmarIgual("Escudo", inv.getItens()[1], "Item no slot 1 deveria ser Escudo");
    }

    private static void testEx4_ProgramacaoDefensivaJogadorNulo() {
        String jogadorNulo = null;
        String resultado = Jogador.verificarStatusConexao(jogadorNulo);
        afirmarIgual("Jogador desconectado", resultado, "Jogador nulo deveria retornar 'Jogador desconectado'");

        Jogador objetoJogadorNulo = null;
        String resultadoObj = Jogador.verificarStatusConexao(objetoJogadorNulo);
        afirmarIgual("Jogador desconectado", resultadoObj, "Objeto nulo deveria retornar 'Jogador desconectado'");
    }

    private static void testEx4_ProgramacaoDefensivaJogadorConectado() {
        String resultado = Jogador.verificarStatusConexao("ProGamer_X");
        afirmarIgual("ProGamer_X", resultado, "Jogador conectado deveria retornar o próprio nome");

        Jogador jogador = new Jogador("ProGamer_X", 30);
        String resultadoObj = Jogador.verificarStatusConexao(jogador);
        afirmarIgual("ProGamer_X", resultadoObj, "Objeto Jogador conectado deveria retornar o nickname");
    }

    private static void testEx5_QuedaRedeLancaCheckedException() {
        boolean excecaoChecked = false;
        try {
            RedeService.conectarServidor();
        } catch (Exception e) {
            excecaoChecked = true;
            afirmarIgual("Servidor caiu!", e.getMessage(), "Mensagem da exceção de rede incorreta");
        }
        afirmarVerdadeiro(excecaoChecked, "conectarServidor deveria lançar Exception checked");
    }

    private static void testEx6e7_BlocoFinallyExecutaSempre() {
        // Redireciona System.out temporariamente para verificar execução do finally
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(baos));
            RedeService.executarRotinaConexaoComFinally();
        } finally {
            System.setOut(originalOut);
        }

        String saida = baos.toString();
        afirmarVerdadeiro(saida.contains("Servidor caiu!"), "Deveria capturar mensagem do catch");
        afirmarVerdadeiro(saida.contains("Fechando portas de rede do jogo..."), "Finally deve executar e fechar portas");
    }

    private static void testEx8_NivelInsuficienteExceptionHerancaEMensagem() {
        NivelInsuficienteException ex = new NivelInsuficienteException();
        afirmarVerdadeiro(ex instanceof RuntimeException, "NivelInsuficienteException deve herdar de RuntimeException");
        afirmarIgual("Seu nível é muito baixo para esta masmorra!", ex.getMessage(), "Mensagem padrão da exceção incorreta");
    }

    private static void testEx9_MasmorraBloqueiaNivelInferior() {
        Masmorra masmorra = new Masmorra();
        boolean bloqueado = false;
        try {
            masmorra.entrar(20);
        } catch (NivelInsuficienteException e) {
            bloqueado = true;
        }
        afirmarVerdadeiro(bloqueado, "Jogador nível 20 deveria ser bloqueado com NivelInsuficienteException");
    }

    private static void testEx9_MasmorraAutorizaNivelSuficiente() {
        Masmorra masmorra = new Masmorra();
        boolean sucesso50 = false;
        boolean sucesso100 = false;
        try {
            masmorra.entrar(50);
            sucesso50 = true;
            masmorra.entrar(100);
            sucesso100 = true;
        } catch (NivelInsuficienteException e) {
            // Não deve lançar
        }
        afirmarVerdadeiro(sucesso50, "Nível 50 deve ter acesso liberado");
        afirmarVerdadeiro(sucesso100, "Nível 100 deve ter acesso liberado");
    }

    private static void testBoss_ModosJogoPolimorfismo() {
        ModoJogo casual = new ModoCasual();
        ModoJogo ranqueado = new ModoRanqueado();

        afirmarVerdadeiro(casual instanceof ModoJogo, "ModoCasual deve implementar ModoJogo");
        afirmarVerdadeiro(ranqueado instanceof ModoJogo, "ModoRanqueado deve implementar ModoJogo");
    }

    private static void testBoss_BanidoExceptionHeranca() {
        BanidoException ex = new BanidoException();
        afirmarVerdadeiro(ex instanceof Exception, "BanidoException deve herdar de Exception (Checked)");
        afirmarFalso(RuntimeException.class.isAssignableFrom(BanidoException.class), "BanidoException NÃO deve herdar de RuntimeException");
        afirmarIgual("Jogador Banido!", ex.getMessage(), "Mensagem de banimento incorreta");
    }

    private static void testBoss_MatchmakerBloqueiaBanido() {
        Matchmaker matchmaker = new Matchmaker();
        ModoJogo casual = new ModoCasual();
        boolean capturouBanimento = false;

        try {
            matchmaker.encontrarSala(casual, true);
        } catch (BanidoException e) {
            capturouBanimento = true;
            afirmarIgual("Jogador Banido!", e.getMessage(), "Mensagem de banimento incorreta");
        }

        afirmarVerdadeiro(capturouBanimento, "Matchmaker deveria ter lançado BanidoException para jogador banido");
    }

    private static void testBoss_MatchmakerAutorizaRegular() {
        Matchmaker matchmaker = new Matchmaker();
        ModoJogo casual = new ModoCasual();
        ModoJogo ranqueado = new ModoRanqueado();
        boolean sucessoCasual = false;
        boolean sucessoRanqueado = false;

        try {
            matchmaker.encontrarSala(casual, false);
            sucessoCasual = true;
            matchmaker.encontrarSala(ranqueado, false);
            sucessoRanqueado = true;
        } catch (BanidoException e) {
            // Não deve lançar
        }

        afirmarVerdadeiro(sucessoCasual, "Matchmaker casual deveria executar sem exceção");
        afirmarVerdadeiro(sucessoRanqueado, "Matchmaker ranqueado deveria executar sem exceção");
    }

    private static void testDesafioExtra_QuebraForcaBruta() {
        CofreGabaritoService service = new CofreGabaritoService();
        ResultadoDecriptacao res = service.desbloquearCofre();

        afirmarVerdadeiro(res.isSucesso(), "Ataque de força bruta deveria ter sucesso");
        afirmarIgual("javac", res.getSenha(), "A senha encontrada deveria ser 'javac'");
        afirmarVerdadeiro(res.getTextoDecifrado() != null && res.getTextoDecifrado().contains("drive.google.com"),
                "O link revelado deve conter drive.google.com");
    }

    // ==========================================
    // HELPERS DE ASSERTION
    // ==========================================

    private static void afirmarVerdadeiro(boolean condicao, String mensagem) {
        if (!condicao) {
            throw new AssertionError("Falha na validação: " + mensagem);
        }
    }

    private static void afirmarFalso(boolean condicao, String mensagem) {
        if (condicao) {
            throw new AssertionError("Falha na validação: " + mensagem);
        }
    }

    private static void afirmarIgual(Object esperado, Object atual, String mensagem) {
        if (esperado == null && atual == null) return;
        if (esperado != null && esperado.equals(atual)) return;
        throw new AssertionError(String.format("%s (Esperado: '%s', Atual: '%s')", mensagem, esperado, atual));
    }

    private static void imprimirRelatorioFinal() {
        System.out.println("========================================================");
        System.out.printf("TOTAL DE TESTES : %d\n", totalTestes);
        System.out.printf("APROVADOS       : %d\n", testesAprovados);
        System.out.printf("REPROVADOS      : %d\n", testesReprovados);
        System.out.printf("TAXA DE SUCESSO : %.1f%%\n", (testesAprovados * 100.0) / totalTestes);
        System.out.println("========================================================");
    }

    @FunctionalInterface
    private interface RunnableComExcecao {
        void run() throws Throwable;
    }
}
