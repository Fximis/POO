package br.com.fronteira.main;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * DESAFIO EXTRA: O COFRE DO GABARITO (Engenharia e Segurança)
 * 
 * Implementação completa do ataque de força bruta contra o payload AES-256-CBC PBKDF2.
 */
public class App {

    public static void main(String[] args) {
        String encryptedB64 = "U2FsdGVkX1/Jz86x4/Ydu3FZFw5pSo86xHG1MwpCFX/Dnn9uCMDd3xLNn61XZouvQy6G2FIhyQXAQwvTWn3/01JGIoIh5RN4NXgs+kdpcf6afHmSMvCZuOEiiiXlVpB2EQGIKDtIAU9c1aQx6bzEgQ==";

        // Limpa possíveis quebras de linha do Base64
        encryptedB64 = encryptedB64.replaceAll("\\s", "");

        // 1. Decodifica o Base64 para Bytes
        byte[] rawBytes = Base64.getDecoder().decode(encryptedB64);

        // 2. Extrai o Salt (do byte 8 ao 15) e o Texto Cifrado (do 16 em diante)
        byte[] salt = Arrays.copyOfRange(rawBytes, 8, 16);
        byte[] cipherText = Arrays.copyOfRange(rawBytes, 16, rawBytes.length);

        // O padrão da senha é "javXX" contendo letras e números
        String charset = "abcdefghijklmnopqrstuvwxyz0123456789";

        System.out.println("Iniciando ataque de força bruta no link da NexusTech...");
        long startTime = System.currentTimeMillis();

        // 3. Força Bruta: Combinando os dois últimos caracteres
        for (char c1 : charset.toCharArray()) {
            for (char c2 : charset.toCharArray()) {
                String testPass = "jav" + c1 + c2;

                try {
                    SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                    // PBKDF2 gera Key e IV juntos. 32 bytes (Key) + 16 bytes (IV) = 48 bytes (384 bits)
                    PBEKeySpec spec = new PBEKeySpec(testPass.toCharArray(), salt, 1000, 48 * 8);
                    byte[] keyAndIv = factory.generateSecret(spec).getEncoded();

                    byte[] key = Arrays.copyOfRange(keyAndIv, 0, 32);
                    byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);

                    // Prepara o motor do AES-256-CBC
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                    IvParameterSpec ivSpec = new IvParameterSpec(iv);
                    cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                    // Tenta descriptografar. Se a chave estiver errada, o padding falha e lança Exception
                    byte[] decrypted = cipher.doFinal(cipherText);
                    String result = new String(decrypted, StandardCharsets.UTF_8);

                    // 4. Checa se revelou o link com sucesso
                    if (result.contains("http")) {
                        long endTime = System.currentTimeMillis();

                        System.out.println("\n[SUCESSO] A criptografia foi quebrada!");
                        System.out.println("Senha encontrada: " + testPass);
                        System.out.println("Link revelado: " + result.trim());
                        System.out.println("Tempo de execução: " + (endTime - startTime) + "ms");
                        return; // Interrompe a execução após achar a senha
                    }

                } catch (Exception e) {
                    // Uma senha incorreta vai acionar a BadPaddingException.
                    // Nós simplesmente ignoramos o erro e o loop continua testando a próxima!
                }
            }
        }

        System.out.println("\nForça bruta concluída. Senha não encontrada.");
    }
}
