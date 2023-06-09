package br.com.dao;

import br.com.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Predicate;

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

    public Produto buscarPorId(Long id) {
        return entityManager.find(Produto.class, id);
    }

    public List<Produto> buscarTodos() {
        String jpql = "SELECT p FROM Produto p";
        return entityManager.createQuery(jpql, Produto.class)
                .getResultList();
    }

    public List<Produto> buscarPorNome(String nome) {
        String jpql = "SELECT p FROM Produto p WHERE p.nome = ?1";
        return entityManager.createQuery(jpql, Produto.class)
                .setParameter(1, nome)
                .getResultList();
    }

    public List<Produto> buscarPorNomeDaCategoria(String nome) {
         return entityManager.createNamedQuery("Produto.produtosPorCategoria", Produto.class)
                .setParameter("nome", nome)
                .getResultList();
    }

    public BigDecimal buscarPrecoDoProdutoComNome(String nome) {
        String jpql = "SELECT p.preco FROM Produto p WHERE p.nome = ?1";
        return entityManager.createQuery(jpql, BigDecimal.class)
                .setParameter(1, nome)
                .getSingleResult();
    }

    //Consulta com parâmetros dinâmicos
    public List<Produto> buscarPorParametros(String nome, BigDecimal preco, LocalDate dataCadastro) {
        String jpql ="SELECT p FROM Produto p WHERE 1=1";
        if (nome != null && !nome.trim().isEmpty()) {
            jpql = "AND p.nome = :nome";
        }
        if (preco != null ) {
            jpql = "AND p.preco = :preco";
        }
        if (preco != null ) {
            jpql = "AND p.dataCadastro = :dataCadastro";
        }

        TypedQuery<Produto> query = entityManager.createQuery(jpql, Produto.class);

        if (nome != null && !nome.trim().isEmpty()) {
           query.setParameter("nome", nome);
        }
        if (preco != null ) {
            query.setParameter("preco", preco);
        }
        if (dataCadastro != null ) {
            query.setParameter("dataCadastro", dataCadastro);
        }

        return query.getResultList();
    }

    public List<Produto> buscarPorParametrosComCriteria(String nome, BigDecimal preco, LocalDate dataCadastro) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Produto> query = builder.createQuery(Produto.class);
        Root<Produto> from = query.from(Produto.class);

        Predicate filtros = builder.and();

        if (nome != null && !nome.trim().isEmpty()) {
            filtros =builder.and(filtros, builder.equal(from.get("nome"), nome));
        }
        if (preco != null ) {
            filtros = builder.and(filtros, builder.equal(from.get("preco"), preco));
        }
        if (dataCadastro != null ) {
            filtros = builder.and(filtros, builder.equal(from.get("dataCadastro"), dataCadastro));
        }
        query.where(filtros);

        return entityManager.createQuery(query)
                .getResultList();
    }
}
