package br.com.technexus.main;

import br.com.technexus.model.Produto;
import br.com.technexus.model.Loja;

public class Main {
    public static void main(String[] args) {
        Loja loja = new Loja();
        loja.cadastrar(new Produto("The Witcher", "GAMES", 150.0));
        loja.cadastrar(new Produto("FIFA", "GAMES", 200.0));
        loja.cadastrar(new Produto("Java for Dummies", "LIVROS", 100.0));
        loja.cadastrar(new Produto("Clean Code", "LIVROS", 80.0));
        loja.cadastrar(new Produto("Mouse", "HARDWARE", 50.0));

        System.out.println("--- Produtos de GAMES ---");
        loja.buscarPorCategoria("GAMES").forEach(System.out::println);

        System.out.println("--- Patrimônio Total ---");
        System.out.println("Total: " + loja.calcularPatrimonioTotal());

        System.out.println("--- Patrimônio LIVROS ---");
        System.out.println("Total LIVROS: " + loja.calcularTotalPorCategoria("LIVROS"));
    }
}
