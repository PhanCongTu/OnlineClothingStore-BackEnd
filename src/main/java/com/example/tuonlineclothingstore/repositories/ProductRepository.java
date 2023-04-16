package com.example.tuonlineclothingstore.repositories;

import com.example.tuonlineclothingstore.entities.Category;
import com.example.tuonlineclothingstore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByProductNameContainingIgnoreCase(String productName, Pageable pageable);
    Page<Product> findByProductNameContainingAndCategoryAllIgnoreCase(String productName, Category category, Pageable pageable);
    List<Product> findTop8ByOrderBySoldDesc();
    List<Product> findTop8ByOrderByCreateAtDesc();

    List<Product> findByCategoryId(long category);
}
