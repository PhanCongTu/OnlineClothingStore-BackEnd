package com.example.tuonlineclothingstore.repositories;

import com.example.tuonlineclothingstore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
//    User findByUserNameAndPasswordAndRoles(String userName, String password, Collection<Role> roles);
    User findByUserNameAndPassword(String userName, String password);
    User findByUserName(String userName);

    Boolean existsByUserName(String userName);
}
