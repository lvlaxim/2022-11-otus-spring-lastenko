package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.lastenko.library.model.Comment;

@Service
@RequiredArgsConstructor
public class CommentInputServiceImpl implements CommentInputService {

    private final IOService ioService;

    @Override
    public Comment getComment() {
        return new Comment(0, null, getText());
    }

    private String getText() {
        ioService.outputString("Введите комментарий");
        return ioService.readString();
    }
}