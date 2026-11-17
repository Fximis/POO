package br.com.nexuscloud.exception;

public class ServidorOfflineException extends RuntimeException {
    public ServidorOfflineException(String msg) {
        super(msg);
    }
}
