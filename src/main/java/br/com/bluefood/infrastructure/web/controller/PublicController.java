package br.com.bluefood.infrastructure.web.controller;

import br.com.bluefood.application.ClienteService;
import br.com.bluefood.application.RestauranteService;
import br.com.bluefood.application.ValidationException;
import br.com.bluefood.domain.cliente.Cliente;
import br.com.bluefood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.bluefood.domain.restaurante.Restaurante;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "/public")
public class PublicController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private CategoriaRestauranteRepository categoriaRestauranteRepository;

    @GetMapping("/cliente/new")
    public String newCliente(Model model) {

        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        // model.addAttribute("cliente", new Cliente());


        ControllerHelper.setEditMode(model, false);
        return "cliente-cadastro";
    }

    @GetMapping("/restaurante/new")
    public String newRestaurante(Model model) {

        Restaurante restaurante = new Restaurante();
        model.addAttribute("restaurante", restaurante);
        // model.addAttribute("cliente", new Cliente());

        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);

        ControllerHelper.setEditMode(model, false);
        return "restaurante-cadastro";
    }

    @PostMapping(path = "/restaurante/save")
    public String saveRestaurante(
            @ModelAttribute("restaurante")
            @Valid Restaurante restaurante,
            Errors errors,
            Model model
    ) {
        System.out.println(restaurante);
        //verifica se deu erro
        if (!errors.hasErrors()) {
            try {
                restauranteService.save(restaurante);
                model.addAttribute("msg", "Restaurante gravado com sucesso");
            } catch (ValidationException e) {
                //joga a mensagem de erro pro html
                errors.rejectValue("email", null, e.getMessage());

            }
        }

        ControllerHelper.addCategoriasToRequest(categoriaRestauranteRepository, model);
        //verifica se é pra editar ou cadastrar, como estamos no cadastro o valor é "false"
        ControllerHelper.setEditMode(model, false);
        return "restaurante-cadastro";
    }


    @PostMapping(path = "/cliente/save")
    public String saveClient(
            @ModelAttribute("cliente")
            @Valid Cliente cliente,
            Errors errors,
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

        //verifica se é pra editar ou cadastrar, como estamos no cadastro o valor é "false"
        ControllerHelper.setEditMode(model, false);
        return "cliente-cadastro";
    }

}
