package ru.lastenko.jpalibrary.service;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lastenko.jpalibrary.repository.BookRepository;
import ru.lastenko.jpalibrary.model.Book;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с книгами должен")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private IOService ioService;
    @InjectMocks
    private BookServiceImpl bookService;

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("вернуть все книги")
    void shouldGetAllBooks() {
        var books = easyRandom.objects(Book.class, 3).collect(Collectors.toList());
        when(bookRepository.findAll()).thenReturn(books);

        List<Book> actualBooks = bookService.getAll();

        assertThat(actualBooks).containsExactlyInAnyOrderElementsOf(books);
    }

    @Test
    @DisplayName("получить новую книгу и сохранить ее")
    void shouldGetAndSaveBook() {
        var book = easyRandom.nextObject(Book.class);

        bookService.save(book);

        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("получить книгу по ее id")
    void shouldGetBookById() {
        var book = easyRandom.nextObject(Book.class);
        long id = book.getId();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));

        Book receivedBook = bookService.getBy(id);

        verify(bookRepository, times(1)).findById(id);
        assertThat(receivedBook).isEqualTo(book);
    }

    @Test
    @DisplayName("выкину NoSuchElementException")
    void shouldThrowNoSuchElementException() {
        long id = 12345L;
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.getBy(id)).isExactlyInstanceOf(NoSuchElementException.class);

        verify(bookRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("получить книгу с изменениями и обновить ею оригинальную")
    void shouldUpdateBook() {
        var originalBook = easyRandom.nextObject(Book.class);
        var bookWithUpdates = easyRandom.nextObject(Book.class);
        when(bookRepository.save(bookWithUpdates)).thenReturn(bookWithUpdates);

        Book updatedBook = bookService.save(bookWithUpdates);

        verify(bookRepository, times(1)).save(bookWithUpdates);
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

        verify(bookRepository, times(1)).delete(book);
    }
}