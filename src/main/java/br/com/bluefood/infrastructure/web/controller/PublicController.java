package br.com.bluefood.infrastructure.web.controller;

import br.com.bluefood.application.ClienteService;
import br.com.bluefood.application.ValidationException;
import br.com.bluefood.domain.cliente.Cliente;
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

    @GetMapping("/cliente/new")
    public String newCliente(Model model) {

        Cliente cliente = new Cliente();
        model.addAttribute("cliente", cliente);
        // model.addAttribute("cliente", new Cliente());

        ControllerHelper.setEditMode(model, false);
        return "cliente-cadastro";
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
            } catch (ValidationException e){
                //joga a mensagem de erro pro html
                errors.rejectValue("email", null, e.getMessage());

            }
        }

        //verifica se é pra editar ou cadastrar, como estamos no cadastro o valor é "false"
        ControllerHelper.setEditMode(model, false);
        return "cliente-cadastro";
    }

}
