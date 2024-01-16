package ru.lastenko.jpalibrary.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Репозиторий для работы с авторами должен")
@DataJpaTest
//@Import(AuthorRepositoryJpa.class)
class AuthorRepositoryJpaTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("получить всех авторов из БД")
    void shouldGetAllAuthors() {
        var allAuthors = authorRepository.findAll();
        assertThat(allAuthors).asList()
                .hasSize(2)
                .containsExactlyInAnyOrder(RepositoryTestUtils.AUTHOR_1, RepositoryTestUtils.AUTHOR_2);
    }
}