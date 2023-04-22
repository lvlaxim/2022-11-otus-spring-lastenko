package ru.lastenko.library.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.dao.EmptyResultDataAccessException;
import ru.lastenko.library.domain.Author;
import ru.lastenko.library.domain.Book;
import ru.lastenko.library.domain.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Dao для работы с книгами должен")
@JdbcTest
@Import(BookDaoJdbc.class)
class BookDaoJdbcTest {

    public static final int EXPECTED_BOOKS_COUNT = 3;
    public static final Author EXISTED_AUTHOR = new Author(1, "Автор1");
    public static final Genre EXISTED_GENRE = new Genre(1, "Жанр1");
    private static final Book EXISTED_BOOK = new Book(1, "Книга1", EXISTED_AUTHOR, EXISTED_GENRE);

    @Autowired
    private BookDaoJdbc bookDaoJdbc;

    @Test
    @DisplayName("получить все книги из БД")
    void shouldGetAllBooks() {
        List<Book> books = bookDaoJdbc.getAll();
        assertThat(books).asList()
                .hasSize(EXPECTED_BOOKS_COUNT)
                .contains(EXISTED_BOOK);
    }

    @Test
    @DisplayName("вставить книгу в БД")
    void shouldInsertBook() {
        var name = "Новая книга";
        var newBook = new Book(0, name, EXISTED_AUTHOR, EXISTED_GENRE);

        bookDaoJdbc.insert(newBook);

        Book receivedBook = bookDaoJdbc.getById(100);
        var expectedBook = new Book(100, name, EXISTED_AUTHOR, EXISTED_GENRE);
        assertThat(receivedBook).isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("получить книгу из БД по id")
    void shouldGetBookById() {
        long id = EXISTED_BOOK.getId();

        Book receivedBook = bookDaoJdbc.getById(id);

        assertThat(receivedBook)
                .isNotNull()
                .isEqualTo(EXISTED_BOOK);
    }

    @Test
    @DisplayName("кинуть ошибку при получении книги из БД по некорректому id")
    void throwsExceptionWenGetBookById() {
        assertThatThrownBy(() -> bookDaoJdbc.getById(12345))
                .isInstanceOf(IllegalArgumentException.class)
                .message().isEqualTo("Incorrect book id");
    }

    @Test
    @DisplayName("обновить книгу в БД")
    void shouldUpdateBook() {
        Book bookWithUpdates = new Book(EXISTED_BOOK.getId(), "Обновленное название", EXISTED_BOOK.getAuthor(), EXISTED_BOOK.getGenre());

        bookDaoJdbc.update(bookWithUpdates);

        Book updatedBook = bookDaoJdbc.getById(bookWithUpdates.getId());
        assertThat(updatedBook)
                .isNotNull()
                .isEqualTo(bookWithUpdates)
                .isNotEqualTo(EXISTED_BOOK);
    }

    @Test
    @DisplayName("удалить книгу из БД по id")
    void shouldDeleteBookById() {
        long id = EXISTED_BOOK.getId();

        bookDaoJdbc.deleteById(id);

        assertThatThrownBy(() -> bookDaoJdbc.getById(id))
                .isInstanceOf(IllegalArgumentException.class)
                .getCause().isInstanceOf(EmptyResultDataAccessException.class);
    }

}