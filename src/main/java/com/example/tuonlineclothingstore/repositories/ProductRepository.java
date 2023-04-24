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
    Page<Product> findByProductNameContainingIgnoreCaseAndIsActive(String productName,Boolean booleen, Pageable pageable);
    Page<Product> findByProductNameContainingAndCategoryAllIgnoreCaseAndIsActive(String productName, Category category,Boolean booleen, Pageable pageable);
    List<Product> findTop8ByOrderBySoldDesc();
    List<Product> findTop8ByOrderByCreateAtDesc();

    List<Product> findByCategoryId(long category);
}
