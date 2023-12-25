package ru.lastenko.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.lastenko.library.model.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}