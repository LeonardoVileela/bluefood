package br.com.javafood.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoItemCardapioRepository extends JpaRepository<PedidoItemCardapio, Integer> {

    @Query("SELECT ic FROM PedidoItemCardapio ic WHERE ic.itemCardapio.restaurante.id = ?1 AND ic.status = 'Pendente' AND ic.pedido.id = ?2" )
    public List<PedidoItemCardapio> findItemsPedidosRestaurante(Integer restauranteId, Integer pedidoId);

    @Query("SELECT DISTINCT ic.pedido FROM PedidoItemCardapio ic WHERE ic.itemCardapio.restaurante.id = ?1 AND ic.status = 'Pendente'" )
    public List<Pedido> findPedidos(Integer restauranteId);

    @Query("SELECT DISTINCT ic.pedido FROM PedidoItemCardapio ic WHERE ic.pedido.cliente.id = ?1 AND ic.status = ?2" )
    public List<Pedido> findPedidosCliente(Integer clienteId, String status);

    @Query("SELECT ic FROM PedidoItemCardapio ic WHERE ic.pedido.id = ?1" )
    public List<PedidoItemCardapio> findItemsPedidoCliente(Integer pedidoId);

    @Query("SELECT DISTINCT ic FROM PedidoItemCardapio ic WHERE ic.pedido.id = ?1" )
    public List<PedidoItemCardapio> findPedido(Integer pedidoId);

    @Query("SELECT DISTINCT ic.pedido FROM PedidoItemCardapio ic WHERE ic.itemCardapio.restaurante.id = ?1" )
    public List<Pedido> listPedidosItems(Integer restauranteId);


}
