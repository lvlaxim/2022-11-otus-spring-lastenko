package ru.lastenko.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.lastenko.library.model.Book;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static ru.lastenko.library.repository.RepositoryTestUtils.*;

@DisplayName("Репозиторий для работы с книгами должен")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("получить все книги из БД")
    void shouldGetAllBooks() {
        var allBooks = bookRepositoryJpa.getAll();
        assertThat(allBooks).asList()
                .hasSize(3)
                .containsExactlyInAnyOrder(BOOK_1, BOOK_2, BOOK_3);
    }

    @Test
    @DisplayName("вставить книгу в БД")
    void shouldInsertBook() {
        var name = "Новая книга";
        var newBook = new Book(0, name, AUTHOR_1, GENRE_1);

        var savedBook = bookRepositoryJpa.insert(newBook);

        var foundBook = entityManager.find(Book.class, 100L);
        var expectedBook = new Book(100, name, AUTHOR_1, GENRE_1);
        assertThat(foundBook)
                .isNotNull()
                .isEqualTo(savedBook)
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("получить книгу из БД по id")
    void shouldGetBookById() {
        var id = BOOK_1.getId();

        var receivedBook = bookRepositoryJpa.getBy(id);

        assertThat(receivedBook)
                .isNotNull()
                .isEqualTo(BOOK_1);
    }

    @Test
    @DisplayName("кинуть ошибку при получении книги из БД по некорректому id")
    void throwsExceptionWenGetBookById() {
        assertThatThrownBy(() -> bookRepositoryJpa.getBy(12345))
                .isInstanceOf(IllegalArgumentException.class)
                .message().isEqualTo("Incorrect book id");
    }

    @Test
    @DisplayName("обновить книгу в БД")
    void shouldUpdateBook() {
        var bookId = BOOK_1.getId();
        var bookWithUpdates = new Book(bookId, "Обновленное название", AUTHOR_2, GENRE_2);

        var updatedBook = bookRepositoryJpa.update(bookWithUpdates);

        var foundBook = entityManager.find(Book.class, bookId);
        assertThat(foundBook)
                .isNotNull()
                .isEqualTo(updatedBook)
                .isEqualTo(bookWithUpdates)
                .isNotEqualTo(BOOK_1);
    }

    @Test
    @DisplayName("удалить книгу из БД")
    void shouldDeleteBook() {
        bookRepositoryJpa.delete(BOOK_1);
        var book = entityManager.find(Book.class, BOOK_1.getId());
        assertThat(book).isNull();
    }

}