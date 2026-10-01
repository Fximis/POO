package br.com.nexustech.infrastructure.crypto;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * Infraestrutura de Criptografia: Quebra por Força Bruta de payload OpenSSL AES-256-CBC com PBKDF2.
 * 
 * DESAFIO EXTRA: O COFRE DO GABARITO (Engenharia e Segurança)
 */
public class AesPbkdf2Decryptor {

    public static class ResultadoDecriptacao {
        private final boolean sucesso;
        private final String senha;
        private final String textoDecifrado;
        private final long tempoExecucaoMs;
        private final int tentativas;

        public ResultadoDecriptacao(boolean sucesso, String senha, String textoDecifrado, long tempoExecucaoMs, int tentativas) {
            this.sucesso = sucesso;
            this.senha = senha;
            this.textoDecifrado = textoDecifrado;
            this.tempoExecucaoMs = tempoExecucaoMs;
            this.tentativas = tentativas;
        }

        public boolean isSucesso() { return sucesso; }
        public String getSenha() { return senha; }
        public String getTextoDecifrado() { return textoDecifrado; }
        public long getTempoExecucaoMs() { return tempoExecucaoMs; }
        public int getTentativas() { return tentativas; }

        @Override
        public String toString() {
            if (sucesso) {
                return String.format("SUCESSO! Senha: %s | Link: %s | Tempo: %dms | Tentativas: %d",
                        senha, textoDecifrado, tempoExecucaoMs, tentativas);
            }
            return String.format("FALHA! Senha não encontrada após %d tentativas (%dms).", tentativas, tempoExecucaoMs);
        }
    }

    /**
     * Executa ataque de força bruta contra o payload OpenSSL criptografado com AES-256-CBC e PBKDF2.
     * 
     * @param encryptedB64 String Base64 contendo o texto cifrado (com cabeçalho Salted__)
     * @param prefixoSenha Prefixo conhecido da senha (ex: "jav")
     * @param tamanhoTotalSenha Tamanho exato da senha (ex: 5 caracteres)
     * @return ResultadoDecriptacao com os detalhes do resultado
     */
    public static ResultadoDecriptacao quebrarForcaBruta(String encryptedB64, String prefixoSenha, int tamanhoTotalSenha) {
        long startTime = System.currentTimeMillis();
        String cleanedB64 = encryptedB64.replaceAll("\\s", "");

        // 1. Decodifica Base64
        byte[] rawBytes = Base64.getDecoder().decode(cleanedB64);

        // 2. Extrai o Salt (bytes 8 a 15) e o Texto Cifrado (bytes 16 em diante)
        byte[] salt = Arrays.copyOfRange(rawBytes, 8, 16);
        byte[] cipherText = Arrays.copyOfRange(rawBytes, 16, rawBytes.length);

        String charset = "abcdefghijklmnopqrstuvwxyz0123456789";
        int tentativas = 0;

        // Tenta com HMAC-SHA256 (padrão OpenSSL moderno) e se necessário HMAC-SHA1
        String[] kdfAlgorithms = {"PBKDF2WithHmacSHA256", "PBKDF2WithHmacSHA1"};

        for (String kdfAlgorithm : kdfAlgorithms) {
            try {
                SecretKeyFactory factory = SecretKeyFactory.getInstance(kdfAlgorithm);

                for (char c1 : charset.toCharArray()) {
                    for (char c2 : charset.toCharArray()) {
                        tentativas++;
                        String testPass = prefixoSenha + c1 + c2;

                        try {
                            // PBKDF2 gera Key e IV juntos: 32 bytes (Key) + 16 bytes (IV) = 48 bytes (384 bits)
                            PBEKeySpec spec = new PBEKeySpec(testPass.toCharArray(), salt, 1000, 48 * 8);
                            byte[] keyAndIv = factory.generateSecret(spec).getEncoded();

                            byte[] key = Arrays.copyOfRange(keyAndIv, 0, 32);
                            byte[] iv = Arrays.copyOfRange(keyAndIv, 32, 48);

                            // Prepara o motor do AES-256-CBC
                            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
                            IvParameterSpec ivSpec = new IvParameterSpec(iv);
                            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

                            // Tenta descriptografar
                            byte[] decrypted = cipher.doFinal(cipherText);
                            String result = new String(decrypted, StandardCharsets.UTF_8);

                            // 4. Checa se revelou o link com sucesso
                            if (result.contains("http")) {
                                long endTime = System.currentTimeMillis();
                                return new ResultadoDecriptacao(true, testPass, result.trim(), (endTime - startTime), tentativas);
                            }
                        } catch (Exception e) {
                            // BadPaddingException / IllegalBlockSizeException são esperadas para senhas incorretas
                            // O fluxo resiliente ignora e prossegue para a próxima combinação
                        }
                    }
                }
            } catch (Exception e) {
                // Se o algoritmo KDF não for suportado nesta JVM, tenta o próximo
            }
        }

        long endTime = System.currentTimeMillis();
        return new ResultadoDecriptacao(false, null, null, (endTime - startTime), tentativas);
    }
}
