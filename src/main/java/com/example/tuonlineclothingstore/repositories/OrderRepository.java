package com.example.tuonlineclothingstore.repositories;

import com.example.tuonlineclothingstore.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdOrderByCreateAtDesc(Long userId);
}
