package com.autoflex.inventory.controller;

import com.autoflex.inventory.dto.ProductionSuggestionDTO;
import com.autoflex.inventory.service.ProductionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/production")
public class ProductionController {

    private final ProductionService productionService;

    public ProductionController(ProductionService productionService) {
        this.productionService = productionService;
    }

    @GetMapping
    public List<ProductionSuggestionDTO> calculate() {
        return productionService.calculateProduction();
    }
}
