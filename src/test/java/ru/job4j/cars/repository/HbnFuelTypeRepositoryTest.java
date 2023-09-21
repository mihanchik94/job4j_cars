package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.FuelType;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnFuelTypeRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static FuelTypeRepository fuelTypeRepository;

    @BeforeAll
    public static void initRepositories(){
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        fuelTypeRepository = new HbnFuelTypeRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<FuelType> fuelTypes = fuelTypeRepository.findAll();
        for (FuelType fuelType : fuelTypes) {
            fuelTypeRepository.delete(fuelType.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    void whenSaveFuelTypeThenRepositoryHasTheSameFuelType() {
        FuelType fuelType1 = new FuelType();
        fuelType1.setName("Бензин");
        fuelTypeRepository.save(fuelType1);
        FuelType result = fuelTypeRepository.findById(fuelType1.getId()).get();
        assertThat(result).isEqualTo(fuelType1);
    }

    @Test
    void whenDeleteThenGetEmptyOptional() {
        FuelType fuelType1 = new FuelType();
        fuelType1.setName("Бензин");
        fuelTypeRepository.save(fuelType1);
        fuelTypeRepository.delete(fuelType1.getId());
        assertThat(fuelTypeRepository.findById(fuelType1.getId())).isEqualTo(Optional.empty());
    }


    @Test
    void whenFindByNameThenGetFuelType() {
        FuelType fuelType1 = new FuelType();
        fuelType1.setName("Бензин");
        fuelTypeRepository.save(fuelType1);
        assertThat(fuelTypeRepository.findByName("Бензин")).isEqualTo(Optional.of(fuelType1));
    }
}