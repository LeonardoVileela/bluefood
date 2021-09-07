package br.com.javafood.domain.pedido;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoItemCardapioRepository extends JpaRepository<PedidoItemCardapio, Integer> {

    @Query("SELECT ic FROM PedidoItemCardapio ic WHERE ic.itemCardapio.restaurante.id = ?1 AND ic.status = 'Pendente'" )
    public List<PedidoItemCardapio> findPedidos(Integer restauranteId);
}
