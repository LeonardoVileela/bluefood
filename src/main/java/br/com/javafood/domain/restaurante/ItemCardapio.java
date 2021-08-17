package br.com.javafood.domain.restaurante;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.com.javafood.infrastructure.web.validator.UploadConstraint;
import br.com.javafood.util.FileType;
import org.springframework.web.multipart.MultipartFile;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "item_cardapio")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
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

    @UploadConstraint(acceptedTypes = FileType.PNG, message = "O arquivo não é um arquivo de imagem válido")
    private transient MultipartFile imagemFile;


    public String setImagemFileName() {
        if (getId() == null) {
            throw new IllegalStateException("é preciso primeiro gravar o registro");
        }

        return String.format("%04d-item.%s", getId(), FileType.of(logotipoFile.getContentType()).getExtension());

    }

}