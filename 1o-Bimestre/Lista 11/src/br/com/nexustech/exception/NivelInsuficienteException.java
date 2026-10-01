package br.com.nexustech.exception;

public class NivelInsuficienteException extends RuntimeException {
    public NivelInsuficienteException() {
        super("Seu nível é muito baixo para esta masmorra!");
    }
}
