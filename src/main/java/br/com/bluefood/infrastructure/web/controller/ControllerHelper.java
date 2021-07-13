package br.com.bluefood.infrastructure.web.controller;

import org.springframework.ui.Model;

public class ControllerHelper {
    // pattern pra verificar se é pra editar ou cadastrar
    public static void setEditMode(Model model, boolean isEdit){
        model.addAttribute("editMode", isEdit);
    }

}
