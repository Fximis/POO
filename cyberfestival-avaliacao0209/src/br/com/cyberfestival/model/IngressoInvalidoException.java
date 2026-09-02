package br.com.cyberfestival.model;

/**
 * Exceção customizada para tratamento de ingressos inválidos.
 * Herda de RuntimeException (Unchecked) conforme RN02.
 */
public class IngressoInvalidoException extends RuntimeException {

    /**
     * Construtor que repassa a mensagem de erro específica.
     */
    public IngressoInvalidoException() {
        // Repassa a mensagem exata solicitada na RN02 para a superclasse
        super("Erro de Segurança: Ingresso já validado ou código duplicado!");
    }
}
