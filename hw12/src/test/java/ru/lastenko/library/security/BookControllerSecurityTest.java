package ru.lastenko.library.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.lastenko.library.controller.BookController;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.service.AuthorService;
import ru.lastenko.library.service.BookService;
import ru.lastenko.library.service.GenreService;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlTemplate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Механизм безопасности должен")
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerSecurityTest {

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mvc;

    @ParameterizedTest
    @ValueSource(strings = {
            "/book",
            "/book/new",
            "/book/{id}"
    })
    @WithMockUser(username = "user")
    @DisplayName("предоставить доступ аутентифицированному пользователю к GET-методу:")
    void shouldGrantAccessForAuthorizedToGetMethod(String urlTemplate) throws Exception {
        long id = 1;
        var book = new BookDto();
        book.setId(id);
        when(bookService.getBy(book.getId())).thenReturn(book);

        mvc.perform(get(urlTemplate, id))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/book",
            "/book/new",
            "/book/1"
    })
    @DisplayName("отказать в доступе пользователю без аутентификации к GET-методу:")
    void shouldDeniedForUnauthorizedToGetMethod(String urlTemplate) throws Exception {
        mvc.perform(get(urlTemplate))
                .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/book",
            "/book/delete"
    })
    @WithMockUser(username = "user")
    @DisplayName("предоставить доступ аутентифицированному пользователю к POST-методу:")
    void shouldGrantAccessForAuthorizedToPostMethod(String urlTemplate) throws Exception {
        mvc.perform(post(urlTemplate).param("id", "1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/book"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/book",
            "/book/delete"
    })
    @DisplayName("отказать в доступе пользователю без аутентификации к POST-методу:")
    void shouldDeniedForUnauthorizedToPostMethod(String urlTemplate) throws Exception {
        mvc.perform(post(urlTemplate).param("id", "1")
                        .with(csrf()))
                .andExpect(status().isUnauthorized());
    }

}
