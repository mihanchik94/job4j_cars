package ru.job4j.cars.service.carModel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.repository.carModel.CarModelRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarModelService implements CarModelService {
    private final CarModelRepository carModelRepository;

    @Override
    public Optional<CarModel> save(CarModel carModel) {
        return carModelRepository.save(carModel);
    }

    @Override
    public void delete(int id) {
        carModelRepository.delete(id);
    }

    @Override
    public List<CarModel> findAll() {
        return carModelRepository.findAll();
    }

    @Override
    public List<CarModel> findByCarBrandName(String carBrandName) {
        return carModelRepository.findByCarBrandName(carBrandName);
    }

    @Override
    public Optional<CarModel> findById(int id) {
        return carModelRepository.findById(id);
    }

    @Override
    public Optional<CarModel> findByName(String name) {
        return carModelRepository.findByName(name);
    }
}