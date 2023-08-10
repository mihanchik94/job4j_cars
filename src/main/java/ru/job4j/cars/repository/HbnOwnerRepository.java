package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Owner;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HbnOwnerRepository  implements OwnerRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Owner> findByName(String name) {
        return crudRepository.query("from Owner o where o.name = :fName", Owner.class,
                Map.of("fName", name));
    }

    @Override
    public void save(Owner owner) {
        crudRepository.run(session -> session.persist(owner));
    }

    @Override
    public Optional<Owner> findById(int id) {
        return crudRepository.optional("from Owner o where o.id = fId", Owner.class,
                Map.of("fId", id));
    }
}