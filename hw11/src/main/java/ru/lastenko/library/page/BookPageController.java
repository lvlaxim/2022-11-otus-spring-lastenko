package ru.lastenko.library.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.mapper.BookMapper;
import ru.lastenko.library.repository.BookRepository;

import static java.util.Objects.isNull;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    @GetMapping("/")
    public String getBooksListPage(Model model) {
        return "bookList";
    }

    @GetMapping("/edit")
    public String getBookEditPage(@RequestParam(required = false) String id, Model model) {
        Mono<BookDto> bookDtoMono = isNull(id) ? Mono.just(new BookDto())
                : bookRepository.findById(id)
                        .map(bookMapper::mapToDto);
        model.addAttribute("book", bookDtoMono);
        return "editBook";
    }

}