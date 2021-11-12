package br.com.javafood.domain.trigger;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Entity
@Data
public class triggerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O numero não pode ser vazio")
    private String message;

    @NotBlank(message = "O nome não pode ser vazio")
    private String method;
}
