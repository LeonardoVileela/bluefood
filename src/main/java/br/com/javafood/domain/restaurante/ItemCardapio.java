package br.com.javafood.domain.restaurante;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.javafood.domain.pedido.PedidoItemCardapio;
import br.com.javafood.infrastructure.web.validator.UploadConstraint;
import br.com.javafood.util.FileType;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_cardapio")
@Getter
@Setter
public class ItemCardapio implements Serializable {


    public ItemCardapio() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O nome não pode ser vazio")
    @Size(max = 50)
    private String nome;

    @NotBlank(message = "A categoria não pode ser vazia")
    @Size(max = 25)
    private String categoria;

    @NotBlank(message = "A descrição não pode ser vazia")
    @Size(max = 80)
    private String descricao;

    private Boolean ativo = true;

    @Size(max = 80)
    private String logotipo;

    @UploadConstraint(acceptedTypes = {FileType.PNG, FileType.JPG}, message = "Formato de arquivo não aceito")
    private transient MultipartFile logotipoFile;

    @NotNull(message = "O preço não pode ser vazio")
    @Min(0)
    private BigDecimal preco;

    @ManyToOne
    @JoinColumn(name = "restaurante_id")
    private Restaurante restaurante;

    @OneToMany(mappedBy = "pedido", fetch = FetchType.EAGER)
    private Set<PedidoItemCardapio> pedido = new HashSet<PedidoItemCardapio>();


    public String setImagemFileName() {
        if (getId() == null) {
            throw new IllegalStateException("é preciso primeiro gravar o registro");
        }

        return String.format("%04d-item.%s", getId(), FileType.of(logotipoFile.getContentType()).getExtension());

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCardapio that = (ItemCardapio) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}