package ru.lastenko.library.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.lastenko.library.domain.Author;
import ru.lastenko.library.domain.Book;
import ru.lastenko.library.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class BookDaoJdbc implements BookDao {
    private final NamedParameterJdbcOperations namedParameterJdbcOperations;

    @Override
    public List<Book> getAll() {
        String sqlQuery =
                "select books.id, books.name, authors.id, authors.full_name, genres.id, genres.name " +
                        "from books left join authors on books.author_id=authors.id " +
                        "left join genres on books.genre_id=genres.id";
        return namedParameterJdbcOperations.query(sqlQuery, new BookDaoJdbc.BookMapper());
    }

    @Override
    public void insert(Book book) {
        String sqlQuery = "insert into books (name, author_id, genre_id) values(:name, :author_id, :genre_id)";
        Map<String, Object> params = Map.of(
                "name", book.getName(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId());
        namedParameterJdbcOperations.update(sqlQuery, params);
    }

    @Override
    public Book getById(long id) {
        String sqlQuery =
                "select books.id, books.name, authors.id, authors.full_name, genres.id, genres.name " +
                        "from books left join authors on books.author_id=authors.id " +
                        "left join genres on books.genre_id=genres.id " +
                        "where books.id = :id";
        Map<String, Object> params = Map.of("id", id);
        Book book;
        try {
            book = namedParameterJdbcOperations.queryForObject(sqlQuery, params, new BookMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Incorrect book id", e);
        }
        return book;
    }

    @Override
    public void update(Book book) {
        String sqlQuery = "update books set name=:name, author_id=:author_id, genre_id=:genre_id where id=:id";
        Map<String, Object> params = Map.of(
                "id", book.getId(),
                "name", book.getName(),
                "author_id", book.getAuthor().getId(),
                "genre_id", book.getGenre().getId());
        namedParameterJdbcOperations.update(sqlQuery, params);
    }

    @Override
    public void deleteById(long id) {
        String sqlQuery = "delete from books where id = :id";
        Map<String, Object> params = Map.of("id", id);
        namedParameterJdbcOperations.update(sqlQuery, params);
    }

    private static class BookMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Book(
                    rs.getLong("books.id"),
                    rs.getString("books.name"),
                    new Author(
                            rs.getLong("authors.id"),
                            rs.getString("authors.full_name")),
                    new Genre(
                            rs.getLong("genres.id"),
                            rs.getString("genres.name"))
            );
        }
    }
}
