package br.com.nexustech.domain.exception;

/**
 * Exceção de Domínio checada (Checked - Exception)
 * lançada quando um jogador banido tenta buscar partida no sistema de matchmaking.
 * 
 * Nível Boss - Passo 3: A Exceção Suprema
 */
public class BanidoException extends Exception {

    private static final long serialVersionUID = 1L;

    public BanidoException() {
        super("Jogador Banido!");
    }

    public BanidoException(String mensagem) {
        super(mensagem);
    }

    public BanidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
