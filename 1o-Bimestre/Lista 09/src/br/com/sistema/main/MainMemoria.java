package br.com.sistema.main;

import br.com.sistema.model.Usuario;

public class MainMemoria {
    public static void main(String[] args) {
        Usuario u1 = new Usuario("Bob", "bob@email.com", true);
        Usuario u2 = new Usuario("Bob Clone", "bob@email.com", false);
        
        if (u1.equals(u2)) {
            System.out.println("Os usuários são logicamente iguais.");
        } else {
            System.out.println("Os usuários são diferentes.");
        }
        
        System.out.println("Plataforma: " + Usuario.NOME_PLATAFORMA);
        System.out.println("Total Usuários: " + Usuario.getTotalUsuarios());
    }
}
