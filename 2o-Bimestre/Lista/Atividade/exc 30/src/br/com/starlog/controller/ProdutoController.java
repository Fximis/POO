package br.com.starlog.controller;

import br.com.starlog.dao.ProdutoDAO;
import br.com.starlog.model.Produto;
import java.util.List;

public class ProdutoController {
    private final ProdutoDAO dao;

    public ProdutoController(ProdutoDAO dao) {
        this.dao = dao;
    }

    public void cadastrar(String nome, double preco, int estoque) {
        Produto p = new Produto(nome, preco, estoque);
        dao.salvar(p);
    }

    public List<Produto> obterTodos() {
        return dao.listarTodos();
    }

    public boolean alterar(Long id, String nome, double preco, int estoque) {
        Produto p = new Produto(id, nome, preco, estoque);
        return dao.atualizar(p);
    }

    public boolean remover(Long id) {
        return dao.excluir(id);
    }
}
