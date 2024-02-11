package ru.lastenko.library.indicator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.lastenko.library.service.BookService;

@Component
@RequiredArgsConstructor
public class LibraryHealthIndicator implements HealthIndicator {

    private final BookService bookService;

    @Override
    public Health health() {
        boolean isLibraryEmpty = bookService.isLibraryEmpty();
        return isLibraryEmpty ? buildHealthDown() : Health.up().build();
    }

    private Health buildHealthDown() {
        return Health.down()
                .withDetail("message", "Караул! Кто-то удалил все книги")
                .build();
    }

}
