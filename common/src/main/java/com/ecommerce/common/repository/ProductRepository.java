package com.ecommerce.common.repository;

import com.ecommerce.common.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category, Pageable pageable);
    List<Product> findAll(String category);
}
