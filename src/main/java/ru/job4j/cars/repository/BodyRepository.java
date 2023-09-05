package ru.job4j.cars.repository;

import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

public interface BodyRepository {
    List<Body> findAll();
    void save(Body body);
    void delete(int id);
    Optional<Body> findById(int id);
    Optional<Body> findByName(String name);
}
