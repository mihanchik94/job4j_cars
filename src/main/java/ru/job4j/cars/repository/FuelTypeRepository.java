package ru.job4j.cars.repository;

import ru.job4j.cars.model.FuelType;

import java.util.List;
import java.util.Optional;

public interface FuelTypeRepository {
    List<FuelType> findAll();
    void save(FuelType fuelType);
    void delete(int id);
    Optional<FuelType> findById(int id);
    Optional<FuelType> findByName(String name);
}