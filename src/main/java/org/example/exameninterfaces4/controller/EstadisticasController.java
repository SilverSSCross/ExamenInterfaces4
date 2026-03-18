package org.example.exameninterfaces4.controller;


import org.example.exameninterfaces4.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/estadisticas")
public class EstadisticasController {

    @Autowired
    private ItemService itemService;

    @GetMapping
    public String dashboard(Model model) {
        // Número total de items
        long totalItems = itemService.countTotalItems();
        model.addAttribute("totalItems", totalItems);

        // Items con stock bajo (menos de 100 unidades)
        model.addAttribute("stockBajo", itemService.findByStockBajo(100));

        // Listado de fabricantes (solo nombres)
        model.addAttribute("fabricantes", itemService.getAllManufacturers());

        return "estadisticas/dashboard";
    }
}


//Api key=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