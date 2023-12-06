package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.CarBrand;
import ru.job4j.cars.repository.carBrand.CarBrandRepository;
import ru.job4j.cars.repository.carBrand.HbnCarBrandRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnCarBrandRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static CarBrandRepository carBrandRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        carBrandRepository = new HbnCarBrandRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<CarBrand> brands = carBrandRepository.findAll();
        for (CarBrand brand : brands) {
            carBrandRepository.delete(brand.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    void whenSaveBrandThenRepositoryHasTheSameBrand() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Jaguar");
        carBrandRepository.save(carBrand);
        CarBrand result = carBrandRepository.findById(carBrand.getId()).get();
        assertThat(result).isEqualTo(carBrand);
    }

    @Test
    void whenFindByNameThenGetBrand() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Jaguar");
        carBrandRepository.save(carBrand);
        assertThat(carBrandRepository.findByName("Jaguar")).isEqualTo(Optional.of(carBrand));
    }

    @Test
    void whenDeleteThenEmptyOptional() {
        CarBrand carBrand = new CarBrand();
        carBrand.setName("Jaguar");
        carBrandRepository.save(carBrand);
        int id = carBrand.getId();
        carBrandRepository.delete(id);
        assertThat(carBrandRepository.findById(id)).isEqualTo(Optional.empty());
    }
}