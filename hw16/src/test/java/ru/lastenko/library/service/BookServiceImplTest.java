package ru.lastenko.library.service;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.mapper.BookMapper;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.repository.BookRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@DisplayName("Сервис для работы с книгами должен")
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("вернуть все книги")
    void shouldGetAllBooks() {
        int booksCount = 3;
        var books = easyRandom.objects(Book.class, booksCount).toList();
        when(bookRepository.findAll()).thenReturn(books);

        var actualBooks = bookService.getAll();

        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(booksCount)).mapToDto(any(Book.class));
    }

    @Test
    @DisplayName("сохранить книгу")
    void shouldSaveBook() {
        var bookDto = new BookDto();
        var book = new Book();
        when(bookMapper.mapFromDto(bookDto)).thenReturn(book);
        var savedBook = new Book();
        when(bookRepository.save(book)).thenReturn(savedBook);
        var savedBookDto = new BookDto();
        when(bookMapper.mapToDto(savedBook)).thenReturn(savedBookDto);

        var resultBook = bookService.save(bookDto);

        verify(bookMapper, times(1)).mapFromDto(bookDto);
        verify(bookRepository, times(1)).save(book);
        verify(bookMapper, times(1)).mapToDto(savedBook);
        assertEquals(savedBookDto, resultBook);
    }

    @Test
    @DisplayName("получить книгу по ее id")
    void shouldGetBookById() {
        var book = easyRandom.nextObject(Book.class);
        long id = book.getId();
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        var bookDto = new BookDto();
        when(bookMapper.mapToDto(book)).thenReturn(bookDto);

        var resultBookDto = bookService.getBy(id);

        verify(bookRepository, times(1)).findById(id);
        verify(bookMapper, times(1)).mapToDto(book);
        assertEquals(bookDto, resultBookDto);
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
    @DisplayName("удалить книгу")
    void shouldDeleteBook() {
        var book = easyRandom.nextObject(Book.class);
        long id = book.getId();

        bookService.deleteBy(id);

        verify(bookRepository, times(1)).deleteById(id);
    }
}
