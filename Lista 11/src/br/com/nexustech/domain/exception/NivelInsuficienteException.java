package br.com.nexustech.domain.exception;

/**
 * Exceção de Domínio não-checada (Unchecked - RuntimeException)
 * lançada quando o jogador tenta acessar masmorras ou áreas de nível superior ao seu.
 * 
 * Exercício 8: Forjando a Regra de Negócio
 */
public class NivelInsuficienteException extends RuntimeException {
    
    private static final long serialVersionUID = 1L;

    public NivelInsuficienteException() {
        super("Seu nível é muito baixo para esta masmorra!");
    }

    public NivelInsuficienteException(String mensagem) {
        super(mensagem);
    }

    public NivelInsuficienteException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
