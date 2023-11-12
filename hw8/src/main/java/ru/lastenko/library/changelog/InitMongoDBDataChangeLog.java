package ru.lastenko.library.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.lastenko.library.model.Author;
import ru.lastenko.library.model.Book;
import ru.lastenko.library.model.Comment;
import ru.lastenko.library.model.Genre;
import ru.lastenko.library.repository.AuthorRepository;
import ru.lastenko.library.repository.BookRepository;
import ru.lastenko.library.repository.CommentRepository;
import ru.lastenko.library.repository.GenreRepository;

import java.util.List;

@ChangeLog(order = "001")
public class InitMongoDBDataChangeLog {

    private final Author pushkin = new Author("1", "Пушкин A.C.");

    private final Author chehov = new Author("2", "Чехов А.П.");

    private final Author bulgakov = new Author("3", "Булгаков М.А.");

    private final Genre novel = new Genre("1", "Роман");

    private final Genre story = new Genre("2", "Рассказ");

    private Book masterAndMargarita;

    private Book eugeneOnegin;

    private Book collectionOfStories;

    private Book belkinsStories;

    @ChangeSet(order = "000", id = "dropDB", author = "lvlaksim", runAlways = true)
    public void dropDB(MongoDatabase database) {
        database.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "lvlaksim", runAlways = true)
    public void initAuthors(AuthorRepository repository) {
        repository.saveAll(
                List.of(pushkin, chehov, bulgakov)
        );
    }

    @ChangeSet(order = "002", id = "initGenres", author = "lvlaksim", runAlways = true)
    public void initGenres(GenreRepository repository) {
        repository.saveAll(
                List.of(novel, story)
        );
    }

    @ChangeSet(order = "003", id = "initBooks", author = "lvlaksim", runAlways = true)
    public void initBooks(BookRepository repository) {
        masterAndMargarita = new Book(null, "Мастер и Маргорита", bulgakov, novel);
        eugeneOnegin = new Book(null, "Евгений Онегин", pushkin, novel);
        collectionOfStories = new Book(null, "Сборник рассказов", chehov, story);
        belkinsStories = new Book(null, "Повести Белкина", pushkin, story);
        repository.saveAll(
                List.of(
                        masterAndMargarita,
                        eugeneOnegin,
                        collectionOfStories,
                        belkinsStories)
        );
    }

    @ChangeSet(order = "004", id = "initComments", author = "lvlaksim", runAlways = true)
    public void initComments(CommentRepository repository) {
        repository.saveAll(
                List.of(
                        new Comment(null, masterAndMargarita, "Комментарий 1"),
                        new Comment(null, masterAndMargarita, "Комментарий 2"),
                        new Comment(null, masterAndMargarita, "Комментарий 3"),
                        new Comment(null, eugeneOnegin, "Комментарий 4"),
                        new Comment(null, eugeneOnegin, "Комментарий 5"),
                        new Comment(null, collectionOfStories, "Комментарий 6")
                )
        );
    }
}
