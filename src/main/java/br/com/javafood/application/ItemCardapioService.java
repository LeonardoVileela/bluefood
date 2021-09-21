package br.com.javafood.application;

import br.com.javafood.domain.restaurante.ItemCardapio;
import br.com.javafood.domain.restaurante.ItemCardapioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ItemCardapioService {
    //injeção de dependencias de imagem service
    @Autowired
    private ImageService imageService;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

    //Sempre que executar algum tipo de função que modifica o banco de dados
    //deve ser colocado a anotação @Transactional, ele faz com que se uma das
    //operações não funcionar, cancela todas as outras, isso faz com que o banco
    //fique consistente
    @Transactional
    public void save(ItemCardapio itemCardapio) throws ValidationException{

            //adiciona nome do arquivo da logo
            itemCardapioRepository.save(itemCardapio);
            String logotipoName = itemCardapio.setImagemFileName();
            itemCardapio.setLogotipo(logotipoName);
            //faz o upload do arquivo

            imageService.uploadItemCardapio(itemCardapio.getLogotipoFile(),itemCardapio.getLogotipo());
            itemCardapioRepository.save(itemCardapio);

    }


    public List<ItemCardapio> searchByRestauranteid(Integer search){
        return itemCardapioRepository.findByRestaurante_IdOrderByNome(search);
    }

    public ItemCardapio searchById(Integer id){
        return itemCardapioRepository.findById(id).get();
    }

}
