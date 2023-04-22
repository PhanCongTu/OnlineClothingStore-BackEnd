package com.example.tuonlineclothingstore.repositories;

import com.example.tuonlineclothingstore.entities.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
//    Optional<ProductSize> findById(Long productImageId);
    List<ProductSize> findAllByProductId(Long productId);
}
