package br.com.bookstore.main;

import br.com.bookstore.dao.LivroDAO;
import br.com.bookstore.dao.LivroDAOSqlite;
import br.com.bookstore.model.Livro;

public class Main {
    public static void main(String[] args) {
        // 1. Instanciar aplicando Inversão de Dependência (DIP)
        LivroDAO dao = new LivroDAOSqlite();

        // 2. Persistir 3 obras literárias
        Livro l1 = new Livro("Clean Code", "Robert C. Martin", 89.90);
        Livro l2 = new Livro("Design Patterns", "Erich Gamma et al.", 120.00);
        Livro l3 = new Livro("Refactoring", "Martin Fowler", 95.50);

        dao.salvar(l1);
        dao.salvar(l2);
        dao.salvar(l3);

        System.out.println("=== Livros Salvos no SQLite ===");
        System.out.println(l1);
        System.out.println(l2);
        System.out.println(l3);

        // 3. Consultar ID 2 com ifPresentOrElse
        System.out.println("\n=== Consulta por ID 2 ===");
        dao.buscarPorId(2L).ifPresentOrElse(
            livro -> System.out.println("Encontrado: " + livro),
            () -> System.out.println("Livro não encontrado.")
        );

        // 4. Consultar ID inexistente (999L) - livre de NullPointerException
        System.out.println("\n=== Consulta ID Inexistente (999L) ===");
        dao.buscarPorId(999L).ifPresentOrElse(
            livro -> System.out.println("Encontrado: " + livro),
            () -> System.out.println("Livro não encontrado (tratado com Optional com segurança).")
        );

        // 5. Listar todos ordenados
        System.out.println("\n=== Listagem Completa (listarTodos) ===");
        System.out.println("----------------------------------------------------------------------");
        dao.listarTodos().forEach(System.out::println);
        System.out.println("----------------------------------------------------------------------");
    }
}
