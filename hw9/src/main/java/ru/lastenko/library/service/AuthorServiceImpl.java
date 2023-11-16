package ru.lastenko.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.lastenko.library.dto.AuthorDto;
import ru.lastenko.library.mapper.AuthorMapper;
import ru.lastenko.library.repository.AuthorRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    @Transactional(readOnly = true)
    public List<AuthorDto> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(authorMapper::mapToDto)
                .toList();
    }
}