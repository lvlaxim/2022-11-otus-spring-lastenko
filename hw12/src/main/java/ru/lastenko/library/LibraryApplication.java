package ru.lastenko.library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {

    /**
     * http://localhost:8080/book
     *
     * username: user
     * password: password
     */

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

}