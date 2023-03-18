package ru.lastenko.library.service;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lastenko.library.dao.BookDao;
import ru.lastenko.library.domain.Author;
import ru.lastenko.library.domain.Book;
import ru.lastenko.library.domain.Genre;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с книгами должен")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookInputService bookInputService;
    @Mock
    private BookDao bookDao;
    @Mock
    private IOService ioService;
    @Mock
    private IdentifiableService identifiableService;
    @InjectMocks
    private BookServiceImpl bookService;

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("вернуть все книги")
    void shouldGetAllBooks() {
        var books = easyRandom.objects(Book.class, 3).collect(Collectors.toList());
        when(bookDao.getAll()).thenReturn(books);

        List<Book> actualBooks = bookService.getAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(books);
    }

    @Test
    @DisplayName("получить новую книгу и сохранить ее")
    void shouldGetAndSaveBook() {
        var book = easyRandom.nextObject(Book.class);
        when(bookInputService.getBook()).thenReturn(book);

        bookService.getAndSave();

        verify(bookInputService, times(1)).getBook();
        verify(bookDao, times(1)).insert(book);
    }

    @Test
    @DisplayName("получить книгу по ее id")
    void shouldGetBookById() {
        var book = easyRandom.nextObject(Book.class);
        long id = book.getId();
        when(bookDao.getById(id)).thenReturn(book);

        Book receivedBook = bookService.getBy(id);

        assertThat(receivedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("сообщить о несуществующем id при запросе и вернуть null")
    void shouldOutputIncorrectIdMessage() {
        when(bookDao.getById(anyLong())).thenThrow(IllegalArgumentException.class);

        Book receivedBook = bookService.getBy(12345L);

        verify(ioService, times(1)).outputString("Введен несущестующий id!");
        assertThat(receivedBook).isNull();
    }

    @Test
    @DisplayName("получить книгу с изменениями и обновить ею оригинальную")
    void shouldUpdateBook() {
        var originalBook = easyRandom.nextObject(Book.class);
        var bookWithUpdates = new Book(
                originalBook.getId(),
                "Книга с обновлениями",
                easyRandom.nextObject(Author.class),
                easyRandom.nextObject(Genre.class)
        );
        when(bookInputService.getBook()).thenReturn(bookWithUpdates);

        Book updatedBook = bookService.update(originalBook);

        verify(bookInputService, times(1)).getBook();
        verify(bookDao, times(1)).update(bookWithUpdates);
        assertThat(updatedBook)
                .isNotNull()
                .isEqualTo(bookWithUpdates)
                .isNotEqualTo(originalBook);
    }

    @Test
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        var book = easyRandom.nextObject(Book.class);

        bookService.delete(book);

        verify(bookDao, times(1)).deleteById(book.getId());
    }

    @Test
    @DisplayName("получить все книги и выбрать одну из них")
    void shouldSelectBook() {
        var books = easyRandom.objects(Book.class, 3).collect(Collectors.toList());
        when(bookDao.getAll()).thenReturn(books);
        Book selectedBook = books.get(0);
        when(identifiableService.selectByIdFrom(books)).thenReturn(selectedBook);

        Book receivedBook = bookService.selectBook();

        assertThat(receivedBook).isEqualTo(selectedBook);
    }

}