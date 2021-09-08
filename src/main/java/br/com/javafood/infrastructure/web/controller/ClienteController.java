package br.com.javafood.infrastructure.web.controller;

import br.com.javafood.application.*;
import br.com.javafood.domain.cliente.Cliente;
import br.com.javafood.domain.cliente.ClienteRepository;
import br.com.javafood.domain.pedido.Pedido;
import br.com.javafood.domain.pedido.PedidoItemCardapio;
import br.com.javafood.domain.restaurante.CategoriaRestaurante;
import br.com.javafood.domain.restaurante.CategoriaRestauranteRepository;
import br.com.javafood.domain.restaurante.ItemCardapio;
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
import java.util.Map;

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
    private ItemCardapioService itemCardapioService;

    @Autowired
    private PedidoService pedidoService;

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
    ) {


        Restaurante restaurante = restauranteService.searchRestauranteById(restautenteId);
        List<ItemCardapio> itemCardapios = itemCardapioService.searchByRestauranteid(restautenteId);
        model.addAttribute("itemCardapios", itemCardapios);
        model.addAttribute("restaurante", restaurante);
        model.addAttribute("cep", SecurityUtils.loggedCliente().getCep());//pega o cep do cliente logado
        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
        return "cliente-restaurante";
    }

    @GetMapping(path = "/carrinho")
    public String carrinho(
            @RequestParam(required = false) Integer itemCarrinhoId,
            @RequestParam(required = false) String quant,
            Model model
    ) {

        if(itemCarrinhoId != null){
            if(quant != null){
                if(quant.equals("plus")){
                    SecurityUtils.loggedCliente().addExisting(itemCarrinhoId);
                }
                if(quant.equals("minus")){
                    SecurityUtils.loggedCliente().quitExisting(itemCarrinhoId);
                }
            }else{
                ItemCardapio itemCardapio = itemCardapioService.searchById(itemCarrinhoId);
                if(SecurityUtils.loggedCliente().contain(itemCardapio)){
                    SecurityUtils.loggedCliente().addExisting(itemCardapio.getId());
                }else{
                    System.out.println("not contain");
                    SecurityUtils.loggedCliente().addItemCarrinho(itemCardapio);
                }
            }
        }
        Map<ItemCardapio, Integer> itemsCarrinho = SecurityUtils.loggedCliente().getItemsCarrinho();
        model.addAttribute("itemsCarrinho", itemsCarrinho);
        model.addAttribute("totalCarrinho", SecurityUtils.loggedCliente().totalCarrinho());

        return "carrinho";
    }



    @GetMapping(path = "/carrinho/remove")
    public String removeCarrinho(
            @RequestParam(required = false) Integer itemCarrinhoId
            ){
        ItemCardapio itemCardapio = itemCardapioService.searchById(itemCarrinhoId);
        SecurityUtils.loggedCliente().removeItemCarrinho(itemCardapio);

        return "redirect:/cliente/carrinho";
    }


    @PostMapping(path = "/pagamento/save")
    public String saveCarrinho(
    ){

        Map<ItemCardapio, Integer> itemsCarrinho = SecurityUtils.loggedCliente().getItemsCarrinho();
        Integer id = pedidoService.save(itemsCarrinho);
        return "redirect:/cliente/pedido?id=" + id;
    }


    @GetMapping(path = "/pagamento")
    public String pagamento(
    ){
        return "pagamento";
    }

    @GetMapping(path = "/pedidos")
    public String pedidos(
            Model model,
            @RequestParam(required = true) Boolean pendente
    ){
        List<Pedido> pedidos = pedidoService.pedidosCliente(SecurityUtils.loggedCliente().getId(), pendente);
        model.addAttribute("pedidos", pedidos);

        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
        return "cliente-pedidos";
    }


    @GetMapping(path = "/pedido")
    public String pedido(
            Model model,
            @RequestParam(required = true) Integer id
    ){
        List<PedidoItemCardapio> items = pedidoService.pedidoCliente(id);
        model.addAttribute("items", items);

        List<CategoriaRestaurante> categorias = categoriaRestauranteRepository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
        return "cliente-pedido";
    }
}
