package br.com.testes;

import br.com.dao.CategoriaDAO;
import br.com.dao.ClienteDAO;
import br.com.dao.PedidoDAO;
import br.com.dao.ProdutoDAO;
import br.com.modelo.*;
import br.com.util.JPAUtil;

import javax.persistence.EntityManager;
import java.math.BigDecimal;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBancoDedados();

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        Produto produto = produtoDAO.buscarPorId(1l);

        ClienteDAO clienteDAO = new ClienteDAO(entityManager);
        Cliente cliente = clienteDAO.buscarPorId(1l);

        entityManager.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10,pedido, produto));

        PedidoDAO pedidoDAO = new PedidoDAO(entityManager);
        pedidoDAO.cadastrar(pedido);

        entityManager.getTransaction().commit();

    }

    private static void popularBancoDedados() {
        Categoria celulares = new Categoria("CELULARES");

        Produto celular = new Produto("Xiomi REdmi","Muito bom",new BigDecimal("1000"), celulares);

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        Cliente cliente = new Cliente("Augusto", "123456");
        ClienteDAO clienteDAO = new ClienteDAO(entityManager);


        entityManager.getTransaction().begin(); //Iniciando a transação
        clienteDAO.cadastrar(cliente);
        categoriaDAO.cadastrar(celulares);
        produtoDAO.cadastrar(celular);
        entityManager.getTransaction().commit(); // enviando a operação
        entityManager.close(); // fechando
    }
}
