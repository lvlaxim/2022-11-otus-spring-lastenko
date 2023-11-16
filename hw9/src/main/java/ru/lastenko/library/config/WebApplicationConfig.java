package ru.lastenko.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.lastenko.library.formater.AuthorDtoFormatter;
import ru.lastenko.library.formater.GenreDtoFormatter;

@Configuration
public class WebApplicationConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new GenreDtoFormatter());
        registry.addFormatter(new AuthorDtoFormatter());
    }

}