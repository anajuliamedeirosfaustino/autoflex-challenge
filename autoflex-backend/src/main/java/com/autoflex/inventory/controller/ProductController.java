package com.autoflex.inventory.controller;

import com.autoflex.inventory.entity.Product;
import com.autoflex.inventory.repository.ProductRepository;
import com.autoflex.inventory.repository.ProductMaterialRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository repository;
    private final ProductMaterialRepository productMaterialRepository;

    public ProductController(
            ProductRepository repository,
            ProductMaterialRepository productMaterialRepository
    ) {
        this.repository = repository;
        this.productMaterialRepository = productMaterialRepository;
    }

    @GetMapping
    public List<Product> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        return repository.save(product);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody Product productDetails) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        product.setCode(productDetails.getCode());
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        return repository.save(product);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        productMaterialRepository.deleteByProductId(id);

        repository.delete(product);
    }
}
