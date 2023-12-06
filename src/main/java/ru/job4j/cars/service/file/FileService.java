package ru.job4j.cars.service.file;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface FileService {
    File save(FileDto file, Post post);
    Optional<FileDto> findById(int id);
    void deleteById(int id);
    List<FileDto> findAll();
}