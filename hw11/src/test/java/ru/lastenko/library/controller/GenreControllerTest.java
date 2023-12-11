package ru.lastenko.library.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.lastenko.library.dto.GenreDto;
import ru.lastenko.library.mapper.GenreMapper;
import ru.lastenko.library.model.Genre;
import ru.lastenko.library.repository.GenreRepository;

import java.util.List;

import static org.mockito.Mockito.when;

@DisplayName("Контроллер для работы с жанрами должен обработать")
@WebFluxTest(GenreController.class)
@TestPropertySource(properties = "mongock.enabled=false") // https://github.com/mongock/mongock/issues/239
class GenreControllerTest {

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private GenreMapper genreMapper;

    @Autowired
    private WebTestClient webClient;

    @Test
    @DisplayName("GET-запрос: вернуть все жанры")
    void shouldGetAllGenres() {
        Genre genre = new Genre();
        when(genreRepository.findAll()).thenReturn(Flux.fromIterable(List.of(genre)));
        GenreDto genreDto = new GenreDto();
        when(genreMapper.mapToDto(genre)).thenReturn(genreDto);

        webClient.get().uri("/api/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class)
                .hasSize(1)
                .contains(genreDto);
    }

}