package br.com.enigma.main;
import java.nio.file.Files;
import java.nio.file.Paths;
public class Main {
    public static void main(String[] args) throws Exception {
        // Pista 01
        String base64Key = "Q2hhdmVfQ2VzYXI6IDcgfCBBbHZvOiBtZW5zYWdlbS50eHQ=";
        Decodificador decBase64 = new DecodificadorBase64();
        System.out.println("Base64 decodificado: " + decBase64.decodificar(base64Key));
        
        // Mensagem Interceptada
        String mensagem = new String(Files.readAllBytes(Paths.get("src/mensagem.txt")));
        Decodificador decCesar = new DecodificadorCesar(7);
        System.out.println("\nMensagem decodificada: " + decCesar.decodificar(mensagem));
        
        // Filtro Mágico
        FiltroImagem filtro = new FiltroVermelhoMagico();
        filtro.aplicarFiltro("src/ruido.bmp", "src/ruido_revelado.bmp");
        System.out.println("\nFiltro aplicado! Verifique o arquivo src/ruido_revelado.bmp");
    }
}
