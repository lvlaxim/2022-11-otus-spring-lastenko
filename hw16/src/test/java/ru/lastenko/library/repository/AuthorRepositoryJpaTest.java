package ru.lastenko.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static ru.lastenko.library.repository.RepositoryTestUtils.AUTHOR_1;
import static ru.lastenko.library.repository.RepositoryTestUtils.AUTHOR_2;

@DisplayName("Репозиторий для работы с авторами должен")
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AuthorRepositoryJpaTest extends DbContainerTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("получить всех авторов из БД")
    void shouldGetAllAuthors() {
        var allAuthors = authorRepository.findAll();
        assertThat(allAuthors).asList()
                .hasSize(2)
                .containsExactlyInAnyOrder(AUTHOR_1, AUTHOR_2);
    }
}
