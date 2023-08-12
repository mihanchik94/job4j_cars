package ru.job4j.cars.repository;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> findAll();
    List<Car> findByName(String name);
    void save(Car car);
    Optional<Car> findById(int id);
}