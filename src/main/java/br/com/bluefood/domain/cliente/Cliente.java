package br.com.bluefood.domain.cliente;

import br.com.bluefood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Cliente extends Usuario {

    @NotBlank(message = "O CPF não pode ser vazio")
    @Pattern(regexp = "[0-9]{11}", message = "O CPF possui formato inválido")
    @Column(length = 11, nullable = false) //tamanho do campo no banco
    private String cpf;

    @NotBlank(message = "O CPF não pode ser vazio")
    @Pattern(regexp = "[0-9]{8}", message = "O CPF possui formato inválido") //validação, aceita somente números de 0 a 9 e no maximo 8 digitos
    @Column(length = 8) //tamanho do campo no banco
    private String cep;

}
