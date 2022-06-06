package model;


import lombok.Data;

import java.util.List;

@Data
public class Check {
    private final Question question;
    private final List<Answer> answers;

    public boolean hasAnswerOptions() {
        return answers.size() > 1;
    }
}
