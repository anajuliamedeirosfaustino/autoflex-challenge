package com.autoflex.inventory.controller;

import com.autoflex.inventory.entity.Product;
import com.autoflex.inventory.entity.RawMaterial;
import com.autoflex.inventory.entity.ProductMaterial;
import com.autoflex.inventory.repository.ProductRepository;
import com.autoflex.inventory.repository.RawMaterialRepository;
import com.autoflex.inventory.repository.ProductMaterialRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product-materials")
public class ProductMaterialController {

    private final ProductMaterialRepository productMaterialRepository;
    private final ProductRepository productRepository;
    private final RawMaterialRepository rawMaterialRepository;

    public ProductMaterialController(
            ProductMaterialRepository productMaterialRepository,
            ProductRepository productRepository,
            RawMaterialRepository rawMaterialRepository
    ) {
        this.productMaterialRepository = productMaterialRepository;
        this.productRepository = productRepository;
        this.rawMaterialRepository = rawMaterialRepository;
    }

    @PostMapping
    public ProductMaterial create(
            @RequestParam Long productId,
            @RequestParam Long rawMaterialId,
            @RequestParam Integer quantityRequired
    ) {
        Product product = productRepository.findById(productId).orElseThrow();
        RawMaterial rawMaterial = rawMaterialRepository.findById(rawMaterialId).orElseThrow();

        ProductMaterial pm = new ProductMaterial();
        pm.setProduct(product);
        pm.setRawMaterial(rawMaterial);
        pm.setQuantityRequired(quantityRequired);

        return productMaterialRepository.save(pm);
    }
}
