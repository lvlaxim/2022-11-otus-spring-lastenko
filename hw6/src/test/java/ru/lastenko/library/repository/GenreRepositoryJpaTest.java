package ru.lastenko.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.lastenko.library.repository.RepositoryTestUtils.GENRE_1;
import static ru.lastenko.library.repository.RepositoryTestUtils.GENRE_2;

@DisplayName("Репозиторий для работы с жанрами должен")
@DataJpaTest
@Import(GenreRepositoryJpa.class)
class GenreRepositoryJpaTest {

    @Autowired
    private GenreRepositoryJpa genreRepositoryJpa;

    @Test
    @DisplayName("получить все жанры из БД")
    void getAll() {
        var allGenres = genreRepositoryJpa.getAll();
        assertThat(allGenres).asList()
                .hasSize(2)
                .containsExactlyInAnyOrder(GENRE_1, GENRE_2);
    }
}