package br.com.javafood.domain.pedido;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Entity
@Getter
@Setter
public class Pedido {

    public Pedido(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal total;

    @NotNull(message = "A data não pode ser vazio")
    private LocalDate date;

    @OneToMany(mappedBy = "itemCardapio")
    private Set<PedidoItemCardapio> pedidoItemCardapio = new HashSet<PedidoItemCardapio>();
}
