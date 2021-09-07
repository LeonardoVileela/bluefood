package br.com.javafood.domain.pedido;

import br.com.javafood.domain.restaurante.ItemCardapio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PedidoItemCardapio")
@Getter
@Setter
public class PedidoItemCardapio implements Serializable {

    @EmbeddedId
    private PedidoItemCardapioPK id;

    @ManyToOne
    @MapsId("pedido_id") //This is the name of attr in EmployerDeliveryAgentPK class
    @JoinColumn(name = "PEDIDO_ID")
    private Pedido pedido;

    @ManyToOne
    @MapsId("itemCardapio_id")
    @JoinColumn(name = "ITEM_CARDAPIO_ID")
    private ItemCardapio itemCardapio;


    private Integer quantidade;

    private String status = "Pendente";

}
