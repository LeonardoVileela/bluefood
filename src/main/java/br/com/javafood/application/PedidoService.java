package br.com.javafood.application;

import br.com.javafood.domain.restaurante.ItemCardapio;
import br.com.javafood.domain.pedido.*;
import br.com.javafood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoItemCardapioRepository pedidoItemCardapioRepository;

    public Integer save(Map<ItemCardapio, Integer> carrinho){
        Pedido pedido = new Pedido();
        pedido.setDate(LocalDate.now());
        pedido.setCliente(SecurityUtils.loggedCliente());

        BigDecimal soma = BigDecimal.valueOf(0.00);
        for (var car : carrinho.entrySet()) {
            soma = soma.add(car.getKey().getPreco().multiply(BigDecimal.valueOf(car.getValue())));
        }

        pedido.setTotal(soma);
        Pedido novaPedido = pedidoRepository.save(pedido);

        for (var car : carrinho.entrySet()) {
            PedidoItemCardapio pedidoItemCardapio = new PedidoItemCardapio();
            pedidoItemCardapio.setPedido(novaPedido);
            pedidoItemCardapio.setItemCardapio(car.getKey());
            pedidoItemCardapio.setQuantidade(car.getValue());
            pedidoItemCardapio.setId(new PedidoItemCardapioPK(novaPedido.getId(), car.getKey().getId()));
            pedidoItemCardapioRepository.save(pedidoItemCardapio);
        }
        return novaPedido.getId();

    }

    public List<Pedido> pedidosCliente(Integer clienteID, Boolean pendente){
        if(pendente){
            return pedidoItemCardapioRepository.findPedidosCliente(clienteID, "Pendente");
        }

        return pedidoItemCardapioRepository.findPedidosCliente(clienteID, "Finalizado");
    }

    public List<PedidoItemCardapio> pedidoCliente(Integer pedidoId){
        return pedidoItemCardapioRepository.findItemsPedidoCliente(pedidoId);
    }
}
