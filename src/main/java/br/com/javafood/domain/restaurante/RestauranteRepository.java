package br.com.javafood.domain.restaurante;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestauranteRepository extends JpaRepository<Restaurante, Integer> {
    public Restaurante findByEmail(String email);

    //IgnoreCase ignora maiousculo e minusculo
    //Containing qualquer letra que contenha no nome
    public List<Restaurante> findByNomeIgnoreCaseContaining(String nome);

    public List<Restaurante> findByCategorias_Id(Integer categoriaId);
}
