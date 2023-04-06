package com.example.tuonlineclothingstore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class TuOnlineClothingStore {
    public static void main(String[] args) {
        SpringApplication.run(TuOnlineClothingStore.class, args);
        System.out.println("-----------------------------------------------------------");
        System.out.println("🚀 Server ready at http://localhost:8080");
        System.out.println("🚀 Api doc ready at http://localhost:8080/swagger-ui.html ");
    }

}
