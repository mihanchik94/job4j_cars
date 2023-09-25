package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Car;
import ru.job4j.cars.model.DriveType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnDriveTypeRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static DriveTypeRepository driveTypeRepository;


    @BeforeAll
    public static void initRepositories() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        driveTypeRepository = new HbnDriveTypeRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<DriveType> driveTypes = driveTypeRepository.findAll();
        for (DriveType driveType : driveTypes) {
            driveTypeRepository.delete(driveType.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }


    @Test
    void whenFindAllThenGetList() {
        DriveType driveType = new DriveType();
        driveType.setName("Полный");
        driveTypeRepository.save(driveType);
        assertThat(driveTypeRepository.findAll()).isEqualTo(List.of(driveType));
    }

    @Test
    void whenFindByNameThenGetDriveType() {
        DriveType driveType = new DriveType();
        driveType.setName("Полный");
        driveTypeRepository.save(driveType);
        assertThat(driveTypeRepository.findByName("Полный")).isEqualTo(Optional.of(driveType));
    }

    @Test
    void whenSaveDriveTypeThenRepositoryHasTheSameDriveType() {
        DriveType driveType = new DriveType();
        driveType.setName("Полный");
        driveTypeRepository.save(driveType);
        DriveType result = driveTypeRepository.findById(driveType.getId()).get();
        assertThat(result).isEqualTo(driveType);
    }

    @Test
    void whenDeleteThenEmptyOptional() {
        DriveType driveType = new DriveType();
        driveType.setName("Полный");
        driveTypeRepository.save(driveType);
        driveTypeRepository.delete(driveType.getId());
        assertThat(driveTypeRepository.findById(driveType.getId())).isEqualTo(Optional.empty());
    }
}