package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.dao.BookDao;
import ru.lastenko.library.domain.Book;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookInputService bookInputService;
    private final BookDao bookDao;
    private final IdentifiableService identifiableService;
    private final IOService ioService;


    @Override
    public List<Book> getAll() {
        return bookDao.getAll();
    }

    @Override
    public void getAndSave() {
        Book book = bookInputService.getBook();
        bookDao.insert(book);
    }

    @Override
    public Book getBy(long id) {
        return bookDao.getById(id);
    }

    @Override
    public void selectAndUpdate() {
        Book selectedBook = selecteBook();
        Book bookWithUpdates = bookInputService.getBook();
        Book updatedBook = new Book(
                selectedBook.getId(),
                bookWithUpdates.getName(),
                bookWithUpdates.getAuthor(),
                bookWithUpdates.getGenre());
        bookDao.update(updatedBook);
    }

    @Override
    public void selectAndDelete() {
        Book selectedBook = selecteBook();
        long selectedBookId = selectedBook.getId();
        bookDao.delete(selectedBookId);
    }

    private Book selecteBook() {
        ioService.outputString("Выберите книгу из списка:");
        List<Book> books = bookDao.getAll();
        return identifiableService.selectByIdFrom(books);
    }
}
