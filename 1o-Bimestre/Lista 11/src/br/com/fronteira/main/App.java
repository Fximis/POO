package br.com.fronteira.main;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

public class App {
    public static void main(String[] args) {
        String encryptedB64 = "U2FsdGVkX1/Jz86x4/Ydu3FZFw5pSo86xHG1MwpCFX/" +
                "Dnn9uCMDd3xLNn61XZouvQy6G2FIhyQXAQwvTWn3/01JGIoIh5RN4NXgs+kdpcf6afHmSMvCZu0EiiiXlVpB2EQGIKDLIAU9c1aQx" +
                "6bzEgQ==";
        
        // Limpa possíveis quebras de linha do Base64
        encryptedB64 = encryptedB64.replaceAll("\\s", "");
        
        byte[] cipherTextWithSalt = Base64.getDecoder().decode(encryptedB64);
        
        byte[] salt = Arrays.copyOfRange(cipherTextWithSalt, 8, 16);
        byte[] cipherText = Arrays.copyOfRange(cipherTextWithSalt, 16, cipherTextWithSalt.length);
        
        String charset = "abcdefghijklmnopqrstuvwxyz0123456789";
        System.out.println("Iniciando ataque de força bruta no link da NexusTech...");
        long startTime = System.currentTimeMillis();
        
        for (char c1 : charset.toCharArray()) {
            for (char c2 : charset.toCharArray()) {
                String testPass = "jav" + c1 + c2;
                try {
                    PBEKeySpec spec = new PBEKeySpec(testPass.toCharArray(), salt, 1000, 384);
                    SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
                    byte[] keyAndIv = skf.generateSecret(spec).getEncoded();
                    
                    byte[] key = Arrays.copyOfRange(keyAndIv, 0, 32);
                    byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);
                    
                    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                    cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, "AES"), new IvParameterSpec(iv));
                    
                    String result = new String(cipher.doFinal(cipherText), StandardCharsets.UTF_8);
                    
                    if (result.contains("http")) {
                        long endTime = System.currentTimeMillis();
                        System.out.println("\n SUCESSO! A criptografia foi quebrada!");
                        System.out.println("Senha encontrada: " + testPass);
                        System.out.println("Link revelado: " + result.trim());
                        System.out.println("Tempo de execução: " + (endTime - startTime) + "ms");
                        return;
                    }
                } catch (Exception e) {
                    // Ignora erro de padding
                }
            }
        }
        System.out.println("\nForça bruta concluída. Senha não encontrada.");
    }
}
