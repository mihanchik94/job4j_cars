package ru.job4j.cars.repository.fuelType;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.FuelType;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnFuelTypeRepository implements FuelTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<FuelType> findAll() {
        return crudRepository.query("from FuelType", FuelType.class);
    }

    @Override
    public void save(FuelType fuelType) {
        crudRepository.run(session -> session.persist(fuelType));
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete from FuelType where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public Optional<FuelType> findById(int id) {
        return crudRepository.optional("from FuelType where id = :fId", FuelType.class,
                Map.of("fId", id));
    }

    @Override
    public Optional<FuelType> findByName(String name) {
        return crudRepository.optional("from FuelType where name = :fName", FuelType.class,
                Map.of("fName", name));
    }
}
