package ru.job4j.cars.service;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    List<Car> findAll();
    List<Car> findByName(String name);
    void save(Car car);
    void delete(int id);
    Optional<Car> findById(int id);
    void update(Car car);
}