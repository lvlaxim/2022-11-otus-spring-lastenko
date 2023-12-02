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
import ru.lastenko.library.dto.GenreDto;
import ru.lastenko.library.service.GenreService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с авторами должен обработать")
@WebMvcTest(GenreConroller.class)
class GenreConrollerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    private final EasyRandom easyRandom = new EasyRandom();

    @Test
    @DisplayName("GET-запрос: вернуть все жанры")
    void shouldGetAllGenress() throws Exception {
        List<GenreDto> genres = easyRandom.objects(GenreDto.class, 3).toList();
        when(genreService.getAll()).thenReturn(genres);

        mvc.perform(get("/api/genre"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(mapper.writeValueAsString(genres)));

        verify(genreService, times(1)).getAll();
    }

}