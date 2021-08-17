package br.com.javafood.infrastructure.web.security;

import br.com.javafood.util.SecurityUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//classe usada quando o login funciona
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        Role role = SecurityUtils.loggedUser().getRole();

        if(role == Role.CLIENTE){
            response.sendRedirect("cliente/home"); //redireciona caso o usuario seja um cliente
        } else if(role == Role.RESTAURANTE){
            response.sendRedirect("restaurante/home");//redireciona caso o usuario seja um restaurante
        } else{
            throw new IllegalStateException("Erro na autenticação");
        }


    }
}
