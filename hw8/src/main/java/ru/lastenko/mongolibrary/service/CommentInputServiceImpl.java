package ru.lastenko.mongolibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.mongolibrary.model.Comment;

@Service
@RequiredArgsConstructor
public class CommentInputServiceImpl implements CommentInputService {

    private final IOService ioService;

    @Override
    public Comment getComment() {
        return new Comment(null, null, getText());
    }

    private String getText() {
        ioService.outputString("Введите комментарий");
        return ioService.readString();
    }
}