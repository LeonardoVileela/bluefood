package br.com.javafood.infrastructure.web.controller;

import br.com.javafood.domain.restaurante.CategoriaRestaurante;
import br.com.javafood.domain.restaurante.CategoriaRestauranteRepository;
import org.springframework.data.domain.Sort;
import org.springframework.ui.Model;

import java.util.List;

public class ControllerHelper {
    // pattern pra verificar se é pra editar ou cadastrar
    public static void setEditMode(Model model, boolean isEdit){
        model.addAttribute("editMode", isEdit);
    }

    public static void addCategoriasToRequest(CategoriaRestauranteRepository repository, Model model){
        List<CategoriaRestaurante> categorias = repository.findAll(Sort.by("nome"));
        model.addAttribute("categorias", categorias);
    }
}
