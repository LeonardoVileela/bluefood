package br.com.bluefood.domain.restaurante;

import br.com.bluefood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
@Table(name = "restaurante")
public class Restaurante extends Usuario {

    @NotBlank(message = "O CNPJ não pode ser vazio")
    @Pattern(regexp = "[0-9]{14}", message = "O CNPJ possui formato inválido")
    @Column(length = 14, nullable = false) //tamanho do campo no banco
    private String cnpj;

    @Size(max = 80)
    private String logotipo;

    @NotNull(message = "A taxa de entrega não pode ser vazia")
    @Max(99)
    @Min(0)
    private BigDecimal taxaEntrega;

    @Max(120)
    @Min(0)
    private Integer tempoEntregaBase;

    @ManyToMany
    @JoinTable(
            name = "restaurante_has_categoria",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "categoria_restaurante_id")
    ) //faço a configuração do muitos pra muitos, de um lado não preciso fazer do outro
    @Size(min = 1, message = "O Restaurante precisa ter pelo menos uma categoria")
    @ToString.Exclude //marcação IMPORTANTEEE Lombok buga o to String em atributos que tem relacionamento, sempre colocar essa marcação
    private Set<CategoriaRestaurante> categorias = new HashSet<>(0);

}
