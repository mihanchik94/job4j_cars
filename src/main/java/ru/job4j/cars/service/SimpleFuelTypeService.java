package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.FuelType;
import ru.job4j.cars.repository.FuelTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleFuelTypeService implements FuelTypeService {
    private final FuelTypeRepository fuelTypeRepository;


    @Override
    public List<FuelType> findAll() {
        return fuelTypeRepository.findAll();
    }

    @Override
    public void save(FuelType fuelType) {
        fuelTypeRepository.save(fuelType);
    }

    @Override
    public void delete(int id) {
        fuelTypeRepository.delete(id);
    }

    @Override
    public Optional<FuelType> findById(int id) {
        return fuelTypeRepository.findById(id);
    }

    @Override
    public Optional<FuelType> findByName(String name) {
        return fuelTypeRepository.findByName(name);
    }
}