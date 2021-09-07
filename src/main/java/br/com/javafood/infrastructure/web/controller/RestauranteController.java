package br.com.javafood.infrastructure.web.controller;

import br.com.javafood.application.ItemCardapioService;
import br.com.javafood.application.RestauranteService;
import br.com.javafood.application.ValidationException;
import br.com.javafood.domain.restaurante.ItemCardapio;
import br.com.javafood.domain.pedido.PedidoItemCardapio;
import br.com.javafood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(path = "/restaurante")
public class RestauranteController {

    @Autowired
    private ItemCardapioService itemCardapioService;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping(path = "/home")
    public String home(Model model) {

        return "restaurante-home";
    }


    @GetMapping(path = "/itemCadastro")
    public String itemCadastro(Model model) {

        ItemCardapio itemCardapio = new ItemCardapio();
        model.addAttribute("itemCardapio", itemCardapio);
        return "item-cadastro";
    }

    @PostMapping(path = "/item/save")
    public String saveItemCardapio(
            @ModelAttribute("itemCardapio")
            @Valid ItemCardapio itemCardapio,
            Errors errors,
            Model model
    ) {
        boolean restauranteCadastro = false;

        itemCardapio.setRestaurante(SecurityUtils.loggedRestaurante());

        //verifica se deu erro
        if (!errors.hasErrors()) {
            try {
                itemCardapioService.save(itemCardapio);
                model.addAttribute("msg", "Restaurante gravado com sucesso");
                restauranteCadastro = true;
            } catch (ValidationException e) {
                //joga a mensagem de erro pro html
                errors.rejectValue("email", null, e.getMessage());

            }
        }

        //TODO mudar redirect para mostrar mensagem de save
        if(restauranteCadastro){
            return "redirect:/restaurante/itemCadastro" + "?cadastroRestaurante=true";
        }

        return "item-cadastro";

    }



    @GetMapping(path = "/pedido/pendentes")
    public String teste(){
      List<PedidoItemCardapio> itemsPendentes = restauranteService.findPedidos(SecurityUtils.loggedRestaurante().getId());

      System.out.println(itemsPendentes);

      //TODO terminar pagina de mostrar pedidos pendentes
      return "redirect:/restaurante/home";
    }

}
