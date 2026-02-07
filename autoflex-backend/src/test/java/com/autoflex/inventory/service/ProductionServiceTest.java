package com.autoflex.inventory.service;

import com.autoflex.inventory.dto.ProductionSuggestionDTO;
import com.autoflex.inventory.entity.Product;
import com.autoflex.inventory.entity.ProductMaterial;
import com.autoflex.inventory.entity.RawMaterial;
import com.autoflex.inventory.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ProductionServiceTest {

    @Test
    void shouldCalculateProductionPrioritizingHigherPriceProduct() {

        RawMaterial iron = new RawMaterial();
        iron.setStockQuantity(100);

        ProductMaterial pmA = new ProductMaterial();
        pmA.setRawMaterial(iron);
        pmA.setQuantityRequired(10);

        Product productA = new Product();
        productA.setName("Produto A");
        productA.setPrice(new BigDecimal("100"));
        productA.setMaterials(List.of(pmA));

        ProductMaterial pmB = new ProductMaterial();
        pmB.setRawMaterial(iron);
        pmB.setQuantityRequired(20);

        Product productB = new Product();
        productB.setName("Produto B");
        productB.setPrice(new BigDecimal("200"));
        productB.setMaterials(List.of(pmB));

        ProductRepository repository = Mockito.mock(ProductRepository.class);
        Mockito.when(repository.findAll())
                .thenReturn(List.of(productA, productB));

        ProductionService service = new ProductionService(repository);

        List<ProductionSuggestionDTO> result =
                service.calculateProduction();

        assertEquals(1, result.size());
        assertEquals("Produto B", result.get(0).getProductName());
        assertEquals(5, result.get(0).getQuantity());
    }

    @Test
    void shouldReturnEmptyWhenStockIsInsufficient() {

        RawMaterial iron = new RawMaterial();
        iron.setStockQuantity(5);

        ProductMaterial pm = new ProductMaterial();
        pm.setRawMaterial(iron);
        pm.setQuantityRequired(10);

        Product product = new Product();
        product.setName("Produto A");
        product.setPrice(new BigDecimal("100"));
        product.setMaterials(List.of(pm));

        ProductRepository repository = Mockito.mock(ProductRepository.class);
        Mockito.when(repository.findAll())
                .thenReturn(List.of(product));

        ProductionService service = new ProductionService(repository);

        List<ProductionSuggestionDTO> result =
                service.calculateProduction();

        assertTrue(result.isEmpty());
    }
}
