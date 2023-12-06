package ru.job4j.cars.repository.carBrand;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnCarBrandRepository implements CarBrandRepository {
    private final CrudRepository crudRepository;

    @Override
    public Optional<CarBrand> save(CarBrand carBrand) {
        try {
            crudRepository.run(session -> session.persist(carBrand));
            return Optional.of(carBrand);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete from CarBrand where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public List<CarBrand> findAll() {
        return crudRepository.query("from CarBrand", CarBrand.class);
    }

    @Override
    public Optional<CarBrand> findById(int id) {
        return crudRepository.optional("from CarBrand where id = :fId", CarBrand.class,
                Map.of("fId", id));
    }

    @Override
    public Optional<CarBrand> findByName(String name) {
        return crudRepository.optional("from CarBrand where name = :fName", CarBrand.class,
                Map.of("fName", name));
    }
}