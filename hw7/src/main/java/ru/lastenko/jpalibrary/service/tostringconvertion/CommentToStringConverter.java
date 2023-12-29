package ru.lastenko.jpalibrary.service.tostringconvertion;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.jpalibrary.model.Comment;

@Service
@RequiredArgsConstructor
public class CommentToStringConverter implements ToStringConverter<Comment> {
    @Override
    public Class<Comment> getConvertedClass() {
        return Comment.class;
    }

    @Override
    public String convert(Comment comment) {
        return String.format("Комментарий - ID: %s, текст: %s", comment.getId(), comment.getText());
    }
}