package ru.job4j.cars.repository.car;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HbnCarRepository implements CarRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Car> findAll() {
        return crudRepository.query("from Car", Car.class);
    }

    @Override
    public List<Car> findByName(String name) {
        return crudRepository.query("from Car c where c.carBrand.name = :fName", Car.class,
                Map.of("fName", name));
    }

    @Override
    public void save(Car car) {
        crudRepository.run(session -> session.persist(car));
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete Car where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public Optional<Car> findById(int id) {
        return crudRepository.optional("from Car c where c.id = :fId", Car.class,
                Map.of("fId", id));
    }

    @Override
    public void update(Car car) {
        crudRepository.run(session -> session.update(car));
    }
}