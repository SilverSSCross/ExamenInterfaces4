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
    public String procesarEdicion(
            @PathVariable String id,
            @RequestParam String title,
            @RequestParam Double price,
            @RequestParam String description,
            @RequestParam String category,
            @RequestParam(required = false) String image,
            @RequestParam(required = false) Double rate,
            @RequestParam Integer count,
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String EAN,
            @RequestParam String manufacturer,
            Model model) {

        try {
            // Crear objeto Item con los datos del formulario
            Item itemActualizado = new Item();
            itemActualizado.setTitle(title);
            itemActualizado.setPrice(price);
            itemActualizado.setDescription(description);
            itemActualizado.setCategory(category);
            itemActualizado.setImage(image);
            itemActualizado.setRate(rate);
            itemActualizado.setCount(count);
            itemActualizado.setColor(color);
            itemActualizado.setEAN(EAN);
            itemActualizado.setManufacturer(manufacturer);

            // Actualizar
            itemService.updateItem(id, itemActualizado);
            return "redirect:/items/" + id;

        } catch (Exception e) {
            model.addAttribute("error", "Error al actualizar: " + e.getMessage());
            return "redirect:/items/" + id + "/editar";
        }
    }
}
