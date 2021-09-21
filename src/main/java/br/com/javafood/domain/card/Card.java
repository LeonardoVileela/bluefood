package br.com.javafood.domain.card;

import br.com.javafood.domain.cliente.Cliente;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O numero não pode ser vazio")
    private String number;

    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    @NotBlank(message = "A data não pode ser vazio")
    private String data;

    @NotBlank(message = "O cvv não pode ser vazio")
    private String cvv;

    @NotBlank(message = "A bandeira não pode ser vazio")
    private String bandeira;

    @OneToOne(fetch = FetchType.EAGER)
    private Cliente cliente;

}
