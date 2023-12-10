package ru.lastenko.library.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;

import java.util.List;

public interface CommentRepository extends ReactiveCrudRepository<Comment, String> {

    Mono<List<Comment>> findAllByBook(Book book);

    Mono<Void> deleteAllByBook(Book book);
}