package br.com.nexustech.main;

import br.com.nexustech.model.*;
import br.com.nexustech.exception.*;

public class Main {
    public static void main(String[] args) {
        // Exercício 2
        try {
            int kills = 15;
            int deaths = 0;
            System.out.println(kills / deaths);
        } catch (ArithmeticException e) {
            System.out.println("Taxa K/D: Jogador Invicto!");
        }

        // Exercício 3
        try {
            String[] inventario = new String[3];
            inventario[5] = "Espada";
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Inventário cheio!");
        }

        // Exercício 4
        String jogador = null;
        if (jogador != null) {
            System.out.println(jogador);
        } else {
            System.out.println("Jogador desconectado");
        }

        // Exercício 6
        try {
            conectarServidor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Fechando portas de rede do jogo...");
        }

        // Exercício 9
        try {
            Masmorra masmorra = new Masmorra();
            masmorra.entrar(20);
        } catch (NivelInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        // Exercício 14
        Matchmaker matchmaker = new Matchmaker();
        ModoCasual casual = new ModoCasual();
        try {
            matchmaker.encontrarSala(casual, true);
        } catch (BanidoException e) {
            System.out.println(e.getMessage());
        }
    }

    // Exercício 5
    public static void conectarServidor() throws Exception {
        throw new Exception("Servidor caiu!");
    }
}
