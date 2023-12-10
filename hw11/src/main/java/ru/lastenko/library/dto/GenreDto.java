package ru.lastenko.library.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@FieldNameConstants
public class GenreDto {

    private String id;

    private String name;
}