package br.com.javafood.domain.pedido;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class PedidoItemCardapioPK implements Serializable {

    @Column(name = "PEDIDO_ID")
    private Integer pedido_id;

    @Column(name = "ITEM_CARDAPIO_ID")
    private Integer itemCardapio_id;

    public PedidoItemCardapioPK(Integer pedido_id, Integer itemCardapio_id) {
        this.pedido_id = pedido_id;
        this.itemCardapio_id = itemCardapio_id;
    }

    public PedidoItemCardapioPK() {
    }
}
