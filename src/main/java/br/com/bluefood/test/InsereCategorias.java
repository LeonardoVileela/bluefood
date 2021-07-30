package br.com.bluefood.test;

import br.com.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class InsereCategorias {

    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Autowired
    public InsereCategorias(CategoriaRestauranteRepository categoriaRestauranteRepository) {
        this.categoriaRestauranteRepository = categoriaRestauranteRepository;
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        CategoriaRestaurante categoriaRestaurante = new CategoriaRestaurante("Pizza", "0001-categoria.png");
        CategoriaRestaurante categoriaRestaurante2 = new CategoriaRestaurante("Sanduíche", "0002-categoria.png");
        CategoriaRestaurante categoriaRestaurante3 = new CategoriaRestaurante("Churrasco", "0003-categoria.png");
        CategoriaRestaurante categoriaRestaurante4 = new CategoriaRestaurante("Salada", "0004-categoria.png");
        CategoriaRestaurante categoriaRestaurante5 = new CategoriaRestaurante("Sobremesa", "0005-categoria.png");
        CategoriaRestaurante categoriaRestaurante6 = new CategoriaRestaurante("Japonês", "0006-categoria.png");

        CategoriaRestaurante categoriaRestauranteDB = categoriaRestauranteRepository.findByNome("Pizza");
        boolean duplicate = false;

        if (categoriaRestauranteDB != null) {
           duplicate = true;
        }

        if (!duplicate) {
            categoriaRestauranteRepository.save(categoriaRestaurante);
            categoriaRestauranteRepository.save(categoriaRestaurante2);
            categoriaRestauranteRepository.save(categoriaRestaurante3);
            categoriaRestauranteRepository.save(categoriaRestaurante4);
            categoriaRestauranteRepository.save(categoriaRestaurante5);
            categoriaRestauranteRepository.save(categoriaRestaurante6);

        }

    }


}
