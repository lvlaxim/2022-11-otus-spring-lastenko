package ru.lastenko.library.dto;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Data
@Accessors(chain = true)
@FieldNameConstants
public class AuthorDto {

    private String id;

    private String fullName;
}