package ru.job4j.cars.repository;

import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Optional;

public interface OwnerRepository {
    List<Owner> findByName(String name);
    void save(Owner owner);
    Optional<Owner> findById(int id);
}