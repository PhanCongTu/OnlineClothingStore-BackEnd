package com.example.tuonlineclothingstore;

import com.example.tuonlineclothingstore.entities.User;
import com.example.tuonlineclothingstore.repositories.UserRepository;
import com.example.tuonlineclothingstore.utils.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.Arrays;
import java.util.Collections;


@SpringBootApplication
@EnableAsync
public class TuOnlineClothingStore implements CommandLineRunner{
    @Autowired
    private UserRepository userRepository;
    public static void main(String[] args) {
        SpringApplication.run(TuOnlineClothingStore.class, args);
        System.out.println("-----------------------------------------------------------");
        System.out.println("🚀 Server ready at http://localhost:8383");
    }
    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User user = new User("User01", "user01", "123", "123123",
                    "user01@gmail.com",
                    Collections.singletonList(EnumRole.ROLE_USER.name()));
            User user1 = new User("Admin01", "admin01", "123", "123123",
                    "admin01@gmail.com",
                    Collections.singletonList(EnumRole.ROLE_ADMIN.name()));
            User user2 = new User("UserAdmin01", "useradmin01", "123", "123123",
                    "useradmin01@gmail.com",
                    Arrays.asList(EnumRole.ROLE_USER.name(), EnumRole.ROLE_ADMIN.name()));
            userRepository.save(user);
            userRepository.save(user1);
            userRepository.save(user2);
        }
    }
}
