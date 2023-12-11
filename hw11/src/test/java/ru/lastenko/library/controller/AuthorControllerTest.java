package ru.lastenko.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.lastenko.library.dto.AuthorDto;
import ru.lastenko.library.mapper.AuthorMapper;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.repository.AuthorRepository;

import java.util.List;

import static org.mockito.Mockito.when;

@DisplayName("Контроллер для работы с авторами должен обработать")
@WebFluxTest(AuthorController.class)
@TestPropertySource(properties = "mongock.enabled=false") // https://github.com/mongock/mongock/issues/239
class AuthorControllerTest {

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private AuthorMapper authorMapper;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("GET-запрос: вернуть всех авторов")
    void shouldGetAllAuthors() {
        Author author = new Author();
        when(authorRepository.findAll()).thenReturn(Flux.fromIterable(List.of(author)));
        AuthorDto authorDto = new AuthorDto();
        when(authorMapper.mapToDto(author)).thenReturn(authorDto);

        webClient.get().uri("/api/author")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(1)
                .contains(authorDto);
    }
}