package br.com.dao;

import br.com.modelo.Categoria;
import br.com.modelo.Produto;

import javax.persistence.EntityManager;

public class ProdutoDAO {

    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Produto produto) {
        this.entityManager.persist(produto);
    }

    public void atualizar(Produto produto) {
        this.entityManager.merge(produto);
    }

    public void remover(Produto produto) {
        produto = entityManager.merge(produto);
        this.entityManager.remove(produto);
    }
}
