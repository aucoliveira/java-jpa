package br.com.testes;

import br.com.dao.CategoriaDAO;
import br.com.dao.ProdutoDAO;
import br.com.modelo.Categoria;
import br.com.modelo.Produto;
import br.com.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class CadastroDeProduto {

    public static void main(String[] args) {
        Categoria celulares = new Categoria("CELULARES");

        Produto celular = new Produto("Xiomi REdmi","Muito bom",new BigDecimal("1000"), celulares);

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);

        entityManager.getTransaction().begin(); //Iniciando a transação
        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(celular);
        entityManager.getTransaction().commit(); // enviando a operação
        entityManager.close(); // fechando
    }
}
