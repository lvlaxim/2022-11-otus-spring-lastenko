package ru.lastenko.library.page;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.service.BookService;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Контроллер для работы с html-страницами относящимися к книгам")
@WebMvcTest(BookPageController.class)
class BookPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    private final EasyRandom easyRandom = new EasyRandom();


    @Test
    @DisplayName("GET-запрос: вернуть имя html-вью \"bookList\"")
    void getBooksListPage() throws Exception {
        var books = easyRandom.objects(BookDto.class, 3).toList();
        when(bookService.getAll()).thenReturn(books);

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("bookList"));
    }

    @Test
    @DisplayName("GET-запрос: вернуть имя html-вью \"editBook\" и модель наполненную новой (пустой) книгой")
    void getBookEditPageForAdding() throws Exception {
        mvc.perform(get("/edit"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", new BookDto()))
                .andExpect(view().name("editBook"));
    }

    @Test
    @DisplayName("GET-запрос: вернуть имя html-вью \"editBook\" и модель наполненную книгой, полученной по id")
    void getBookEditPageForEditing() throws Exception {
        var book = easyRandom.nextObject(BookDto.class);
        long id = book.getId();
        when(bookService.getBy(id)).thenReturn(book);

        mvc.perform(get("/edit").param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(model().attribute("book", book))
                .andExpect(view().name("editBook"));

        verify(bookService, times(1)).getBy(id);
    }
}