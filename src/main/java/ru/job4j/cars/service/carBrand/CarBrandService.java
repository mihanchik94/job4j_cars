package ru.job4j.cars.service.carBrand;

import ru.job4j.cars.model.CarBrand;

import java.util.List;
import java.util.Optional;

public interface CarBrandService {
    Optional<CarBrand> save(CarBrand carBrand);
    void delete(int id);
    List<CarBrand> findAll();
    Optional<CarBrand> findById(int id);
    Optional<CarBrand> findByName(String name);
}