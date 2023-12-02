package ru.lastenko.library.formater;

import ru.lastenko.library.dto.AuthorDto;

import java.util.Locale;
import java.util.Map;

import static java.lang.Long.parseLong;
import static ru.lastenko.library.dto.AuthorDto.Fields.fullName;
import static ru.lastenko.library.dto.AuthorDto.Fields.id;

public class AuthorDtoFormatter extends AbstractFormatter<AuthorDto> {

    @Override
    public AuthorDto parse(String text, Locale locale) {
        Map<String, String> data = getDataOf(AuthorDto.class, text);
        return new AuthorDto()
                .setId(parseLong(data.get(id)))
                .setFullName(data.get(fullName));
    }

    @Override
    public String print(AuthorDto object, Locale locale) {
        return object.toString();
    }

}