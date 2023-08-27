package ru.job4j.cars.repository;

import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Optional;

public interface FileRepository {
    void save(File file);
    Optional<File> findById(int id);
    void deleteById(int id);
    List<File> findAll();
}
