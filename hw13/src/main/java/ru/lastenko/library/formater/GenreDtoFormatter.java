package ru.lastenko.library.formater;

import ru.lastenko.library.dto.GenreDto;

import java.text.ParseException;
import java.util.Locale;
import java.util.Map;

import static java.lang.Long.parseLong;
import static ru.lastenko.library.dto.GenreDto.Fields.id;
import static ru.lastenko.library.dto.GenreDto.Fields.name;

public class GenreDtoFormatter extends AbstractFormatter<GenreDto> {

    @Override
    public GenreDto parse(String text, Locale locale) throws ParseException {
        Map<String, String> data = getDataOf(GenreDto.class, text);
        return new GenreDto()
                .setId(parseLong(data.get(id)))
                .setName(data.get(name));
    }

    @Override
    public String print(GenreDto object, Locale locale) {
        return object.toString();
    }

}