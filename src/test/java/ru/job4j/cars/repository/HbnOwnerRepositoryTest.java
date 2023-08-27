package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Owner;
import ru.job4j.cars.model.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnOwnerRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static OwnerRepository ownerRepository;
    private static UserRepository userRepository;


    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        userRepository = new UserRepository(new CrudRepository(sf));
        ownerRepository = new HbnOwnerRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<Owner> owners = ownerRepository.findAll();
        for (Owner owner : owners) {
            ownerRepository.delete(owner.getId());
        }

        List<User> users = userRepository.findAllOrderById();
        for (User user : users) {
            userRepository.delete(user.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    void whenFindAllThenGetList() {
        Owner owner1 = new Owner();
        owner1.setName("test1");

        User user1 = new User();
        User user2 = new User();


        user1.setLogin("test1");
        user1.setPassword("test");

        user2.setLogin("test2");
        user2.setPassword("test");

        userRepository.create(user1);
        userRepository.create(user2);

        owner1.setUser(user1);
        Owner owner2 = new Owner();
        owner2.setName("test2");
        owner2.setUser(user2);
        ownerRepository.save(owner1);
        ownerRepository.save(owner2);

        assertThat(ownerRepository.findAll()).isEqualTo(List.of(owner1, owner2));
    }

    @Test
    void whenSaveOwnerThenRepositoryHasTheSameOwner() {
        Owner owner = new Owner();
        owner.setName("test");

        User user1 = new User();
        User user2 = new User();

        user1.setLogin("test1");
        user1.setPassword("test");

        user2.setLogin("test2");
        user2.setPassword("test");

        userRepository.create(user1);
        userRepository.create(user2);

        owner.setUser(user1);
        ownerRepository.save(owner);
        Owner result = ownerRepository.findById(owner.getId()).get();
        assertThat(result).isEqualTo(owner);
    }

    @Test
    void whenFindByNameThenGetOwner() {
        Owner owner = new Owner();
        owner.setName("test");

        User user1 = new User();
        User user2 = new User();

        user1.setLogin("test1");
        user1.setPassword("test");

        user2.setLogin("test2");
        user2.setPassword("test");

        userRepository.create(user1);
        userRepository.create(user2);

        owner.setUser(user1);
        ownerRepository.save(owner);
        assertThat(ownerRepository.findByName("test")).isEqualTo(List.of(owner));
    }

    @Test
    void whenDeleteThenEmptyOptional() {
        Owner owner = new Owner();
        owner.setName("test");

        User user1 = new User();

        user1.setLogin("test1");
        user1.setPassword("test");

        userRepository.create(user1);

        owner.setUser(user1);
        ownerRepository.save(owner);
        int id = owner.getId();
        ownerRepository.delete(id);
        assertThat(ownerRepository.findById(id)).isEqualTo(Optional.empty());
    }
}