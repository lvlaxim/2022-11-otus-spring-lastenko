package ru.lastenko.mongolibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.mongolibrary.model.Book;
import ru.lastenko.mongolibrary.model.Comment;
import ru.lastenko.mongolibrary.repository.CommentRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllFor(Book book) {
        return commentRepository.findAllByBook(book);
    }

    @Override
    @Transactional
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getBy(String id) {
        return commentRepository.findById(id)
                .orElseThrow();
    }

    @Override
    @Transactional
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }
}