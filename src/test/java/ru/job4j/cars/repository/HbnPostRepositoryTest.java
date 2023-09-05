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
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

class HbnPostRepositoryTest {
    private final LocalDateTime TIME_1 = LocalDateTime.now().plusDays(1).withNano(0);
    private final LocalDateTime TIME_2 = LocalDateTime.now().minusDays(3).withNano(0);
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static PostRepository postRepository;
    private static UserRepository userRepository;
    private static FileRepository fileRepository;
    private static CarRepository carRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        postRepository = new HbnPostRepository(crudRepository);
        userRepository = new UserRepository(crudRepository);
        fileRepository = new HbnFileRepository(crudRepository);
        carRepository = new HbnCarRepository(crudRepository);
    }

    @AfterEach
    public void deleteModels() {
        List<User> users = userRepository.findAllOrderById();
        for (User user : users) {
            userRepository.delete(user.getId());
        }

        List<File> files = fileRepository.findAll();
        for (File file : files) {
            fileRepository.deleteById(file.getId());
        }

        List<Post> posts = postRepository.findAll();
        for (Post post : posts) {
            postRepository.delete(post.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }


    @Test
    void whenFindAllPostsThenGetList() {
        Post post1 = new Post();
        Post post2 = new Post();

        User user1 = new User();
        User user2 = new User();

        user1.setLogin("test1");
        user1.setPassword("test");

        user2.setLogin("test2");
        user2.setPassword("test");

        userRepository.create(user1);
        userRepository.create(user2);

        post1.setDescription("test1");
        post1.setUserId(user1.getId());
        post1.setCreated(TIME_1);

        post2.setDescription("test2");
        post2.setUserId(user2.getId());
        post2.setCreated(TIME_2);

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> result = postRepository.findAll();

        assertThat(result).isEqualTo(List.of(post1, post2));
    }

    @Test
    void whenFindPostsOfTodayThenGetList() {
        Post post1 = new Post();
        Post post2 = new Post();

        User user1 = new User();
        User user2 = new User();

        user1.setLogin("test1");
        user1.setPassword("test");

        user2.setLogin("test2");
        user2.setPassword("test");

        userRepository.create(user1);
        userRepository.create(user2);

        post1.setDescription("test1");
        post1.setUserId(user1.getId());
        post1.setCreated(TIME_1);

        post2.setDescription("test2");
        post2.setUserId(user2.getId());
        post2.setCreated(TIME_2);

        postRepository.save(post1);
        postRepository.save(post2);

        assertThat(postRepository.findPostsOfToday()).isEqualTo(List.of(post1));
    }

    @Test
    void whenFindPostsOnlyWithPictureThenGetList() {
        User user1 = new User();
        User user2 = new User();

        user1.setLogin("test1");
        user1.setPassword("test");
        user2.setLogin("test2");
        user2.setPassword("test");

        userRepository.create(user1);
        userRepository.create(user2);

        File file = new File();
        file.setName("file");
        file.setPath("/");


        Post post1 = new Post();
        Post post2 = new Post();


        post1.setDescription("test1");
        post1.setUserId(user1.getId());
        post1.setCreated(TIME_1);
        post1.setPhotos(Set.of(file));

        post2.setDescription("test2");
        post2.setUserId(user2.getId());
        post2.setCreated(TIME_2);

        postRepository.save(post1);
        postRepository.save(post2);

        assertThat(postRepository.findPostsOnlyWithPicture()).isEqualTo(List.of(post1));
    }

        @Test
        void whenFindPostsByBrandThenGetList() {
            Post post1 = new Post();
            Post post2 = new Post();

            User user1 = new User();
            User user2 = new User();

            Car car1 = new Car();
            Car car2 = new Car();

            user1.setLogin("test1");
            user1.setPassword("test");

            user2.setLogin("test2");
            user2.setPassword("test");

            userRepository.create(user1);
            userRepository.create(user2);

           car1.setName("Audi");
           car2.setName("BMW");

           carRepository.save(car1);
           carRepository.save(car2);

           post1.setDescription("test1");
           post1.setUserId(user1.getId());
           post1.setCreated(TIME_1);
           post1.setCar(car1);

           post2.setDescription("test2");
           post2.setUserId(user2.getId());
           post2.setCreated(TIME_2);
           post2.setCar(car2);

           postRepository.save(post1);
           postRepository.save(post2);

           assertThat(postRepository.findPostsByName("Audi")).isEqualTo(List.of(post1));
    }
}