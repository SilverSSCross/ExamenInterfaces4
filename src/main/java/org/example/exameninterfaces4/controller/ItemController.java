package org.example.exameninterfaces4.controller;

import org.example.exameninterfaces4.model.Item;
import org.example.exameninterfaces4.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    // Lista
    @GetMapping
    public String listado(Model model) {
        model.addAttribute("items", itemService.findAll());
        return "items/listado";
    }

    // Detalles
    @GetMapping("/{id}")
    public String detalle(@PathVariable String id, Model model) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        model.addAttribute("item", item);
        return "items/detalle";
    }

    // Editar
    @GetMapping("/{id}/editar")
    public String formularioEditar(@PathVariable String id, Model model) {
        Item item = itemService.findById(id)
                .orElseThrow(() -> new RuntimeException("Item no encontrado"));
        model.addAttribute("item", item);
        return "items/editar";
    }

    // Mandar la edicion
    @PostMapping("/{id}/editar")
    public String procesarEdicion(@PathVariable String id, @ModelAttribute Item item) {
        itemService.updateItem(id, item);
        return "redirect:/items/" + id;
    }
}
