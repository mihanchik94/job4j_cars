package ru.job4j.cars.service;

import ru.job4j.cars.model.GearBox;

import java.util.List;
import java.util.Optional;

public interface GearBoxService {
    List<GearBox> findAll();
    void save(GearBox gearBox);
    void delete(int id);
    Optional<GearBox> findById(int id);
    Optional<GearBox> findByName(String name);
}