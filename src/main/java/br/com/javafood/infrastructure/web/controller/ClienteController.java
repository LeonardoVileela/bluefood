package br.com.javafood.infrastructure.web.controller;

import br.com.javafood.application.ClienteService;
import br.com.javafood.application.RestauranteService;
import br.com.javafood.application.ValidationException;
import br.com.javafood.domain.cliente.Cliente;
import br.com.javafood.domain.cliente.ClienteRepository;
import br.com.javafood.domain.restaurante.CategoriaRestaurante;
import br.com.javafood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.javafood.domain.restaurante.Restaurante;
import br.com.javafood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

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
    private RestauranteService restauranteService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping(path = "/home")
    public String home(Model model) {
        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);

        return "cliente-home";
    }

    @GetMapping("/edit")
    public String edit(Model model) {
        Integer clienteId = SecurityUtils.loggedCliente().getId(); //pega o id do cliente logado para depois buscar o cliente logado no banco
        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(); // busca o cliente logado no banco, se não encontrar lança uma exception
        model.addAttribute("cliente", cliente);//joga o cliente logado pra tela de edição
        ControllerHelper.setEditMode(model, true);//ativa o modo de edição
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

    @GetMapping(path = "/search")
    public String search(@RequestParam String busca, @RequestParam(required = false) String filter, Model model) throws Exception {

        List<Restaurante> restaurantes = restauranteService.search(busca);
        //método filter responsavel pelos filtros de pesquisa
        restaurantes = restauranteService.filter(filter, restaurantes);

        model.addAttribute("restaurantes", restaurantes);
        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
        model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());

        return "cliente-busca";
    }

    @GetMapping(path = "/searchCategory")
    public String searchCategory(@RequestParam Integer categoria, @RequestParam(required = false) String filter, Model model) throws Exception {

        List<Restaurante> restaurantes = restauranteService.searchCategory(categoria);
        restaurantes = restauranteService.filter(filter, restaurantes);

        model.addAttribute("restaurantes", restaurantes);
        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
        model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());

        return "cliente-busca";
    }

    @GetMapping(path = "/restaurante")
    public String viewRestaurante(
            @RequestParam Integer restautenteId,
            Model model
    ){

        Restaurante restaurante = restauranteService.searchRestauranteById(restautenteId);
        model.addAttribute("itemCardapios", restaurante.getItemCardapios());
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());//pega o cep do cliente logado
        return "cliente-restaurante";
    }


}
