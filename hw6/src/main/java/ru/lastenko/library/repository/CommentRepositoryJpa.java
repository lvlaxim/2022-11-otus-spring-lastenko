package ru.lastenko.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

import static java.util.Objects.isNull;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Comment> getAllFor(Book book) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "select c from Comment c " +
                        "join fetch c.book b " +
                        "join fetch b.author " +
                        "join fetch b.genre " +
                        "where c.book = :book", Comment.class);
        query.setParameter("book", book);
        return query.getResultList();
    }

    @Override
    public Comment insert(Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public Comment getBy(long id) {
        var comment = entityManager.find(Comment.class, id);
        if (isNull(comment)) {
            throw new IllegalArgumentException("Incorrect comment id");
        }
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        return entityManager.merge(comment);
    }

    @Override
    public void delete(Comment comment) {
        Comment mergedComment = entityManager.merge(comment);
        entityManager.remove(mergedComment);
    }
}
