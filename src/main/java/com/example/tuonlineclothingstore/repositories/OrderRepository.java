package com.example.tuonlineclothingstore.repositories;

import com.example.tuonlineclothingstore.entities.Order;
import com.example.tuonlineclothingstore.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserIdOrderByCreateAtDesc(Long userId);
    Page<Order> findAllByPhoneNumberContainingAndStatusContainingAllIgnoreCase(String phoneNumber, String status, Pageable pageable);
    Page<Order> findAllByAddressContainingAndStatusContainingOrPhoneNumberContainingAndStatusContainingAllIgnoreCase(String address, String status1,String phoneNumber, String status2, Pageable pageable );
}
