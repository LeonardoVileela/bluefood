package br.com.javafood.infrastructure.web.controller;

import br.com.javafood.application.ItemCardapioService;
import br.com.javafood.application.RestauranteService;
import br.com.javafood.application.ValidationException;
import br.com.javafood.domain.pedido.Pedido;
import br.com.javafood.domain.restaurante.ItemCardapio;
import br.com.javafood.domain.pedido.PedidoItemCardapio;
import br.com.javafood.domain.restaurante.ItemCardapioRepository;
import br.com.javafood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(path = "/restaurante")
public class RestauranteController {

    @Autowired
    private ItemCardapioService itemCardapioService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private ItemCardapioRepository itemCardapioRepository;

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
            return "redirect:/restaurante/itemCadastro" + "?cadastroItem=true";
        }

        return "redirect:/restaurante/itemCadastro" + "?cadastroItem=false";

    }

    @PostMapping(path = "/item/edit")
    public String editItemCardapio(
            @ModelAttribute("itemCardapio") ItemCardapio itemCardapio,
            Errors errors,
            Model model
    ) throws ValidationException {
        ItemCardapio itemCardapioEdit = itemCardapioService.searchById(itemCardapio.getId());
        if(itemCardapio.getLogotipoFile().isEmpty()){
            itemCardapio.setLogotipoFile(itemCardapioEdit.getLogotipoFile());
            itemCardapio.setLogotipo(itemCardapioEdit.getLogotipo());
            itemCardapio.setAtivo(itemCardapioEdit.getAtivo());
            itemCardapio.setRestaurante(SecurityUtils.loggedRestaurante());

            itemCardapioRepository.save(itemCardapio);
        }else{
            itemCardapio.setAtivo(itemCardapioEdit.getAtivo());
            itemCardapio.setRestaurante(SecurityUtils.loggedRestaurante());
            itemCardapioService.save(itemCardapio);
        }




        return "redirect:/restaurante/items/list";

    }



    @GetMapping(path = "/pedido/pendentes")
    public String pedidosPendentes(
            Model model
    ){
      List<Pedido> pedidos = restauranteService.findPedidos(SecurityUtils.loggedRestaurante().getId());
        model.addAttribute("pedidos", pedidos);

      return "restaurante-pedidos-pendentes";
    }

    @GetMapping(path = "/pedido/pendentes/items")
    public String pedidosPendentesItems(
            @RequestParam(required = true) Integer pedidoId,
            Model model
    ){
        List<PedidoItemCardapio> itemsPendentes = restauranteService.findItemsPedidosRestaurante(SecurityUtils.loggedRestaurante().getId(), pedidoId );
        model.addAttribute("items", itemsPendentes);

        model.addAttribute("pedidoId", pedidoId);


        return "restaurante-pedidos-pendentes-items";
    }

    @GetMapping(path = "/pedido/finalizar")
    public String finalizarPedido(
            Model model,
            @RequestParam(required = true) Integer pedidoId
    ){
        restauranteService.finalizarPedido(pedidoId);

        return "redirect:/restaurante/pedido/pendentes";
    }

    @GetMapping(path = "/items/list")
    public String todosItemsCardapio(
            Model model
    ){
        model.addAttribute("itemsCardapio", restauranteService.listItemsRestaurante(SecurityUtils.loggedRestaurante().getId()));

        return "restaurante-items-cardapio";
    }

    @GetMapping(path = "/items/list/desativados")
    public String todosItemsCardapioDesativados(
            Model model
    ){
        model.addAttribute("itemsCardapio", restauranteService.listItemsRestauranteDesativados(SecurityUtils.loggedRestaurante().getId()));

        return "restaurante-items-cardapio-desativados";
    }

    @GetMapping(path = "/pedidos/list")
    public String todosPedidos(
            Model model
    ){

        List<Pedido> nice = restauranteService.listPedidosRestaurante(SecurityUtils.loggedRestaurante().getId());

        model.addAttribute("pedidos", restauranteService.listPedidosRestaurante(SecurityUtils.loggedRestaurante().getId()));

        return "restaurante-pedidos-todos";
    }

    @GetMapping(path = "/itemcardapio/desativar")
    public String desativarItemCardapio(
            @RequestParam(required = true) Integer id
    ){

        restauranteService.desativarItemCardapioService(id);

        return "redirect:/restaurante/items/list";
    }

    @GetMapping(path = "/itemcardapio/ativar")
    public String ativarItemCardapio(
            @RequestParam(required = true) Integer id
    ){

        restauranteService.ativarItemCardapioService(id);

        return "redirect:/restaurante/items/list";
    }

    @GetMapping(path = "/itemcardapio/edit")
    public String editItemCardapio(
            Model model,
            @RequestParam(required = true) Integer id
    ){

        ItemCardapio itemCardapio = itemCardapioService.searchById(id);
        model.addAttribute("itemCardapio", itemCardapio);

        return "item-cardapio-edit";
    }


}
