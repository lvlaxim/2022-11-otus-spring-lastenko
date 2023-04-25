package ru.lastenko.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lastenko.library.model.Book;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class BookRepositoryJpa implements BookRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Book> getAll() {
        TypedQuery<Book> query = entityManager.createQuery("select b from Book b", Book.class);
        setBookEntityGraphTo(query);
        return query.getResultList();
    }

    @Override
    public void insert(Book book) {
        entityManager.persist(book);
    }

    @Override
    public Book getById(long id) {
        Book book = entityManager.find(Book.class, id);
        if (isNull(book)) {
            throw new IllegalArgumentException("Incorrect book id");
        }
        return book;
    }

    @Override
    public void update(Book book) {
        entityManager.merge(book);
    }

    @Override
    public void delete(Book book) {
        Book mergedBook = entityManager.merge(book);
        entityManager.remove(mergedBook);
    }

    private void setBookEntityGraphTo(TypedQuery<Book> query) {
        EntityGraph<?> bookEntityGraph = entityManager.getEntityGraph("book-entity-graph");
        query.setHint("javax.persistence.fetchgraph", bookEntityGraph);
    }
}
