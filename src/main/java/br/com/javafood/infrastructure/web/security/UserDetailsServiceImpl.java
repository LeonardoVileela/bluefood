package br.com.javafood.infrastructure.web.security;

import br.com.javafood.domain.cliente.ClienteRepository;
import br.com.javafood.domain.restaurante.RestauranteRepository;
import br.com.javafood.domain.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteRepository clienteRespository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario usuario = clienteRespository.findByEmail(username);

        if (usuario == null) {
            usuario = restauranteRepository.findByEmail(username);

            if (usuario == null) {
                throw new UsernameNotFoundException(username);
            }
        }

        return new LoggedUser(usuario);
    }

}
