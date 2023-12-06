package ru.job4j.cars.repository.car;

import ru.job4j.cars.model.Car;

import java.util.List;
import java.util.Optional;

public interface CarRepository {
    List<Car> findAll();
    List<Car> findByName(String name);
    void save(Car car);
    void delete(int id);
    Optional<Car> findById(int id);
    void update(Car car);
}