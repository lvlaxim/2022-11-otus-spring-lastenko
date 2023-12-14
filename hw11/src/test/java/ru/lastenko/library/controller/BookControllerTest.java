package ru.lastenko.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.mapper.BookMapper;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.repository.BookRepository;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@DisplayName("Контроллер для работы с книгами должен обработать")
@WebFluxTest(BookController.class)
@TestPropertySource(properties = "mongock.enabled=false") // https://github.com/mongock/mongock/issues/239
class BookControllerTest {

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private BookMapper bookMapper;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("GET-запрос: вернуть все книги")
    void shouldGetAllBooks() {
        Book book = new Book();
        when(bookRepository.findAll()).thenReturn(Flux.fromIterable(List.of(book)));
        BookDto bookDto = new BookDto();
        when(bookMapper.mapToDto(book)).thenReturn(bookDto);

        webClient.get().uri("/api/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(1)
                .contains(bookDto);
    }

    @Test
    @DisplayName("GET-запрос: вернуть книгу по id")
    void shouldGetBookById() {
        String id = "someId";
        Book book = new Book();
        when(bookRepository.findById(id)).thenReturn(Mono.just(book));
        BookDto bookDto = new BookDto();
        when(bookMapper.mapToDto(book)).thenReturn(bookDto);

        webClient.get().uri("/api/book/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(BookDto.class)
                .isEqualTo(bookDto);
    }

    @Test
    @DisplayName("POST-запрос: сохранить полученную книгу")
    void shouldSaveBook() {
        BookDto bookDto = new BookDto();
        Book book = new Book();
        when(bookMapper.mapFromDto(bookDto)).thenReturn(book);
        Book savedBook = new Book();
        when(bookRepository.save(book)).thenReturn(Mono.just(savedBook));
        BookDto savedBookDto = new BookDto();
        when(bookMapper.mapToDto(book)).thenReturn(savedBookDto);

        webClient.post().uri("/api/book")
                .contentType(APPLICATION_JSON)
                .bodyValue(bookDto)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(BookDto.class)
                .isEqualTo(savedBookDto);
    }

    @Test
    @DisplayName("PUT-запрос: обновить полученную книгу")
    void shouldUpdateBook() {
        BookDto bookDto = new BookDto();
        Book book = new Book();
        when(bookMapper.mapFromDto(bookDto)).thenReturn(book);
        Book updatedBook = new Book();
        when(bookRepository.save(book)).thenReturn(Mono.just(updatedBook));
        BookDto updatedBookDto = new BookDto();
        when(bookMapper.mapToDto(book)).thenReturn(updatedBookDto);

        webClient.put().uri("/api/book")
                .contentType(APPLICATION_JSON)
                .bodyValue(bookDto)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(APPLICATION_JSON)
                .expectBody(BookDto.class)
                .isEqualTo(updatedBookDto);
    }

    @Test
    @DisplayName("DELETE-запрос: удалить книгу по полученному id")
    void shouldDeleteBook() {
        String id = "someId";
        when(bookRepository.deleteById(id)).thenReturn(Mono.empty());

        webClient.delete().uri("/api/book/{id}", id)
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();

        verify(bookRepository, times(1)).deleteById(id);
    }

}