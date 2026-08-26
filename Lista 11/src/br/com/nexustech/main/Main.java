package br.com.nexustech.main;

import br.com.nexustech.domain.exception.BanidoException;
import br.com.nexustech.domain.exception.NivelInsuficienteException;
import br.com.nexustech.domain.model.Masmorra;
import br.com.nexustech.domain.service.Matchmaker;
import br.com.nexustech.domain.service.ModoCasual;
import br.com.nexustech.domain.service.ModoJogo;
import br.com.nexustech.domain.service.ModoRanqueado;
import br.com.nexustech.application.service.CofreGabaritoService;
import br.com.nexustech.infrastructure.crypto.AesPbkdf2Decryptor.ResultadoDecriptacao;

/**
 * Ponto de Entrada Principal (NexusTech Game Engine - Protocolo de Resiliência).
 * Executa detalhadamente todos os níveis e exercícios da Lista 11.
 */
public class Main {

    public static void main(String[] args) {
        imprimirCabecalho();

        // =========================================================================
        // NÍVEL 1: O TRAJE ANTIBOMBAS (Unchecked Exceptions)
        // =========================================================================
        System.out.println("\n========================================================");
        System.out.println(">>> NÍVEL 1: O TRAJE ANTIBOMBAS (Unchecked Exceptions)");
        System.out.println("========================================================");

        // --- Exercício 1 & 2: O Bug do K/D e Conserto ---
        System.out.println("\n[Exercício 1 e 2] Cálculo de Taxa K/D (Divisão por zero e ArithmeticException):");
        int kills = 15;
        int deaths = 0;
        
        System.out.println("Status do Jogador: Kills = " + kills + ", Deaths = " + deaths);
        try {
            // Tentativa de divisão de kills por deaths
            int taxaKd = kills / deaths;
            System.out.println("Taxa K/D calculada: " + taxaKd);
        } catch (ArithmeticException e) {
            // Captura da exceção matemática e resposta elegante
            System.out.println("Taxa K/D: Jogador Invicto!");
        }

        // Demonstração adicional com mortes > 0
        int killsNormal = 15;
        int deathsNormal = 3;
        try {
            int taxaKdNormal = killsNormal / deathsNormal;
            System.out.println("Taxa K/D (Partida com mortes): " + taxaKdNormal);
        } catch (ArithmeticException e) {
            System.out.println("Taxa K/D: Jogador Invicto!");
        }

        // --- Exercício 3: O Inventário Bugado ---
        System.out.println("\n[Exercício 3] O Inventário Bugado (ArrayIndexOutOfBoundsException):");
        String[] inventario = new String[3]; // Índices 0, 1 e 2
        System.out.println("Inventário criado com tamanho: " + inventario.length + " slots.");

        try {
            System.out.println("Tentando inserir 'Espada' no slot 5 (fora dos limites)...");
            inventario[5] = "Espada";
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Inventário cheio!");
        }

        // --- Exercício 4: O Fantasma (O Mito do NullPointer) ---
        System.out.println("\n[Exercício 4] O Fantasma (Programação Defensiva contra NullPointerException):");
        String jogador = null;
        System.out.println("Validando variável jogador nula com checagem defensiva if/else:");
        if (jogador != null) {
            System.out.println(jogador);
        } else {
            System.out.println("Jogador desconectado");
        }

        // Testando jogador conectado
        String jogadorConectado = "CyberKnight_99";
        System.out.println("Validando jogador autenticado:");
        if (jogadorConectado != null) {
            System.out.println("Jogador logado: " + jogadorConectado);
        } else {
            System.out.println("Jogador desconectado");
        }

        // =========================================================================
        // NÍVEL 2: O CONTRATO OBRIGATÓRIO (Checked Exceptions & Finally)
        // =========================================================================
        System.out.println("\n========================================================");
        System.out.println(">>> NÍVEL 2: O CONTRATO OBRIGATÓRIO (Checked Exceptions)");
        System.out.println("========================================================");

        // --- Exercício 5, 6 e 7: Queda de Servidor, Assinatura de Contrato e Finally ---
        System.out.println("\n[Exercício 5, 6 e 7] Conexão com Servidor e Fechamento com Finally:");
        try {
            conectarServidor();
        } catch (Exception e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        } finally {
            System.out.println("Fechando portas de rede do jogo...");
        }

        // =========================================================================
        // NÍVEL 3: DOMINANDO AS REGRAS (Exceções Customizadas)
        // =========================================================================
        System.out.println("\n========================================================");
        System.out.println(">>> NÍVEL 3: DOMINANDO AS REGRAS (Exceções Customizadas)");
        System.out.println("========================================================");

        // --- Exercício 8 e 9: NivelInsuficienteException e Masmorra Fechada ---
        System.out.println("\n[Exercício 8 e 9] Validação de Nível na Masmorra (NivelInsuficienteException):");
        Masmorra masmorra = new Masmorra();
        
        // Tentativa com jogador nível 20 (abaixo do requisito 50)
        int nivelJogadorTeste = 20;
        System.out.println("Tentando entrar na Masmorra com nível " + nivelJogadorTeste + "...");
        try {
            masmorra.entrar(nivelJogadorTeste);
        } catch (NivelInsuficienteException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }

        // Tentativa com jogador nível 60 (autorizado)
        int nivelJogadorVeterano = 60;
        System.out.println("\nTentando entrar na Masmorra com nível " + nivelJogadorVeterano + "...");
        try {
            masmorra.entrar(nivelJogadorVeterano);
        } catch (NivelInsuficienteException e) {
            System.out.println("Exceção capturada: " + e.getMessage());
        }

        // =========================================================================
        // NÍVEL BOSS: O ARQUITETO MATCHMAKER
        // =========================================================================
        System.out.println("\n========================================================");
        System.out.println(">>> NÍVEL BOSS: O ARQUITETO MATCHMAKER (Polimorfismo + Exceções)");
        System.out.println("========================================================");

        Matchmaker matchmaker = new Matchmaker();
        ModoJogo casual = new ModoCasual();
        ModoJogo ranqueado = new ModoRanqueado();

        // 1. Testando Jogador Banido no Modo Casual
        System.out.println("\n[Boss - Caso 1] Tentativa de busca de partida para JOGADOR BANIDO (Modo Casual):");
        boolean jogadorBanido = true;
        try {
            matchmaker.encontrarSala(casual, jogadorBanido);
        } catch (BanidoException e) {
            System.out.println("Alerta de Segurança: " + e.getMessage());
        }

        // 2. Testando Jogador Autorizado no Modo Casual
        System.out.println("\n[Boss - Caso 2] Busca de partida para JOGADOR REGULAR (Modo Casual):");
        try {
            matchmaker.encontrarSala(casual, false);
        } catch (BanidoException e) {
            System.out.println("Alerta de Segurança: " + e.getMessage());
        }

        // 3. Testando Jogador Autorizado no Modo Ranqueado
        System.out.println("\n[Boss - Caso 3] Busca de partida para JOGADOR REGULAR (Modo Ranqueado):");
        try {
            matchmaker.encontrarSala(ranqueado, false);
        } catch (BanidoException e) {
            System.out.println("Alerta de Segurança: " + e.getMessage());
        }

        // =========================================================================
        // DESAFIO EXTRA: O COFRE DO GABARITO (Engenharia e Segurança)
        // =========================================================================
        System.out.println("\n========================================================");
        System.out.println(">>> DESAFIO EXTRA: O COFRE DO GABARITO (Quebra Criptográfica)");
        System.out.println("========================================================");

        CofreGabaritoService cofreService = new CofreGabaritoService();
        System.out.println("Executando ataque de força bruta no payload AES-256-CBC PBKDF2...");
        ResultadoDecriptacao resultado = cofreService.desbloquearCofre();
        if (resultado.isSucesso()) {
            System.out.println("[SUCESSO] Gabarito desbloqueado com sucesso!");
            System.out.println("Senha Descoberta : " + resultado.getSenha());
            System.out.println("Link do Gabarito : " + resultado.getTextoDecifrado());
            System.out.println("Tempo de Execução: " + resultado.getTempoExecucaoMs() + "ms");
            System.out.println("Tentativas       : " + resultado.getTentativas());
        } else {
            System.out.println("[FALHA] Não foi possível quebrar a criptografia.");
        }

        System.out.println("\n========================================================");
        System.out.println("PROTOCOLO DE RESILIÊNCIA CONCLUÍDO COM 100% DE SUCESSO!");
        System.out.println("========================================================\n");
    }

    /**
     * Exercício 5: Simulando a Queda de Internet
     * Método estático que declara checked exception e força o erro de rede.
     */
    public static void conectarServidor() throws Exception {
        throw new Exception("Servidor caiu!");
    }

    private static void imprimirCabecalho() {
        System.out.println("========================================================");
        System.out.println("   NEXUSTECH GAME ENGINE - PROTOCOLO DE RESILIÊNCIA     ");
        System.out.println("         Lista 11: Tratamento de Exceções em Java       ");
        System.out.println("========================================================");
    }
}
