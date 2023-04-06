package com.example.tuonlineclothingstore.repositories;

import com.example.tuonlineclothingstore.entities.Category;
import com.example.tuonlineclothingstore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByProductNameContainingIgnoreCase(String name);
    List<Product> findByProductNameContainingAndCategoryAllIgnoreCase(String productName, Category category);
    List<Product> findTop8ByOrderBySoldDesc();
    List<Product> findTop8ByOrderByCreateAtDesc();

    List<Product> findByCategoryId(long category);
}
