package ru.job4j.cars.repository.driveType;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.DriveType;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnDriveTypeRepository implements DriveTypeRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<DriveType> findAll() {
        return crudRepository.query("from DriveType", DriveType.class);
    }

    @Override
    public void save(DriveType driveType) {
        crudRepository.run(session -> session.persist(driveType));
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete from DriveType where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public Optional<DriveType> findById(int id) {
        return crudRepository.optional("from DriveType where id = :fId", DriveType.class,
                Map.of("fId", id));
    }

    @Override
    public Optional<DriveType> findByName(String name) {
        return crudRepository.optional("from DriveType where name = :fName", DriveType.class,
                Map.of("fName", name));
    }
}