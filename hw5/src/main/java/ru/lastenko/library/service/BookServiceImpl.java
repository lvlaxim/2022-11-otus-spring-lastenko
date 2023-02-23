package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.dao.BookDao;
import ru.lastenko.library.domain.Book;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookInputService bookInputService;
    private final BookDao bookDao;

    @Override
    public void inputAndSave() {
        Book book = bookInputService.getBook();
        bookDao.insert(book);
    }
}
