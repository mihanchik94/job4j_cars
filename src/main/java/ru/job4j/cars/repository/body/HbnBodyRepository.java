package ru.job4j.cars.repository.body;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Body;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnBodyRepository implements BodyRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Body> findAll() {
        return crudRepository.query("from Body", Body.class);
    }

    @Override
    public void save(Body body) {
        crudRepository.run(session -> session.persist(body));
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete from Body where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public Optional<Body> findById(int id) {
        return crudRepository.optional("from Body where id = :fId", Body.class,
                Map.of("fId", id));
    }

    @Override
    public Optional<Body> findByName(String name) {
        return crudRepository.optional("from Body where name = :fName", Body.class,
                Map.of("fName", name));
    }
}