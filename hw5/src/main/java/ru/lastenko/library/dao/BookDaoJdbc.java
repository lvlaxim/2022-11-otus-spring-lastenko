package ru.lastenko.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.lastenko.library.domain.Book;

import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public int insert(Book book) {
        return namedParameterJdbcOperations.update("insert into books (name, author_id, genre_id) " +
                        "values(:name, :author_id, :genre_id)",
                Map.of(
                        "name", book.getName(),
                        "author_id", book.getAuthor().getId(),
                        "genre_id", book.getGenre().getId())
        );
    }

    @Override
    public Book getById(long id) {
        return null;
    }

    @Override
    public void update(Book book) {

    }

    @Override
    public void deleteById(long id) {

    }
}
