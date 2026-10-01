package br.com.sistema.main;

import br.com.sistema.model.Usuario;
import br.com.sistema.model.Video;

public class MainStreaming {
    public static void main(String[] args) {
        Usuario u = new Usuario("Alice", "alice@email.com", true);
        Video v1 = new Video("Filme 1", 120);
        Video v2 = new Video("Filme 2", -45);
        
        System.out.println(u.toString());
        System.out.println("V1 duração: " + v1.getDuracaoMinutos());
        System.out.println("V2 duração: " + v2.getDuracaoMinutos());
    }
}
