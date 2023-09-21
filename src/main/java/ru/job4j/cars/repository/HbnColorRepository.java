package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnColorRepository implements ColorRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Color> findAll() {
        return crudRepository.query("from Color", Color.class);
    }

    @Override
    public void save(Color color) {
        crudRepository.run(session -> session.persist(color));
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete from Color where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public Optional<Color> findById(int id) {
        return crudRepository.optional("from Color where id = :fId", Color.class,
                Map.of("fId", id));
    }

    @Override
    public Optional<Color> findByName(String name) {
        return crudRepository.optional("from Color where name = :fName", Color.class,
                Map.of("fName", name));
    }
}