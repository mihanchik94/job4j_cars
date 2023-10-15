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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnCarRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static CarRepository carRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        carRepository = new HbnCarRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<Car> cars = carRepository.findAll();
        for (Car car : cars) {
            carRepository.delete(car.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    void whenFindAllThenGetList() {
        Car car1 = new Car();
        car1.setBrand("Voyah");
        Car car2 = new Car();
        car2.setBrand("Jaguar");
        carRepository.save(car1);
        carRepository.save(car2);

        assertThat(carRepository.findAll()).isEqualTo(List.of(car1, car2));
    }

    @Test
    void whenFindByNameThenGetCar() {
        Car car = new Car();
        car.setBrand("Voyah");
        carRepository.save(car);
        assertThat(carRepository.findByName("Voyah")).isEqualTo(List.of(car));
    }

    @Test
    void whenSaveCarThenRepositoryHasTheSameCar() {
        Car car = new Car();
        car.setBrand("Voyah");
        carRepository.save(car);
        Car result = carRepository.findById(car.getId()).get();
        assertThat(result).isEqualTo(car);
    }

    @Test
    void whenDeleteThenEmptyOptional() {
        Car car = new Car();
        car.setBrand("Voyah");
        carRepository.save(car);
        int id = car.getId();
        carRepository.delete(id);
        assertThat(carRepository.findById(id)).isEqualTo(Optional.empty());
    }

    @Test
    void whenUpdateThenGetUpdatedObject() {
        Car car = new Car();
        car.setBrand("Voyah");
        carRepository.save(car);
        Car updatedCar = new Car();
        updatedCar.setId(car.getId());
        updatedCar.setBrand("Jaguar");
        carRepository.update(updatedCar);
        assertThat(carRepository.findById(car.getId())).isEqualTo(Optional.of(updatedCar));
    }
}