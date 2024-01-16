package ru.lastenko.jpalibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lastenko.jpalibrary.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}