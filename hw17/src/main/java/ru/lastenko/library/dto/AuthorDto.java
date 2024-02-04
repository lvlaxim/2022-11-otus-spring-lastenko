package ru.lastenko.library.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@FieldNameConstants
public class AuthorDto {

    private long id;

    private String fullName;
}