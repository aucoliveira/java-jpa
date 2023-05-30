package br.com.dao;

import br.com.modelo.Pedido;
import br.com.modelo.Produto;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class PedidoDAO {

    private EntityManager entityManager;

    public PedidoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Pedido pedido) {
        this.entityManager.persist(pedido);
    }

    public void atualizar(Pedido pedido) {
        this.entityManager.merge(pedido);
    }

    public void remover(Pedido pedido) {
        pedido = entityManager.merge(pedido);
        this.entityManager.remove(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return entityManager.find(Pedido.class, id);
    }

    public List<Pedido> buscarTodos() {
        String jpql = "SELECT p FROM Pedido p";
        return entityManager.createQuery(jpql, Pedido.class)
                .getResultList();
    }

    public List<Pedido> buscarPorNome(String nome) {
        String jpql = "SELECT p FROM Pedido p WHERE p.nome = ?1";
        return entityManager.createQuery(jpql, Pedido.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public List<Pedido> buscarPorNomeDaCategoria(String nome) {
        String jpql = "SELECT p FROM Pedido p WHERE p.categoria.nome = ?1";
        return entityManager.createQuery(jpql, Pedido.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoDoPedidoComNome(String nome) {
        String jpql = "SELECT p.preco FROM Pedido p WHERE p.nome = ?1";
        return entityManager.createQuery(jpql, BigDecimal.class)
                .setParameter(1, nome)
                .getSingleResult();
    }
}
