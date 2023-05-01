package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;
import ru.lastenko.library.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final CommentInputService commentInputService;
    private final IOService ioService;
    private final IdentifiableService identifiableService;

    @Override
    public List<Comment> getAllFor(Book book) {
        return commentRepository.getAllFor(book);
    }

    @Override
    @Transactional
    public void insertCommentFor(Book selectedBook) {
        Comment comment = commentInputService.getComment();
        comment.setBook(selectedBook);
        commentRepository.insert(comment);
    }

    @Override
    public Comment getBy(long id) {
        Comment comment = null;
        try {
            comment = commentRepository.getBy(id);
        } catch (IllegalArgumentException e) {
            ioService.outputString("Введен несущестующий id!");
        }
        return comment;
    }

    @Override
    @Transactional
    public Comment update(Comment comment) {
        Comment commentWithUpdates = commentInputService.getComment();
        commentWithUpdates.setId(comment.getId());
        commentWithUpdates.setBook(comment.getBook());
        return commentRepository.update(commentWithUpdates);
    }

    @Override
    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    @Override
    public Comment selectBookComment(Book book) {
        ioService.outputString("Выберите комментарий из списка:");
        List<Comment> bookComments = getAllFor(book);
        return identifiableService.selectByIdFrom(bookComments);
    }


}
