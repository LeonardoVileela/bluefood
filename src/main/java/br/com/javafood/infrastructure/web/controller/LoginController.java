package br.com.javafood.infrastructure.web.controller;

import br.com.javafood.util.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private Object IllegalStateException;

    @Autowired
    private ClienteController clienteController;

    @GetMapping(path = {"/login", "/"})
    public String login() {

        if(SecurityUtils.loggedClienteByHome()){
            return "redirect:/cliente/home"; //como redirecionar pra uma pagina
        }

        if(SecurityUtils.loggedRestauranteByHome()){
            return "redirect:/restaurante/home"; //como redirecionar pra uma pagina
        }

        return "login";
    }

    @GetMapping(path = "/login-error")
    public String loginError(Model model){
        model.addAttribute("msg", "Credenciais inválidas");
        return "login";

    }


    public ModelAndView redirectHome() {
        return new ModelAndView("redirect:" + "/cliente/home");
    }


}
