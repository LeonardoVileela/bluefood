package br.com.javafood.application;

import br.com.javafood.domain.restaurante.ItemCardapio;
import br.com.javafood.domain.pedido.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoItemCardapioRepository pedidoItemCardapioRepository;

    public void save(Map<ItemCardapio, Integer> carrinho){
        Pedido pedido = new Pedido();
        pedido.setDate(LocalDate.now());
        Pedido novaPedido = pedidoRepository.save(pedido);

        for (var car : carrinho.entrySet()) {
            PedidoItemCardapio pedidoItemCardapio = new PedidoItemCardapio();
            pedidoItemCardapio.setPedido(novaPedido);
            pedidoItemCardapio.setItemCardapio(car.getKey());
            pedidoItemCardapio.setQuantidade(car.getValue());
            pedidoItemCardapio.setId(new PedidoItemCardapioPK(novaPedido.getId(), car.getKey().getId()));
            pedidoItemCardapioRepository.save(pedidoItemCardapio);
        }
    }
}
