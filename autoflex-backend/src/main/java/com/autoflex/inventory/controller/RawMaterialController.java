package com.autoflex.inventory.controller;

import com.autoflex.inventory.entity.RawMaterial;
import com.autoflex.inventory.repository.ProductMaterialRepository;
import com.autoflex.inventory.repository.RawMaterialRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/raw-materials")
public class RawMaterialController {

    private final RawMaterialRepository repository;
    private final ProductMaterialRepository productMaterialRepository;

    public RawMaterialController(
            RawMaterialRepository repository,
            ProductMaterialRepository productMaterialRepository
    ) {
        this.repository = repository;
        this.productMaterialRepository = productMaterialRepository;
    }

    @GetMapping
    public List<RawMaterial> findAll() {
        return repository.findAll();
    }

    @PostMapping
    public RawMaterial create(@RequestBody RawMaterial rawMaterial) {
        return repository.save(rawMaterial);
    }

    @PutMapping("/{id}")
    public RawMaterial update(@PathVariable Long id, @RequestBody RawMaterial rawMaterialDetails) {
        RawMaterial rawMaterial = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw Material not found"));

        rawMaterial.setCode(rawMaterialDetails.getCode());
        rawMaterial.setName(rawMaterialDetails.getName());
        rawMaterial.setStockQuantity(rawMaterialDetails.getStockQuantity());

        return repository.save(rawMaterial);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {

        RawMaterial rawMaterial = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Raw Material not found"));

        productMaterialRepository.deleteByRawMaterialId(id);

        repository.delete(rawMaterial);
    }
}
