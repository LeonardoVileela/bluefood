package br.com.javafood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRestauranteRepository extends JpaRepository<CategoriaRestaurante, Integer> {

    public CategoriaRestaurante findByNome(String nome);
}
