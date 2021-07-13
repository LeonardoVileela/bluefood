package br.com.bluefood.application;

import br.com.bluefood.domain.cliente.Cliente;
import br.com.bluefood.domain.cliente.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public void save(Cliente cliente) throws ValidationException{

        //chama o método pra verificar se o email está duplicado
       if(!validateEmail(cliente.getEmail(), cliente.getId())){
           throw new ValidationException("O e-mail está duplicado");
       }

       //se cliente for uma edição ele vai buscar pelo id pegar a senha do banco e colocar
        //se for criação ele faz a criptografia da senha
       if (cliente.getEmail() == null){
           //orElseThrow retorna o cliente, se não retornar nada ele lança uma exeption
           //Optional retorna algo ou Null
           Cliente clienteDB = clienteRepository.findById(cliente.getId()).orElseThrow();

           cliente.setSenha(clienteDB.getSenha());
       }else {
           cliente.encryptPassword();
       }

        clienteRepository.save(cliente);
    }

    //verifica se o email está duplicado
    private boolean validateEmail(String email, Integer id) {
        Cliente cliente = clienteRepository.findByEmail(email);

        if(cliente != null){
            if(id == null){
                return false;
            }

            if (!cliente.getId().equals(id)){
                return false;
            }
        }

        return true;
    }
}
