package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.model.CarModel;
import ru.job4j.cars.repository.carBrand.CarBrandRepository;
import ru.job4j.cars.repository.carBrand.HbnCarBrandRepository;
import ru.job4j.cars.repository.carModel.CarModelRepository;
import ru.job4j.cars.repository.carModel.HbnCarModelRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnCarModelRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static CarModelRepository carModelRepository;
    private static CarBrandRepository carBrandRepository;

    @BeforeAll
    public static void initRepositories() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        carModelRepository = new HbnCarModelRepository(new CrudRepository(sf));
        carBrandRepository = new HbnCarBrandRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<CarModel> models = carModelRepository.findAll();
        for (CarModel model : models) {
            carModelRepository.delete(model.getId());
        }

        List<CarBrand> brands = carBrandRepository.findAll();
        for (CarBrand brand : brands) {
            carBrandRepository.delete(brand.getId());
        }
    }

    @Test
    void whenSaveModelThenRepositoryHasTheSameModel() {
        CarBrand carBrand = new CarBrand();
        carBrandRepository.save(carBrand);

        CarModel carModel = new CarModel();
        carModel.setName("XF");
        carModel.setCarBrand(carBrand);

        carModelRepository.save(carModel);
        CarModel result = carModelRepository.findById(carModel.getId()).get();
        assertThat(result).isEqualTo(carModel);
    }

    @Test
    void whenFindByNameThenGetBrand() {
        CarBrand carBrand = new CarBrand();
        carBrandRepository.save(carBrand);

        CarModel carModel = new CarModel();
        carModel.setName("XF");
        carModel.setCarBrand(carBrand);

        carModelRepository.save(carModel);
        assertThat(carModelRepository.findByName("XF")).isEqualTo(Optional.of(carModel));
    }

    @Test
    void whenFindByCarBrandNameThenGetListOfModels() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Jaguar");
        Optional<CarBrand> savedCarBrand = carBrandRepository.save(carBrand);

        CarModel carModel1 =  new CarModel();
        carModel1.setName("XF");
        carModel1.setCarBrand(savedCarBrand.get());

        CarModel carModel2 =  new CarModel();
        carModel2.setName("XE");
        carModel2.setCarBrand(savedCarBrand.get());

        Optional<CarModel> savedModel1 = carModelRepository.save(carModel1);
        Optional<CarModel> savedModel2 = carModelRepository.save(carModel2);

        assertThat(carModelRepository.findByCarBrandName("Jaguar")).isEqualTo(List.of(savedModel1.get(), savedModel2.get()));
    }

    @Test
    void whenDeleteThenEmptyOptional() {
        CarBrand carBrand = new CarBrand();
        carBrandRepository.save(carBrand);

        CarModel carModel = new CarModel();
        carModel.setName("XF");
        carModel.setCarBrand(carBrand);

        carModelRepository.save(carModel);
        int id = carModel.getId();
        carModelRepository.delete(id);
        assertThat(carModelRepository.findById(id)).isEqualTo(Optional.empty());
    }
}