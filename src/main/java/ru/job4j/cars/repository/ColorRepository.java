package ru.job4j.cars.repository;

import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Optional;

public interface ColorRepository {
    List<Color> findAll();
    void save(Color color);
    void delete(int id);
    Optional<Color> findById(int id);
    Optional<Color> findByName(String name);
}