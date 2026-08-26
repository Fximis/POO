package br.com.nexustech.application.service;

/**
 * Serviço de Aplicação para gerenciamento de conexões de rede e simulação de resiliência.
 * 
 * Exercício 5: Simulando a Queda de Internet (throws Exception)
 * Exercício 6: A Assinatura do Contrato (try-catch de checked exception)
 * Exercício 7: O Zelador (bloco finally para liberação de recursos)
 */
public class RedeService {

    /**
     * Simula tentativa de conexão com o servidor de jogos.
     * 
     * Exercício 5: throws Exception e força a queda com "Servidor caiu!"
     * 
     * @throws Exception simulando falha na comunicação de rede
     */
    public static void conectarServidor() throws Exception {
        throw new Exception("Servidor caiu!");
    }

    /**
     * Executa a rotina completa de conexão com tratamento e liberação garantida no finally.
     * 
     * Exercícios 6 e 7 integrados.
     */
    public static void executarRotinaConexaoComFinally() {
        try {
            conectarServidor();
        } catch (Exception e) {
            System.out.println("Erro capturado: " + e.getMessage());
        } finally {
            System.out.println("Fechando portas de rede do jogo...");
        }
    }
}
