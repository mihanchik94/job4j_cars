package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.User;
import ru.job4j.cars.repository.user.HbnUserRepository;
import ru.job4j.cars.repository.user.UserRepository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

class HbnUserRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static UserRepository userRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        userRepository = new HbnUserRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
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
    void whenCreateUserThenRepositoryHasTheSameUser() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        userRepository.create(user);
        User result = userRepository.findById(user.getId()).get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void whenUpdateThenGetUpdatedUser() {
        User user1 = new User();
        user1.setLogin("test");
        user1.setPassword("test");
        userRepository.create(user1);

        User user2 = new User();
        user2.setId(user1.getId());
        user2.setLogin(user1.getLogin());
        user2.setPassword("test1");
        userRepository.update(user2);

        String result = userRepository.findById(user1.getId()).get().getPassword();
        assertThat(result).isEqualTo("test1");
    }

    @Test
    void whenDeleteThenEmptyOptional() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        userRepository.create(user);
        userRepository.delete(user.getId());
        assertThat(userRepository.findById(user.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void whenFindAllOrderByIdThenGetOrderingList() {
        User user1 = new User();
        user1.setLogin("test1");
        user1.setPassword("test");
        User user2 = new User();
        user2.setLogin("test2");
        user2.setPassword("test");
        User user3 = new User();
        user3.setLogin("test3");
        user3.setPassword("test");

        userRepository.create(user1);
        userRepository.create(user2);
        userRepository.create(user3);

        assertThat(userRepository.findAllOrderById()).isEqualTo(List.of(user1, user2, user3));
    }

    @Test
    void whenFindByLikeLoginThenGetCollectionsWithKeyWord() {
        User user1 = new User();
        user1.setLogin("test1");
        user1.setPassword("test");
        User user2 = new User();
        user2.setLogin("test2");
        user2.setPassword("test");
        userRepository.create(user1);
        userRepository.create(user2);

        assertThat(userRepository.findByLikeLogin("test")).isEqualTo(List.of(user1, user2));
    }

    @Test
    void whenFindByLoginThenGetUser() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test");
        userRepository.create(user);
        User result = userRepository.findByLogin("test").get();
        assertThat(result).isEqualTo(user);
    }

    @Test
    void whenFindByLoginAndPasswordThenGetUser() {
        User user = new User();
        user.setLogin("test");
        user.setPassword("test1");
        userRepository.create(user);
        User result = userRepository.findByLoginAndPassword("test", "test1").get();
        assertThat(result).isEqualTo(user);
    }
}