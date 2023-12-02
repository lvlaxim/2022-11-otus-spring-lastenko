package ru.lastenko.library.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.lastenko.library.dto.BookDto;
import ru.lastenko.library.service.BookService;

import static java.util.Objects.isNull;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    private final BookService bookService;

    @GetMapping("/")
    public String getBooksListPage(Model model) {
        return "bookList";
    }

    @GetMapping("/edit")
    public String getBookEditPage(@RequestParam(required = false) Long id, Model model) {
        BookDto bookDto = isNull(id) ? new BookDto() : bookService.getBy(id);
        model.addAttribute("book", bookDto);
        return "editBook";
    }

}