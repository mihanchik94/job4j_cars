package ru.job4j.cars.repository.gearBox;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.GearBox;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnGearBoxRepository implements GearBoxRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<GearBox> findAll() {
        return crudRepository.query("from GearBox", GearBox.class);
    }

    @Override
    public void save(GearBox gearBox) {
        crudRepository.run(session -> session.persist(gearBox));
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete from GearBox where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public Optional<GearBox> findById(int id) {
        return crudRepository.optional("from GearBox where id = :fId", GearBox.class,
                Map.of("fId", id));
    }

    @Override
    public Optional<GearBox> findByName(String name) {
        return crudRepository.optional("from GearBox where name = :fName", GearBox.class,
                Map.of("fName", name));
    }
}