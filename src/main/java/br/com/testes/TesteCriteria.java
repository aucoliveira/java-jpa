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
import java.time.LocalDate;
import java.util.List;

public class TesteCriteria {

    public static void main(String[] args) {
        popularBancoDedados();

        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoTesteCriteriaDAO = new ProdutoDAO(entityManager);
        produtoTesteCriteriaDAO.buscarPorParametrosComCriteria("PS%", null, null);
        produtoTesteCriteriaDAO.buscarPorParametrosComCriteria("PS%", null, LocalDate.now());
        produtoTesteCriteriaDAO.buscarPorParametrosComCriteria(null, null, LocalDate.now());

    }

    private static void popularBancoDedados() {
        Categoria celulares = new Categoria("CELULARES");
        Categoria videoGames = new Categoria("VIDEOGAMES");
        Categoria informatica = new Categoria("INFORMÁTICA");

        Produto celular = new Produto("Xiomi REdmi","Muito bom",
                new BigDecimal("1000"), celulares);

        Produto videoGame = new Produto("PS5","Excelentes gráficos",
                new BigDecimal("5000"), videoGames);

        Produto macbook = new Produto("MAcBokk","Uma máquina poderosa",
                new BigDecimal("15000"), informatica);


        EntityManager entityManager = JPAUtil.getEntityManager();

        ProdutoDAO produtoDAO = new ProdutoDAO(entityManager);
        CategoriaDAO categoriaDAO = new CategoriaDAO(entityManager);
        Cliente cliente = new Cliente("Augusto", "123456");
        ClienteDAO clienteDAO = new ClienteDAO(entityManager);

        Pedido pedido = new Pedido(cliente);

        entityManager.getTransaction().begin(); //Iniciando a transação
        clienteDAO.cadastrar(cliente);
        categoriaDAO.cadastrar(celulares);
        categoriaDAO.cadastrar(videoGames);
        categoriaDAO.cadastrar(informatica);

        produtoDAO.cadastrar(celular);
        produtoDAO.cadastrar(videoGame);
        produtoDAO.cadastrar(macbook);
        pedido.adicionarItem(new ItemPedido(10,pedido, celular));
        pedido.adicionarItem(new ItemPedido(40,pedido, videoGame));
        pedido.adicionarItem(new ItemPedido(40,pedido, macbook));
        entityManager.getTransaction().commit(); // enviando a operação
        entityManager.close(); // fechando
    }
}
