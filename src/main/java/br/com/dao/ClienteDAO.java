package br.com.dao;

import br.com.modelo.Cliente;
import br.com.modelo.Pedido;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class ClienteDAO {

    private EntityManager entityManager;

    public ClienteDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void cadastrar(Cliente cliente) {
        this.entityManager.persist(cliente);
    }

    public void atualizar(Cliente cliente) {
        this.entityManager.merge(cliente);
    }

    public void remover(Cliente cliente) {
        cliente = entityManager.merge(cliente);
        this.entityManager.remove(cliente);
    }

    public Cliente buscarPorId(Long id) {
        return entityManager.find(Cliente.class, id);
    }

    public List<Cliente> buscarTodos() {
        String jpql = "SELECT p FROM Cliente c";
        return entityManager.createQuery(jpql, Cliente.class)
                .getResultList();
    }

    public List<Cliente> buscarPorNome(String nome) {
        String jpql = "SELECT p FROM Cliente c  WHERE p.nome = ?1";
        return entityManager.createQuery(jpql, Cliente.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public List<Cliente> buscarPorNomeDaCategoria(String nome) {
        String jpql = "SELECT p FROM Cliente c WHERE p.categoria.nome = ?1";
        return entityManager.createQuery(jpql, Cliente.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoDoPedidoComNome(String nome) {
        String jpql = "SELECT p.preco FROM Cliente c WHERE p.nome = ?1";
        return entityManager.createQuery(jpql, BigDecimal.class)
                .setParameter(1, nome)
                .getSingleResult();
    }
}
