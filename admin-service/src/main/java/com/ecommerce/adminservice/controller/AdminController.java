package com.ecommerce.adminservice.controller;
//import com.ecommerce.adminservice.model.Product;
import com.ecommerce.common.model.*;
import com.ecommerce.adminservice.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/inventory")
    public List<Product> viewInventory(@RequestParam(required = false) String category, Pageable pageable) {
        return adminService.getInventory(category, pageable);
    }

    @PostMapping("/inventory/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product) {
        return adminService.addProduct(product);
    }

    @PutMapping("/inventory/update/{productId}")
    public ResponseEntity<String> updateProduct(@PathVariable Long productId, @RequestBody Product product) {
        return adminService.updateProduct(productId, product);
    }

    @DeleteMapping("/inventory/outofstock/{productId}")
    public ResponseEntity<String> markProductOutOfStock(@PathVariable Long productId) {
        return adminService.markOutOfStock(productId);
    }
}
