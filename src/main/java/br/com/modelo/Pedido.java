package br.com.modelo;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "valor_total")
    private BigDecimal valorTotal = BigDecimal.ZERO;
    private LocalDate data = LocalDate.now();

    @ManyToOne(fetch= FetchType.LAZY)
    private Cliente cliente;

    public List<ItemPedido> getItens() {
        return itens;
    }

    public void setItens(List<ItemPedido> itens) {
        this.itens = itens;
    }

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL) //indicando que esse relacionamento já está mapeado, evitando a criação de mais uma tabela. sempre colocar no OneToMany
    private List<ItemPedido> itens =  new ArrayList<>();

    public Pedido() {
    }

    public Pedido(Cliente cliente) {
         this.cliente = cliente;
    }

    public void adicionarItem(ItemPedido itemPedido) {
        itemPedido.setPedido(this);
        this.itens.add(itemPedido);
        this.valorTotal = this.valorTotal.add(itemPedido.getValor());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
