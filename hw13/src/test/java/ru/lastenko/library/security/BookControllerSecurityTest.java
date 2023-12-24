package ru.lastenko.library.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import ru.lastenko.library.controller.BookController;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.service.AuthorService;
import ru.lastenko.library.service.BookService;
import ru.lastenko.library.service.GenreService;

import java.util.List;

import static org.junit.platform.commons.util.StringUtils.isBlank;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Механизм безопасности должен")
@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
class BookControllerSecurityTest {

    static final String ROLE_SOMEBODY = "ROLE_SOMEBODY";
    static final String ROLE_ADMIN = "ROLE_ADMIN";
    static final String ROLE_USER = "ROLE_USER";
    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private AppUserService appUserService;

    @Autowired
    private MockMvc mvc;

    @AfterEach
    void doAfterEach() {
        SecurityContextHolder.clearContext();
    }

    @ParameterizedTest
    @CsvSource({
            "/book,",
            "/book," + ROLE_USER,
            "/book," + ROLE_ADMIN,
            "/book," + ROLE_SOMEBODY,
            "/book/new," + ROLE_USER,
            "/book/new," + ROLE_ADMIN,
            "/book/{id}," + ROLE_USER,
            "/book/{id}," + ROLE_ADMIN
    })
    @DisplayName("предоставить доступ пользователю к GET-методу согласно роли:")
    void shouldGrantAccessToGetMethodByRole(String urlTemplate, String role) throws Exception {
        long id = 1;
        var book = new BookDto();
        book.setId(id);
        when(bookService.getBy(book.getId())).thenReturn(book);

        setUserWithRole(role);
        mvc.perform(get(urlTemplate, id))
                .andExpect(status().isOk());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/book/new",
            "/book/1"
    })
    @DisplayName("перенаправить на страницу входа пользователя без аутентификации, обращающегося к GET-методу:")
    void shouldRedirectToLoginPageForUnauthorizedToGetMethod(String urlTemplate) throws Exception {
        mvc.perform(get(urlTemplate))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/book/new",
            "/book/1"
    })
    @DisplayName("отказать в доступе пользователю с неподходящей ролью, обращающегося к GET-методу:")
    void shouldDenyAccessToGetMethodForWrongRole(String urlTemplate) throws Exception {
        setUserWithRole(ROLE_SOMEBODY);
        mvc.perform(get(urlTemplate))
                .andExpect(status().isForbidden());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/book",
            "/book/delete"
    })
    @DisplayName("предоставить доступ пользователю с ролью 'ADMIN' к POST-методу:")
    void shouldGrantAccessForAdminToPostMethod(String urlTemplate) throws Exception {
        setUserWithRole(ROLE_ADMIN);
        mvc.perform(post(urlTemplate).param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlTemplate("/book"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/book",
            "/book/delete"
    })
    @DisplayName("перенаправить на страницу входа пользователя без аутентификации обращающегося к POST-методу:")
    void shouldRedirectToLoginPageForUnauthorizedForPostMethod(String urlTemplate) throws Exception {
        mvc.perform(post(urlTemplate).param("id", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "/book",
            "/book/delete"
    })
    @DisplayName("отказать в доступе пользователю с неподходящей ролью, обращающегося к POST-методу:")
    void shouldDenyAccessToPostMethodForWrongRole(String urlTemplate) throws Exception {
        setUserWithRole(ROLE_SOMEBODY);
        mvc.perform(post(urlTemplate).param("id", "1"))
                .andExpect(status().isForbidden());
    }

    private void setUserWithRole(String role) {
        if (isBlank(role)) {
            return;
        }
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                "username", null, List.of(new SimpleGrantedAuthority(role)));
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

}
