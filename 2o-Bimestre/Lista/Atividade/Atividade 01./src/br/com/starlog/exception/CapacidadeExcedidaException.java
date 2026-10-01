package br.com.starlog.exception;

/**
 * Exceção checada (Checked Exception) lançada quando uma tentativa de
 * carregamento ultrapassa a capacidade máxima estrutural suportada por um módulo de carga.
 */
public class CapacidadeExcedidaException extends Exception {

    public CapacidadeExcedidaException(String message) {
        super(message);
    }

    public CapacidadeExcedidaException() {
        super("Capacidade máxima do módulo de carga excedida.");
    }
}
