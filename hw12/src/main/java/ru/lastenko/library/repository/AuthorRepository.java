package ru.lastenko.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lastenko.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}