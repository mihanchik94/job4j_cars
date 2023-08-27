package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.File;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HbnFileRepository implements FileRepository {
    private final CrudRepository crudRepository;

    @Override
    public void save(File file) {
        crudRepository.run(session -> session.persist(file));
    }

    @Override
    public Optional<File> findById(int id) {
        return crudRepository.optional("from File f where f.id = :fId", File.class,
                Map.of("fId", id));
    }

    @Override
    public void deleteById(int id) {
        crudRepository.run("delete File where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public List<File> findAll() {
        return crudRepository.query("from File", File.class);
    }
}