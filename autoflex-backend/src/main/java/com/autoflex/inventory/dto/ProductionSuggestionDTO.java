package com.autoflex.inventory.dto;

import java.math.BigDecimal;

public class ProductionSuggestionDTO {

    private String productName;
    private Integer quantity;
    private BigDecimal totalValue;

    public ProductionSuggestionDTO(String productName, Integer quantity, BigDecimal totalValue) {
        this.productName = productName;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }

    public String getProductName() {
        return productName;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }
}
