package br.com.javafood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {

    @Query("SELECT DISTINCT ic FROM ItemCardapio ic WHERE ic.restaurante.id = ?1 AND ic.ativo = true order by ic.nome" )
    public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer restauranteId);

    @Query("SELECT DISTINCT ic FROM ItemCardapio ic WHERE ic.restaurante.id = ?1 AND ic.ativo = true" )
    public List<ItemCardapio> listItems(Integer restauranteId);

    @Query("SELECT DISTINCT ic FROM ItemCardapio ic WHERE ic.restaurante.id = ?1 AND ic.ativo = false" )
    public List<ItemCardapio> listItemsDesativados(Integer restauranteId);
}
