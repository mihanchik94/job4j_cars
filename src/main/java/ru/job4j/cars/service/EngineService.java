package ru.job4j.cars.service;

import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Optional;

public interface EngineService {
    List<Engine> findAll();
    List<Engine> findByName(String name);
    void save(Engine engine);
    void delete(int id);
    Optional<Engine> findById(int id);
}