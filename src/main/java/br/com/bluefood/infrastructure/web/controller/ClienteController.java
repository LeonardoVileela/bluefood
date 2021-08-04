package br.com.bluefood.infrastructure.web.controller;

import br.com.bluefood.application.ClienteService;
import br.com.bluefood.application.ValidationException;
import br.com.bluefood.domain.cliente.Cliente;
import br.com.bluefood.domain.cliente.ClienteRepository;
import br.com.bluefood.domain.restaurante.CategoriaRestaurante;
import br.com.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.bluefood.util.SecurityUtils;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/cliente")
public class ClienteController {

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteService clienteService;

    @GetMapping(path = "/home")
    public String home(Model model){
        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);

        return "cliente-home";
    }

    @GetMapping("/edit")
    public String edit(Model model){
        Integer clienteId = SecurityUtils.loggedCliente().getId(); //pega o id do cliente logado para depois buscar o cliente logado no banco
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(); // busca o cliente logado no banco, se não encontrar lança uma exception
        model.addAttribute("cliente", cliente);//joga o cliente logado pra tela de edição
        ControllerHelper.setEditMode(model,true);//ativa o modo de edição
        return "cliente-cadastro";
    }

    @PostMapping(path = "/save")
    public String save(
            @ModelAttribute("cliente") //recebe um atributo cliente
            @Valid Cliente cliente, //valida se realmente é um cliente
            Errors errors, // trás os erros
            Model model
    ) {


        //verifica se deu erro
        if (!errors.hasErrors()) {
            try {
                clienteService.save(cliente);
                model.addAttribute("msg", "Cliente gravado com sucesso");

            } catch (ValidationException e) {
                //joga a mensagem de erro pro html
                errors.rejectValue("email", null, e.getMessage());

            }
        }

        //verifica se é pra editar ou cadastrar, como estamos editando o valor é "true"
        ControllerHelper.setEditMode(model, true);


        return "cliente-cadastro";
    }

}
