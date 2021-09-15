package br.com.javafood.domain.restaurante;

import br.com.javafood.domain.pedido.PedidoItemCardapio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {

    public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer restauranteId);

    @Query("SELECT DISTINCT ic FROM ItemCardapio ic WHERE ic.restaurante.id = ?1" )
    public List<ItemCardapio> listItems(Integer restauranteId);
}
