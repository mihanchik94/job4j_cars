package ru.job4j.cars.service.carModel;

import ru.job4j.cars.model.CarModel;

import java.util.List;
import java.util.Optional;

public interface CarModelService {
    Optional<CarModel> save(CarModel carModel);
    void delete(int id);
    List<CarModel> findAll();
    List<CarModel> findByCarBrandName(String carBrandName);
    Optional<CarModel> findById(int id);
    Optional<CarModel> findByName(String name);
}