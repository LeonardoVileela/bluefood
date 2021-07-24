package br.com.bluefood.application;

import br.com.bluefood.domain.cliente.Cliente;
import br.com.bluefood.domain.cliente.ClienteRepository;
import br.com.bluefood.domain.restaurante.Restaurante;
import br.com.bluefood.domain.restaurante.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    //injeção de dependencias de imagem service
    @Autowired
    private ImageService imageService;

    //Sempre que executar algum tipo de função que modifica o banco de dados
    //deve ser colocado a anotação @Transactional, ele faz com que se uma das
    //operações não funcionar, cancela todas as outras, isso faz com que o banco
    //fique consistente
    @Transactional
    public void save(Restaurante restaurante) throws ValidationException{

        //chama o método pra verificar se o email está duplicado
        if(!validateEmail(restaurante.getEmail(), restaurante.getId())){
            throw new ValidationException("O e-mail está duplicado");
        }

        //se cliente for uma edição ele vai buscar pelo id pegar a senha do banco e colocar
        //se for criação ele faz a criptografia da senha
        if (restaurante.getEmail() == null){
            //orElseThrow retorna o cliente, se não retornar nada ele lança uma exeption
            //Optional retorna algo ou Null
            Restaurante restauranteDB = restauranteRepository.findById(restaurante.getId()).orElseThrow();

            restaurante.setSenha(restauranteDB.getSenha());
        }else {
            restaurante.encryptPassword();
            restauranteRepository.save(restaurante);
            //adiciona nome do arquivo da logo
            String logotipoName = restaurante.setLogotipoFileName();
            restaurante.setLogotipo(logotipoName);
            System.out.println(logotipoName);
            //faz o upload do arquivo
            imageService.uploadLogotipo(restaurante.getLogotipoFile(),restaurante.getLogotipo());
            restauranteRepository.save(restaurante);
        }


    }

    //verifica se o email está duplicado
    private boolean validateEmail(String email, Integer id) {
        Restaurante restaurante = restauranteRepository.findByEmail(email);

        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente != null){
            return false;
        }

        if(restaurante != null){
            if(id == null){
                return false;
            }

            if (!restaurante.getId().equals(id)){
                return false;
            }
        }

        return true;
    }
}
