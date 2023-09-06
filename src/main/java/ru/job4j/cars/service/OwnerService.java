package ru.job4j.cars.service;

import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerService {
    List<Owner> findByName(String name);
    void save(Owner owner);
    void delete(int id);
    Optional<Owner> findById(int id);
    List<Owner> findAll();
}