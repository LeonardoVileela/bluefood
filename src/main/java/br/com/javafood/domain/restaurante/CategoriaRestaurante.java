package br.com.javafood.domain.restaurante;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // EqualsAndHashCode vão ser gerados somente quando eu deixar explcito
@Entity
@Table(name = "categoria_restaurante")
public class CategoriaRestaurante implements Serializable {

    public CategoriaRestaurante(String nome, String imagem) {
        this.nome = nome;
        this.imagem = imagem;
    }

    public CategoriaRestaurante( ) {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include //Deixo explicito o uso do EqualsAndHashCode
    private Integer id;

    @NotBlank
    @Size(max = 20)
    private String nome;

    @NotBlank
    @Size(max = 50)
    private String imagem;

    @ManyToMany(mappedBy = "categorias")
    private Set<Restaurante> restaurante = new HashSet<>(0);




}
