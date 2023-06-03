package com.example.tuonlineclothingstore.repositories;

import com.example.tuonlineclothingstore.entities.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByUserId(Long UserId);
    Page<Cart> findAllByUserId(Long UserId, Pageable pageable);
    int countByUserId(Long UserId);

    void deleteById(Long cartId);
}
