package com.ecommerce.common.model;

import java.util.List;

public class OrderRequest {
    private Long customerId;
    private List<Product> products;

    public OrderRequest(Long customerId, List<Product> products) {
        this.customerId = customerId;
        this.products = products;
    }

    // Getters and setters

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    // Methods to get details of the first product in the list (assuming single product orders)

    public Long getProductId() {
        // Returns the product ID of the first product in the list
        if (products != null && !products.isEmpty()) {
            return products.get(0).getId(); // Assuming `Product` class has a method `getId()`
        }
        return null;
    }

    public Double getTotalPrice() {
        // Calculate the total price of the products in the request
        double total = 0;
        if (products != null && !products.isEmpty()) {
            for (Product product : products) {
                total += product.getPrice();
            }
        }
        return total;
    }
}
