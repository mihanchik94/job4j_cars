package ru.job4j.cars.repository;

import ru.job4j.cars.model.DriveType;

import java.util.List;
import java.util.Optional;

public interface DriveTypeRepository {
    List<DriveType> findAll();
    void save(DriveType driveType);
    void delete(int id);
    Optional<DriveType> findById(int id);
    Optional<DriveType> findByName(String name);
}