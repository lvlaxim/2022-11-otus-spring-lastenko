package ru.lastenko.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lastenko.library.model.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;
import static ru.lastenko.library.model.Book.Fields.author;
import static ru.lastenko.library.model.Book.Fields.genre;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {

    public static final String JAVAX_PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";

    public static final String INCORRECT_BOOK_ID = "Incorrect book id";

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        query.setHint(JAVAX_PERSISTENCE_FETCHGRAPH, getBookEntityGraph());
        return query.getResultList();
    }

    @Override
    public Book insert(Book book) {
        entityManager.persist(book);
        return book;
    }

    @Override
    public Book getBy(long id) {
        Book book = entityManager.find(Book.class, id,
                Map.of(JAVAX_PERSISTENCE_FETCHGRAPH, getBookEntityGraph()));
        if (isNull(book)) {
            throw new IllegalArgumentException(INCORRECT_BOOK_ID);
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        if (book.getId() == 0) {
            throw new IllegalArgumentException(INCORRECT_BOOK_ID);
        }
        getBy(book.getId());
        return entityManager.merge(book);
    }

    @Override
    public void delete(Book book) {
        Book managedBook = getBy(book.getId());
        entityManager.remove(managedBook);
    }

    private EntityGraph<Book> getBookEntityGraph() {
        EntityGraph<Book> bookEntityGraph = entityManager.createEntityGraph(Book.class);
        bookEntityGraph.addAttributeNodes(author, genre);
        return bookEntityGraph;
    }
}