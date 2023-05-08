package ru.lastenko.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Genre;

import javax.persistence.EntityManager;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Репозиторий для работы с книгами должен")
@DataJpaTest
@Import(BookRepositoryJpa.class)
class BookRepositoryJpaTest {

    public static final int EXPECTED_BOOKS_COUNT = 3;
    public static final Author EXISTED_AUTHOR = new Author(1, "Автор1");
    public static final Genre EXISTED_GENRE = new Genre(1, "Жанр1");
    private static final Book EXISTED_BOOK = new Book(1, "Книга1", EXISTED_AUTHOR, EXISTED_GENRE, emptyList());

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName("получить все книги из БД")
    void shouldGetAllBooks() {
        List<Book> books = bookRepositoryJpa.getAll();
        assertThat(books).asList()
                .hasSize(EXPECTED_BOOKS_COUNT)
                .contains(EXISTED_BOOK);
    }

    @Test
    @DisplayName("вставить книгу в БД")
    void shouldInsertBook() {
        var name = "Новая книга";
        var newBook = new Book(0, name, EXISTED_AUTHOR, EXISTED_GENRE, emptyList());

        Book savedBook = bookRepositoryJpa.insert(newBook);

        Book foundBook = entityManager.find(Book.class, 100L);
        var expectedBook = new Book(100, name, EXISTED_AUTHOR, EXISTED_GENRE, emptyList());
        assertThat(foundBook)
                .isNotNull()
                .isEqualTo(savedBook)
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("получить книгу из БД по id")
    void shouldGetBookById() {
        long id = EXISTED_BOOK.getId();

        Book receivedBook = bookRepositoryJpa.getBy(id);

        assertThat(receivedBook)
                .isNotNull()
                .isEqualTo(EXISTED_BOOK);
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
        var bookWithUpdates = new Book(EXISTED_BOOK.getId(),
                "Обновленное название",
                EXISTED_BOOK.getAuthor(),
                EXISTED_BOOK.getGenre(),
                emptyList());

        Book updatedBook = bookRepositoryJpa.update(bookWithUpdates);

        long bookId = bookWithUpdates.getId();
        var foundBook = entityManager.find(Book.class, bookId);
        assertThat(foundBook)
                .isNotNull()
                .isEqualTo(updatedBook)
                .isEqualTo(bookWithUpdates)
                .isNotEqualTo(EXISTED_BOOK);
    }

    @Test
    @DisplayName("удалить книгу из БД")
    void shouldDeleteBookById() {
        bookRepositoryJpa.delete(EXISTED_BOOK);
        var book = entityManager.find(Book.class, EXISTED_BOOK.getId());
        assertThat(book).isNull();
    }

}