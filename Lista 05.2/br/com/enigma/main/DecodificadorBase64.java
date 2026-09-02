package br.com.enigma.main;
import java.util.Base64;
public class DecodificadorBase64 implements Decodificador {
    @Override
    public String decodificar(String texto) {
        return new String(Base64.getDecoder().decode(texto));
    }
}
