package br.com.javafood.domain.cliente;

import br.com.javafood.domain.restaurante.ItemCardapio;
import br.com.javafood.domain.usuario.Usuario;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@Entity
public class Cliente extends Usuario {

    @NotBlank(message = "O CPF não pode ser vazio")
    @Column(length = 14, nullable = false) //tamanho do campo no banco
    private String cpf;

    @NotBlank(message = "O CPF não pode ser vazio")
    @Column(length = 9) //tamanho do campo no banco
    private String cep;

    @Transient
    private Map<ItemCardapio, Integer> carrinho = new HashMap<>();


    public void addItemCarrinho(ItemCardapio itemCardapio){
        this.carrinho.put(itemCardapio, 1);
    }

    public void removeItemCarrinho(ItemCardapio itemCardapio){
        this.carrinho.remove(itemCardapio);
    }

    public void esvaziarCarrinho(){
        carrinho.clear();
    }

    public Map<ItemCardapio, Integer> getItemsCarrinho(){
        return this.carrinho;
    }

    public BigDecimal totalCarrinho(){
        BigDecimal soma = BigDecimal.valueOf(0.00);
        for (var car : this.carrinho.entrySet()) {
            soma = soma.add(car.getKey().getPreco().multiply(BigDecimal.valueOf(car.getValue())));
        }
        return soma;
    }

    public boolean contain(ItemCardapio itemCardapio){

        for (var car : this.carrinho.entrySet()) {
            if (car.getKey().getId() == itemCardapio.getId()){
                return true;
            }
        }

        return false;

    }

    public void addExisting(int id){
        for (var car : this.carrinho.entrySet()) {
            if (car.getKey().getId() == id){
                car.setValue(car.getValue() + 1);
            }
        }

    }

    public void quitExisting(int id){
        for (var car : this.carrinho.entrySet()) {
            if (car.getKey().getId() == id){
                if(car.getValue() != 1){
                    car.setValue(car.getValue() - 1);
                }
            }
        }

    }

}
