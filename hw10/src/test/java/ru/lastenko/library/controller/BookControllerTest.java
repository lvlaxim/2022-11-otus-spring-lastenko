package ru.lastenko.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.service.BookService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с книгами должен обработать")
@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("GET-запрос: вернуть все книги")
    void shouldGetAllBooks() throws Exception {
        var books = easyRandom.objects(BookDto.class, 3).toList();
        when(bookService.getAll()).thenReturn(books);

        mvc.perform(get("/api/book"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(books)));

        verify(bookService, times(1)).getAll();
    }

    @Test
    @DisplayName("GET-запрос: вернуть книгу по id")
    void shouldGetBookById() throws Exception {
        BookDto book = easyRandom.nextObject(BookDto.class);
        long id = book.getId();
        when(bookService.getBy(id)).thenReturn(book);

        mvc.perform(get("/api/book/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(book)));

        verify(bookService, times(1)).getBy(id);
    }

    @Test
    @DisplayName("POST-запрос: сохранить полученную книгу")
    void shouldSaveBook() throws Exception {
        BookDto book = easyRandom.nextObject(BookDto.class);
        when(bookService.save(book)).thenReturn(book);

        mvc.perform(post("/api/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(book)))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(book)));

        verify(bookService, times(1)).save(book);
    }

    @Test
    @DisplayName("DELETE-запрос: удалить книгу по полученному id")
    void shouldDeleteBook() throws Exception {
        long id = 777;
        mvc.perform(delete("/api/book/{id}", id))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteBy(id);
    }
}