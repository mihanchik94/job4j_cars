package ru.job4j.cars.service.car;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.car.CarRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarService implements CarService {
    private final CarRepository carRepository;

    @Override
    public List<Car> findAll() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> findByName(String name) {
        return carRepository.findByName(name);
    }

    @Override
    public void save(Car car) {
        carRepository.save(car);
    }

    @Override
    public void delete(int id) {
        carRepository.delete(id);
    }

    @Override
    public Optional<Car> findById(int id) {
        return carRepository.findById(id);
    }

    @Override
    public void update(Car car) {
        carRepository.update(car);
    }
}