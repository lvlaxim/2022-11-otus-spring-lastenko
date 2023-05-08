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

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    public static final String JAVAX_PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        setBookEntityGraphTo(query);
        return query.getResultList();
    }

    @Override
    public Book insert(Book book) {
        entityManager.persist(book);
        return book;
    }

    @Override
    public Book getBy(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-entity-graph");
        Book book = entityManager.find(Book.class, id,
                Map.of(JAVAX_PERSISTENCE_FETCHGRAPH, entityGraph));
        if (isNull(book)) {
            throw new IllegalArgumentException("Incorrect book id");
        }
        return book;
    }

    @Override
    public Book update(Book book) {
        return entityManager.merge(book);
    }

    @Override
    public void delete(Book book) {
        Book mergedBook = entityManager.merge(book);
        entityManager.remove(mergedBook);
    }

    private void setBookEntityGraphTo(TypedQuery<Book> query) {
        EntityGraph<?> bookEntityGraph = entityManager.getEntityGraph("book-entity-graph");
        query.setHint(JAVAX_PERSISTENCE_FETCHGRAPH, bookEntityGraph);
    }
}
