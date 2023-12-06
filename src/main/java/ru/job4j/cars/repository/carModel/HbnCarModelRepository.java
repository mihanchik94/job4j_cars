package ru.job4j.cars.repository.carModel;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.repository.CrudRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class HbnCarModelRepository implements CarModelRepository {
    private CrudRepository crudRepository;

    @Override
    public Optional<CarModel> save(CarModel carModel) {
        try {
            crudRepository.run(session -> session.persist(carModel));
            return Optional.of(carModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public void delete(int id) {
        crudRepository.run("delete from CarModel where id = :fId",
                Map.of("fId", id));
    }

    @Override
    public List<CarModel> findAll() {
        return crudRepository.query("from CarModel", CarModel.class);
    }

    @Override
    public List<CarModel> findByCarBrandName(String carBrandName) {
        return crudRepository.query("from CarModel m join fetch m.carBrand where m.carBrand.name = :fCarBrandName", CarModel.class,
                Map.of("fCarBrandName", carBrandName));
    }

    @Override
    public Optional<CarModel> findById(int id) {
        return crudRepository.optional("from CarModel where id = :fId", CarModel.class,
                Map.of("fId", id));
    }

    @Override
    public Optional<CarModel> findByName(String name) {
        return crudRepository.optional("from CarModel where name = :fName", CarModel.class,
                Map.of("fName", name));
    }
}