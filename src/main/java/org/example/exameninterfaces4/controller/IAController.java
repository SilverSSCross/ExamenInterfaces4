package org.example.exameninterfaces4.controller;


import org.example.exameninterfaces4.service.IAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/ia")
public class IAController {

    @Autowired
    private IAService iaService;

    @GetMapping
    public String mostrarPagina() {
        return "ia/chat";
    }

    @PostMapping("/preguntar")
    public String preguntar(@RequestParam String pregunta, Model model) {
        String respuesta = iaService.preguntar(pregunta);
        model.addAttribute("respuesta", respuesta);
        model.addAttribute("pregunta", pregunta);
        return "ia/chat";
    }
}
