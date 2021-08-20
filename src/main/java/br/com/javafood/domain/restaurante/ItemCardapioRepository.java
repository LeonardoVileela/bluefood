package br.com.javafood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {

    public List<ItemCardapio> findByRestaurante_IdOrderByNome(Integer restauranteId);
}
