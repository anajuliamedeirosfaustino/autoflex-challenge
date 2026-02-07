package com.autoflex.inventory.repository;

import com.autoflex.inventory.entity.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {

    void deleteByProductId(Long productId);

    void deleteByRawMaterialId(Long rawMaterialId);

}
