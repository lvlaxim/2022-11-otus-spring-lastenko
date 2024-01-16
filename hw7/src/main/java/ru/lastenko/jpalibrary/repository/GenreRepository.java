package ru.lastenko.jpalibrary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lastenko.jpalibrary.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}