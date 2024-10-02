package com.ecommerce.adminservice.service;

//import com.ecommerce.adminservice.model.Product;
import com.ecommerce.common.exception.ProductNotFoundException;
import com.ecommerce.common.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import com.ecommerce.common.model.*;
@Service
public class AdminService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> getInventory(String category, Pageable pageable) {
        if (category == null) {
            return productRepository.findAll(String.valueOf(pageable));
        } else {
            return productRepository.findByCategory(category, pageable);
        }
    }

    public ResponseEntity<String> addProduct(Product product) {
        productRepository.save(product);
        return ResponseEntity.ok("Product added successfully");
    }

    public ResponseEntity<String> updateProduct(Long productId, Product product) {
        Product existingProduct = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        existingProduct.setName(product.getName());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setCategory(product.getCategory());
        productRepository.save(existingProduct);
        return ResponseEntity.ok("Product updated successfully");
    }

    public ResponseEntity<String> markOutOfStock(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setOutOfStock(true);
        productRepository.save(product);
        return ResponseEntity.ok("Product marked out of stock");
    }
}
