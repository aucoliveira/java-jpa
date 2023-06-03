package br.com.testes;

import br.com.dao.CategoriaDAO;
import br.com.dao.ProdutoDAO;
import br.com.modelo.Categoria;
import br.com.modelo.CategoriaId;
import br.com.modelo.Produto;
import br.com.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDeProduto {

    public static void main(String[] args) {
        cadastrarProduto();

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        Produto p = produtoDAO.buscarPorId(1l);
       // System.out.println(p.getNome());

       // List<Produto> todos = produtoDAO.buscarTodos();
        //todos.forEach(p2 -> System.out.println(p.getNome()));

        List<Produto> porNome = produtoDAO.buscarPorNome("Xiomi");
        porNome.forEach(p2 -> System.out.println(p.getNome()));

        List<Produto> porNomeDaCategoria = produtoDAO.buscarPorNomeDaCategoria("CELULARES");
        porNomeDaCategoria.forEach(p2 -> System.out.println(p.getNome()));

        BigDecimal precoDoProduto = produtoDAO.buscarPrecoDoProdutoComNome("Xiomi REdmi");
        System.out.println("Preço do Produto: "+precoDoProduto);
    }

    private static void cadastrarProduto() {
        Categoria celulares = new Categoria("CELULARES");

        Produto celular = new Produto("Xiomi REdmi","Muito bom",new BigDecimal("1000"), celulares);

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);

        entityManager.getTransaction().begin(); //Iniciando a transação
        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(celular);
        entityManager.getTransaction().commit(); // enviando a operação

        entityManager.find(Categoria.class, new CategoriaId("CELULARES", "XPTO"));

        entityManager.close(); // fechando
    }
}
