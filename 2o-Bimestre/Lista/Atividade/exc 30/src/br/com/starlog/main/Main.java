package br.com.starlog.main;

import br.com.starlog.dao.ProdutoDAO;
import br.com.starlog.dao.ProdutoDAOSqlite;
import br.com.starlog.model.Produto;

public class Main {
    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAOSqlite();

        // 1. [Create]: Cadastrar dois itens e mostrar os IDs autoincrement gerados
        System.out.println("=== 1. [CREATE] Cadastrando Produtos no Almoxarifado ===");
        Produto p1 = new Produto("Teclado Mecânico RGB", 320.00, 10);
        Produto p2 = new Produto("Mousepad Speed XL", 89.90, 25);

        dao.salvar(p1);
        dao.salvar(p2);

        System.out.println("Cadastrado com ID gerado (#" + p1.getId() + "): " + p1);
        System.out.println("Cadastrado com ID gerado (#" + p2.getId() + "): " + p2);

        // 2. [Read]: Listar todos os produtos com exibição limpa em colunas
        System.out.println("\n=== 2. [READ] Catálogo Completo (Ordenado por Nome) ===");
        System.out.println("----------------------------------------------------------------------");
        dao.listarTodos().forEach(System.out::println);
        System.out.println("----------------------------------------------------------------------");

        // 3. [Update]: Alterar estoque do Teclado para 15 unidades e preço para 289.90
        System.out.println("\n=== 3. [UPDATE] Atualizando Preço e Estoque do Teclado ===");
        p1.setPreco(289.90);
        p1.setEstoque(15);
        boolean atualizado = dao.atualizar(p1);
        System.out.println("Status da atualização (ID #" + p1.getId() + "): " + atualizado);
        dao.buscarPorId(p1.getId()).ifPresent(
            prod -> System.out.println("Registro atualizado no banco: " + prod)
        );

        // 4. [Delete]: Remover o Mousepad por ID e testar ID inexistente (888L)
        System.out.println("\n=== 4. [DELETE] Exclusão de Produto e Teste de ID Inexistente ===");
        boolean excluidoMousepad = dao.excluir(p2.getId());
        System.out.println("Exclusão do Mousepad (ID #" + p2.getId() + ") retornou: " + excluidoMousepad);

        boolean excluidoInexistente = dao.excluir(888L);
        System.out.println("Tentativa de exclusão de ID inexistente (888L) retornou: " + excluidoInexistente + " (tratado sem falhas)");

        // 5. [Final State]: Executar listarTodos() e mostrar que restou exatamente 1 produto
        System.out.println("\n=== 5. [FINAL STATE] Estado Final do Banco de Dados ===");
        System.out.println("----------------------------------------------------------------------");
        var restantes = dao.listarTodos();
        restantes.forEach(System.out::println);
        System.out.println("----------------------------------------------------------------------");
        System.out.println("Total de produtos restantes no banco: " + restantes.size());
    }
}
