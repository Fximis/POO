package br.com.nexustech.application.service;

import br.com.nexustech.infrastructure.crypto.AesPbkdf2Decryptor;
import br.com.nexustech.infrastructure.crypto.AesPbkdf2Decryptor.ResultadoDecriptacao;

/**
 * Serviço de Aplicação para o Desafio Extra (Cofre do Gabarito).
 * Coordena a descriptografia por força bruta do link cifrado do Drive.
 */
public class CofreGabaritoService {

    public static final String LINK_CIFRADO_PADRAO =
            "U2FsdGVkX1/Jz86x4/Ydu3FZFw5pSo86xHG1MwpCFX/Dnn9uCMDd3xLNn61XZouv" +
            "Qy6G2FIhyQXAQwvTWn3/01JGIoIh5RN4NXgs+kdpcf6afHmSMvCZuOEiiiXlVpB2" +
            "EQGIKDtIAU9c1aQx6bzEgQ==";

    public static final String PREFIXO_SENHA = "jav";
    public static final int TAMANHO_SENHA = 5;

    public ResultadoDecriptacao desbloquearCofre() {
        return desbloquearCofre(LINK_CIFRADO_PADRAO);
    }

    public ResultadoDecriptacao desbloquearCofre(String payloadCifrado) {
        return AesPbkdf2Decryptor.quebrarForcaBruta(payloadCifrado, PREFIXO_SENHA, TAMANHO_SENHA);
    }
}
