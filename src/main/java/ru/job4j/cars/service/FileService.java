package ru.job4j.cars.service;

import ru.job4j.cars.dto.FileDto;

import java.util.List;
import java.util.Optional;

public interface FileService {
    void save(FileDto file);
    Optional<FileDto> findById(int id);
    void deleteById(int id);
    List<FileDto> findAll();
}