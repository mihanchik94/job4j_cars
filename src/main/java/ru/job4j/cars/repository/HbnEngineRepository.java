package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Engine;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HbnEngineRepository implements EngineRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Engine> findAll() {
        return crudRepository.query("from Engine", Engine.class);
    }

    @Override
    public List<Engine> findByName(String name) {
        return crudRepository.query("from Engine e where e.name = :fName", Engine.class,
                Map.of("fName", name));
    }

    @Override
    public void save(Engine engine) {
        crudRepository.run(session -> session.persist(engine));
    }

    @Override
    public Optional<Engine> findById(int id) {
        return crudRepository.optional("from Engine e where e.id = :fId", Engine.class,
                Map.of("fId", id));
    }
}