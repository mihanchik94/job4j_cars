package ru.job4j.cars.service.carBrand;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.carBrand.CarBrandRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleCarBrandService implements CarBrandService {
    private final CarBrandRepository carBrandRepository;

    @Override
    public Optional<CarBrand> save(CarBrand carBrand) {
        Optional<CarBrand> optionalCarBrand = carBrandRepository.findByName(carBrand.getName());
        if (optionalCarBrand.isEmpty()) {
            return carBrandRepository.save(carBrand);
        }
        return optionalCarBrand;
    }

    @Override
    public void delete(int id) {
        carBrandRepository.delete(id);
    }

    @Override
    public List<CarBrand> findAll() {
        return carBrandRepository.findAll();
    }

    @Override
    public Optional<CarBrand> findById(int id) {
        return carBrandRepository.findById(id);
    }

    @Override
    public Optional<CarBrand> findByName(String name) {
        return carBrandRepository.findByName(name);
    }
}