package ru.job4j.cars.service.body;

import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

public interface BodyService {
    List<Body> findAll();
    void save(Body body);
    void delete(int id);
    Optional<Body> findById(int id);
    Optional<Body> findByName(String name);
}