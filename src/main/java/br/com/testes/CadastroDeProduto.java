package br.com.testes;

import br.com.modelo.Produto;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.math.BigDecimal;

public class CadastroDeProduto {

    public static void main(String[] args) {

        Produto celular = new Produto();
        celular.setNome("Xiomi REdmi");
        celular.setDescricao("Muito bom");
        celular.setPreco(new BigDecimal("1000"));

        EntityManagerFactory factory = Persistence
                .createEntityManagerFactory("loja");

        EntityManager entityManager = factory.createEntityManager();

        entityManager.getTransaction().begin(); //Iniciando a operação
        entityManager.persist(celular);
        entityManager.getTransaction().commit(); // enviando a operação
        entityManager.close(); // fechando
    }
}
