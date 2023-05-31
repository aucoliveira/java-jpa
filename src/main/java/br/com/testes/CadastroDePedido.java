package br.com.testes;

import br.com.dao.CategoriaDAO;
import br.com.dao.ClienteDAO;
import br.com.dao.PedidoDAO;
import br.com.dao.ProdutoDAO;
import br.com.modelo.*;
import br.com.util.JPAUtil;
import br.com.vo.RelatorioDeVendasVo;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

public class CadastroDePedido {

    public static void main(String[] args) {
        popularBancoDedados();

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        Produto produto = produtoDAO.buscarPorId(1l);
        Produto produto2 = produtoDAO.buscarPorId(2l);
        Produto produto3 = produtoDAO.buscarPorId(3l);

        ClienteDAO clienteDAO = new ClienteDAO(entityManager);
        Cliente cliente = clienteDAO.buscarPorId(1l);

        entityManager.getTransaction().begin();

        Pedido pedido = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(10,pedido, produto));
        pedido.adicionarItem(new ItemPedido(40,pedido, produto2));

        Pedido pedido2 = new Pedido(cliente);
        pedido.adicionarItem(new ItemPedido(2, pedido2, produto3));


        PedidoDAO pedidoDAO = new PedidoDAO(entityManager);
        pedidoDAO.cadastrar(pedido);

        entityManager.getTransaction().commit();

        BigDecimal totalVendido = pedidoDAO.valorTotalVendido();
        System.out.println("O valor Total vendido é: "+totalVendido);

        List<RelatorioDeVendasVo> relatorio = pedidoDAO.relatorioDeVendas();
        relatorio.forEach(System.out::println);
    }

    private static void popularBancoDedados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videoGames = new Categoria("VIDEOGAMES");
        Categoria informatica = new Categoria("INFORMÁTICA");

        Produto celular = new Produto("Xiomi REdmi","Muito bom",new BigDecimal("1000"), celulares);
        Produto videoGame = new Produto("PS5","Excelentes gráficos",new BigDecimal("5000"), videoGames);
        Produto macbook = new Produto("MAcBokk","Uma máquina poderosa",new BigDecimal("15000"), informatica);

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        Cliente cliente = new Cliente("Augusto", "123456");
        ClienteDAO clienteDAO = new ClienteDAO(entityManager);


        entityManager.getTransaction().begin(); //Iniciando a transação
        clienteDAO.cadastrar(cliente);
        categoriaDAO.cadastrar(celulares);
        categoriaDAO.cadastrar(videoGames);
        categoriaDAO.cadastrar(informatica);

        produtoDAO.cadastrar(celular);
        produtoDAO.cadastrar(videoGame);
        produtoDAO.cadastrar(macbook);
        entityManager.getTransaction().commit(); // enviando a operação
        entityManager.close(); // fechando
    }
}
