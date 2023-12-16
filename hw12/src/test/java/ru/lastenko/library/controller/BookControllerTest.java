package ru.lastenko.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.lastenko.library.dto.AuthorDto;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.dto.GenreDto;
import ru.lastenko.library.service.AuthorService;
import ru.lastenko.library.service.BookService;
import ru.lastenko.library.service.GenreService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с книгами должен обработать")
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false) // отключаем безопасность (проверяем отдельно)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("GET-запрос: вернуть имя html-вью \"bookList\" и модель наполненную книгами")
    void shouldReturnBookListViewWithBooks() throws Exception {
        var books = easyRandom.objects(BookDto.class, 3).toList();
        when(bookService.getAll()).thenReturn(books);

        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("books", books))
                .andExpect(view().name("bookList"));

    }

    @Test
    @DisplayName("GET-запрос: вернуть имя html-вью \"editBook\" и модель наполненную новой книгой, жанрами и авторами")
    void shouldReturnEditBookViewWithNewBookAndGenresAndAuthors() throws Exception {
        var genres = easyRandom.objects(GenreDto.class, 3).toList();
        when(genreService.getAll()).thenReturn(genres);
        var authors = easyRandom.objects(AuthorDto.class, 3).toList();
        when(authorService.getAll()).thenReturn(authors);

        mvc.perform(get("/book/new"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new BookDto()))
                .andExpect(model().attribute("genres", genres))
                .andExpect(model().attribute("authors", authors))
                .andExpect(view().name("editBook"));
    }

    @Test
    @DisplayName("GET-запрос: вернуть имя html-вью \"editBook\" и модель наполненную книгой, жанрами и авторами")
    void shouldReturnEditBookViewWithBookAndGenresAndAuthors() throws Exception {
        var book = easyRandom.nextObject(BookDto.class);
        when(bookService.getBy(book.getId())).thenReturn(book);
        var genres = easyRandom.objects(GenreDto.class, 3).toList();
        when(genreService.getAll()).thenReturn(genres);
        var authors = easyRandom.objects(AuthorDto.class, 3).toList();
        when(authorService.getAll()).thenReturn(authors);

        mvc.perform(get("/book/{id}", String.valueOf(book.getId())))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", book))
                .andExpect(model().attribute("genres", genres))
                .andExpect(model().attribute("authors", authors))
                .andExpect(view().name("editBook"));
    }

    @Test
    @DisplayName("POST-запрос: вызвать метод сохраняющий полученную книгу, сделать редирект на список книг")
    void shouldSaveBook() throws Exception {
        var bookDto = new BookDto();
        mvc.perform(post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(bookDto)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/book"));

        verify(bookService, times(1)).save(bookDto);
    }

    @Test
    @DisplayName("POST-запрос: вызвать метод удаляющий книгу по полученному id, сделать редирект на список книг")
    void shouldDeleteBook() throws Exception {
        long id = 777;
        mvc.perform(post("/book/delete").param("id", String.valueOf(id)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/book"));

        verify(bookService, times(1)).deleteBy(id);
    }
}