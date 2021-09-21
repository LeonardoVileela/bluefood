package br.com.javafood.domain.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardRepository extends JpaRepository<Card, Integer> {

    @Query("SELECT DISTINCT ic FROM Card ic WHERE ic.cliente.id = ?1" )
    public Card findByClienteId(Integer clienteId);

}
