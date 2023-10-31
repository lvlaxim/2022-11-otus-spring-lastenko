package ru.lastenko.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.lastenko.library.model.Comment;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.lastenko.library.repository.RepositoryTestUtils.*;

@DisplayName("Репозиторий для работы с комментариями должен")
@DataJpaTest
class CommentRepositoryJpaTest {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("получить все комментарии к книге")
    void shouldGetAllCommentsForBook() {
        var allCommentsForBook1 = commentRepository.findAllByBook(BOOK_1);
        assertThat(allCommentsForBook1).asList()
                .hasSize(2)
                .containsExactlyInAnyOrder(COMMENT_1, COMMENT_2);
    }

    @Test
    @DisplayName("вставить комментарий в БД")
    void shouldInsertComment() {
        var text = "Новый комментарий";
        var newComment = new Comment(0, BOOK_2, text);

        var savedComment = commentRepository.save(newComment);

        var foundComment = entityManager.find(Comment.class, 100L);
        var expectedComment = new Comment(100, BOOK_2, text);
        assertThat(foundComment)
                .isNotNull()
                .isEqualTo(savedComment)
                .isEqualTo(expectedComment);
    }

    @Test
    @DisplayName("получить комментарий из БД по id")
    void shouldGetCommentById() {
        var id = COMMENT_1.getId();

        var receivedComment = commentRepository.findById(id).get();

        assertThat(receivedComment)
                .isNotNull()
                .isEqualTo(COMMENT_1);
    }

    @Test
    @DisplayName("обновить комментарий в БД")
    void shouldUpdateComment() {
        var commentId = COMMENT_1.getId();
        var commentWithUpdates = new Comment(commentId, BOOK_3, "Обновленный комментарий");

        var updatedComment = commentRepository.save(commentWithUpdates);

        var foundComment = entityManager.find(Comment.class, commentId);
        assertThat(foundComment)
                .isNotNull()
                .isEqualTo(updatedComment)
                .isEqualTo(commentWithUpdates)
                .isNotEqualTo(COMMENT_1);
    }

    @Test
    @DisplayName("удалить комментарий из БД")
    void shouldDeleteComment() {
        commentRepository.delete(COMMENT_1);
        Comment comment = entityManager.find(Comment.class, COMMENT_1.getId());
        assertThat(comment).isNull();
    }
}