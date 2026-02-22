package com.example.spring_mvc_lab.controller;

import com.example.spring_mvc_lab.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticsController {

    private final ProductService productService;

    public StatisticsController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/statistics")
    public String statistics(Model model) {

        model.addAttribute("totalProducts", productService.findAll().size());
        model.addAttribute("totalPerCategory", productService.countByCategory());
        model.addAttribute("mostExpensive", productService.getMostExpensive());
        model.addAttribute("cheapest", productService.getCheapest());
        model.addAttribute("averagePrice", productService.getAveragePrice());
        model.addAttribute("lowStockCount", productService.countLowStock());
        model.addAttribute("title", "Statistik Produk");

        return "statistics";
    }
}