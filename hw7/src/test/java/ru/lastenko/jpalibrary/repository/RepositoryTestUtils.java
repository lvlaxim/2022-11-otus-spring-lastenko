package ru.lastenko.jpalibrary.repository;

import ru.lastenko.jpalibrary.model.Author;
import ru.lastenko.jpalibrary.model.Book;
import ru.lastenko.jpalibrary.model.Comment;
import ru.lastenko.jpalibrary.model.Genre;

class RepositoryTestUtils {

    static final Author AUTHOR_1 = new Author(1, "Автор1");
    static final Author AUTHOR_2 = new Author(2, "Автор2");

    static final Genre GENRE_1 = new Genre(1, "Жанр1");
    static final Genre GENRE_2 = new Genre(2, "Жанр2");

    static final Book BOOK_1 = new Book(1, "Книга1", AUTHOR_1, GENRE_1);
    static final Book BOOK_2 = new Book(2, "Книга2", AUTHOR_2, GENRE_2);
    static final Book BOOK_3 = new Book(3, "Книга3", AUTHOR_1, GENRE_2);

    static final Comment COMMENT_1 = new Comment(1, BOOK_1, "Комментарий 1");
    static final Comment COMMENT_2 = new Comment(2, BOOK_1, "Комментарий 2");
    static final Comment COMMENT_3 = new Comment(3, BOOK_2, "Комментарий 3");
}
