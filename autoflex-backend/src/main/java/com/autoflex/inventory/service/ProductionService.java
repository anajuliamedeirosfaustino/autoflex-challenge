package com.autoflex.inventory.service;

import com.autoflex.inventory.dto.ProductionSuggestionDTO;
import com.autoflex.inventory.entity.Product;
import com.autoflex.inventory.entity.ProductMaterial;
import com.autoflex.inventory.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ProductionService {

    private final ProductRepository productRepository;

    public ProductionService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

   public List<ProductionSuggestionDTO> calculateProduction() {

   List<Product> products =
        new ArrayList<>(productRepository.findAll());

products.sort((a, b) -> b.getPrice().compareTo(a.getPrice()));


    // copia estoque para memória
    Map<Long, Integer> stockMap = new HashMap<>();

    for (Product product : products) {

    if (product.getMaterials() == null) {
        continue;
    }
        for (ProductMaterial pm : product.getMaterials()) {
            Long id = pm.getRawMaterial().getId();
            stockMap.putIfAbsent(id, pm.getRawMaterial().getStockQuantity());
        }
    }

    List<ProductionSuggestionDTO> result = new ArrayList<>();

    for (Product product : products) {

        if (product.getMaterials() == null || product.getMaterials().isEmpty()) {
        continue;
    }

        int maxQuantity = Integer.MAX_VALUE;

        for (ProductMaterial pm : product.getMaterials()) {

            Long materialId = pm.getRawMaterial().getId();
            int stock = stockMap.get(materialId);
            int required = pm.getQuantityRequired();

             if (required <= 0) {
        continue;
    }

            int possible = stock / required;
            maxQuantity = Math.min(maxQuantity, possible);
        }

        if (maxQuantity > 0 && maxQuantity != Integer.MAX_VALUE) {

            // consome estoque da memória
            for (ProductMaterial pm : product.getMaterials()) {
                Long materialId = pm.getRawMaterial().getId();
                int used = pm.getQuantityRequired() * maxQuantity;

                stockMap.put(materialId, stockMap.get(materialId) - used);
            }

            BigDecimal totalValue = product.getPrice()
                    .multiply(BigDecimal.valueOf(maxQuantity));

            result.add(new ProductionSuggestionDTO(
                    product.getName(),
                    maxQuantity,
                    totalValue
            ));
        }
    }

    return result;
}

}
