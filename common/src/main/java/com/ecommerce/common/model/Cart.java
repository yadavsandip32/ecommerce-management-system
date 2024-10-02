package com.ecommerce.common.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Long customerId;
    private List<Product> products = new ArrayList<>();

    public Cart(Long customerId) {
        this.customerId = customerId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public void removeProduct(Product product) {
        this.products.remove(product);
    }
}
