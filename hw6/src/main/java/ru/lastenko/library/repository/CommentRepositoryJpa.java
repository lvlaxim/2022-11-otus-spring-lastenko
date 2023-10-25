package ru.lastenko.library.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Subgraph;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Map;

import static ru.lastenko.library.model.Book.Fields.author;
import static ru.lastenko.library.model.Book.Fields.genre;
import static ru.lastenko.library.model.Comment.Fields.book;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryJpa implements CommentRepository {

    public static final String JAVAX_PERSISTENCE_FETCHGRAPH = "javax.persistence.fetchgraph";

    public static final String INCORRECT_COMMENT_ID_MSG = "Incorrect comment id";

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Comment> getAllFor(Book book) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "select c from Comment c where c.book = :book",
                Comment.class);
        query.setParameter("book", book);
        query.setHint(JAVAX_PERSISTENCE_FETCHGRAPH, getCommentWithBookEntityGraph());
        return query.getResultList();
    }

    @Override
    public Comment insert(Comment comment) {
        entityManager.persist(comment);
        return comment;
    }

    @Override
    public Comment getBy(long id) {
        Comment comment = entityManager.find(Comment.class, id);
        Assert.notNull(comment, INCORRECT_COMMENT_ID_MSG);
        return comment;
    }

    @Override
    public Comment update(Comment comment) {
        Assert.state(comment.getId() > 0, INCORRECT_COMMENT_ID_MSG);
        Comment managedComment = entityManager.find(Comment.class, comment.getId(),
                Map.of(JAVAX_PERSISTENCE_FETCHGRAPH, getCommentWithBookEntityGraph()));
        Assert.notNull(managedComment, INCORRECT_COMMENT_ID_MSG);
        return entityManager.merge(comment);
    }

    @Override
    public void delete(Comment comment) {
        Comment managedComment = getBy(comment.getId());
        entityManager.remove(managedComment);
    }

    private EntityGraph<Comment> getCommentWithBookEntityGraph() {
        EntityGraph<Comment> commentEntityGraph = entityManager.createEntityGraph(Comment.class);
        Subgraph<Book> bookSubgraph = commentEntityGraph.addSubgraph(book, Book.class);
        bookSubgraph.addAttributeNodes(author, genre);
        return commentEntityGraph;
    }
}