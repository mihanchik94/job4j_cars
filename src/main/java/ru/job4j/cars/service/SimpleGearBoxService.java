package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.model.GearBox;
import ru.job4j.cars.repository.GearBoxRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SimpleGearBoxService implements GearBoxService {
    private final GearBoxRepository gearBoxRepository;

    @Override
    public List<GearBox> findAll() {
        return gearBoxRepository.findAll();
    }

    @Override
    public void save(GearBox gearBox) {
        gearBoxRepository.save(gearBox);
    }

    @Override
    public void delete(int id) {
        gearBoxRepository.delete(id);
    }

    @Override
    public Optional<GearBox> findById(int id) {
        return gearBoxRepository.findById(id);
    }

    @Override
    public Optional<GearBox> findByName(String name) {
        return gearBoxRepository.findByName(name);
    }
}