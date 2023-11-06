package ru.lastenko.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Репозиторий для работы с авторами должен")
@DataMongoTest
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("получить всех авторов из БД")
    void shouldGetAllAuthors() {
        var allAuthors = authorRepository.findAll();
        assertThat(allAuthors).asList()
                .hasSize(3);
    }
}