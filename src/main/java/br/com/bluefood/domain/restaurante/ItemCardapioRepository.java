package br.com.bluefood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemCardapioRepository extends JpaRepository<ItemCardapio, Integer> {

    public ItemCardapio findByNome(String nome);

}
