package com.example.tuonlineclothingstore.repositories;

import com.example.tuonlineclothingstore.dtos.User.UserDto;
import com.example.tuonlineclothingstore.entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserNameAndPassword(String userName, String password);
    User findByUserName(String userName);

    Boolean existsByUserName(String userName);

    Boolean existsByEmail(String email);
    Page<User> findByNameContainingAndEmailContainingAllIgnoreCase(String name, String email, Pageable pageable);
}
