/*package br.com.bluefood.test;

import br.com.bluefood.domain.restaurante.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.Optional;

@Component
public class InsereItemCardapio {

    private ItemCardapioRepository itemCardapioRepository;

    private RestauranteRepository restauranteRepository;

    @Autowired
    public InsereItemCardapio(ItemCardapioRepository itemCardapioRepository, RestauranteRepository restauranteRepository) {
        this.itemCardapioRepository = itemCardapioRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Optional<Restaurante> restaurante = restauranteRepository.findById(1);
        ItemCardapio itemCardapio = new ItemCardapio("Sanduíche", "Sanduíche", "Delicioso sanduíche com molho", "555", BigDecimal.valueOf(23.8), false, restaurante.get());
        ItemCardapio itemCardapio2 = new ItemCardapio("Pizza", "Pizza", "Delicioso sanduíche com molho", "555", BigDecimal.valueOf(23.8), false, restaurante.get());
        ItemCardapio itemCardapio3 = new ItemCardapio("Salada", "Salada", "Delicioso sanduíche com molho", "555", BigDecimal.valueOf(23.8), false, restaurante.get());
        ItemCardapio itemCardapio4 = new ItemCardapio("Sushi", "Japonês", "Delicioso sanduíche com molho", "555", BigDecimal.valueOf(23.8), false, restaurante.get());
        ItemCardapio itemCardapio5 = new ItemCardapio("Arroz", "Brasileiro", "Delicioso sanduíche com molho", "555", BigDecimal.valueOf(23.8), false, restaurante.get());
        ItemCardapio itemCardapio6 = new ItemCardapio("Feijão", "Brasileiro", "Delicioso sanduíche com molho", "555", BigDecimal.valueOf(23.8), false, restaurante.get());


        ItemCardapio ItemCardapioDB = itemCardapioRepository.findByNome("Pizza");
        boolean duplicate = false;

        if (ItemCardapioDB != null) {
            duplicate = true;
        }

        if (!duplicate) {
            itemCardapioRepository.save(itemCardapio);
            itemCardapioRepository.save(itemCardapio2);
            itemCardapioRepository.save(itemCardapio3);
            itemCardapioRepository.save(itemCardapio4);
            itemCardapioRepository.save(itemCardapio5);
            itemCardapioRepository.save(itemCardapio6);

        }

    }

}*/
