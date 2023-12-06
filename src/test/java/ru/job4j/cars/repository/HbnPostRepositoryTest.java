package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.*;
import ru.job4j.cars.repository.car.CarRepository;
import ru.job4j.cars.repository.car.HbnCarRepository;
import ru.job4j.cars.repository.carBrand.CarBrandRepository;
import ru.job4j.cars.repository.carBrand.HbnCarBrandRepository;
import ru.job4j.cars.repository.carModel.CarModelRepository;
import ru.job4j.cars.repository.carModel.HbnCarModelRepository;
import ru.job4j.cars.repository.file.FileRepository;
import ru.job4j.cars.repository.file.HbnFileRepository;
import ru.job4j.cars.repository.post.HbnPostRepository;
import ru.job4j.cars.repository.post.PostRepository;
import ru.job4j.cars.repository.user.HbnUserRepository;
import ru.job4j.cars.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;

class HbnPostRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static PostRepository postRepository;
    private static UserRepository userRepository;
    private static FileRepository fileRepository;
    private static CarRepository carRepository;
    private static CarBrandRepository carBrandRepository;
    private static CarModelRepository carModelRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        CrudRepository crudRepository = new CrudRepository(sf);
        postRepository = new HbnPostRepository(crudRepository);
        userRepository = new HbnUserRepository(crudRepository);
        fileRepository = new HbnFileRepository(crudRepository);
        carRepository = new HbnCarRepository(crudRepository);
        carBrandRepository = new HbnCarBrandRepository(crudRepository);
        carModelRepository = new HbnCarModelRepository(crudRepository);
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

        List<CarModel> models = carModelRepository.findAll();
        for (CarModel model : models) {
            carModelRepository.delete(model.getId());
        }

        List<Car> cars = carRepository.findAll();
        for (Car car : cars) {
            carRepository.delete(car.getId());
        }

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
    void whenFindAllPostsThenGetList() {
        Post post1 = new Post();
        Post post2 = new Post();
        postRepository.save(post1);
        postRepository.save(post2);
        List<Post> result = postRepository.findAll();
        assertThat(result).isEqualTo(List.of(post1, post2));
    }

    @Test
    void whenFindPostByIdThenGetOptionalPost() {
        Post post1 = new Post();
        postRepository.save(post1);
        assertThat(postRepository.findPostById(post1.getId())).isEqualTo(Optional.of(post1));
    }

        @Test
        void whenFindPostsByBrandThenGetList() {
            Post post1 = new Post();
            Post post2 = new Post();
            Car car1 = new Car();
            Car car2 = new Car();
            CarBrand carBrand1 = new CarBrand();
            CarBrand carBrand2 = new CarBrand();

            carBrand1.setName("Audi");
            carBrand2.setName("BMW");

            carBrandRepository.save(carBrand1);
            carBrandRepository.save(carBrand2);

            car1.setCarBrand(carBrand1);
            car2.setCarBrand(carBrand2);

            carRepository.save(car1);
            carRepository.save(car2);

            post1.setCar(car1);
            post2.setCar(car2);

            postRepository.save(post1);
            postRepository.save(post2);
            assertThat(postRepository.findPostsByBrand("Audi")).isEqualTo(List.of(post1));
    }

    @Test
    void whenUpdateThenGetUpdatedObject() {
        Post post = new Post();
        post.setDescription("test");
        postRepository.save(post);
        Post updatedPost = new Post();
        updatedPost.setId(post.getId());
        updatedPost.setDescription("test1");
        postRepository.update(updatedPost);
        assertThat(postRepository.findPostById(post.getId())).isEqualTo(Optional.of(updatedPost));
    }

    @Test
    void whenFindPostsOfUserThenGetList() {
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

        post1.setUserId(user1.getId());
        post2.setUserId(user2.getId());

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> result = postRepository.findPostsOfUser(user1.getId());
        assertThat(result).isEqualTo(List.of(post1));
    }

    @Test
    void whenDontFindPostsOfUserThenGetEmptyList() {
        User user1 = new User();
        user1.setLogin("test1");
        user1.setPassword("test");
        userRepository.create(user1);
        List<Post> result = postRepository.findPostsOfUser(user1.getId());
        assertThat(result).isEqualTo(emptyList());
    }

    @Test
    void whenChangePriceThenGetPostWithNewPrice() {
        Post post = new Post();
        post.setPrice(1000);
        postRepository.save(post);
        boolean isChanged = postRepository.changePrice(post.getId(), 10);
        Post changedPost = postRepository.findPostById(post.getId()).get();
        assertThat(isChanged).isTrue();
        assertThat(changedPost.getPrice()).isEqualTo(10);
    }

    @Test
    void whenSearchingPostByPriceIntervalSuccessfullyThenGetList() {
        int minPrice = 200000;
        int maxPrice = 300000;
        Post post = new Post();
        post.setPrice(220000);
        postRepository.save(post);
        assertThat(postRepository.findPostByPriceInterval(minPrice, maxPrice)).isEqualTo(List.of(post));
    }

    @Test
    void whenSearchingPostByPriceIntervalFailedThenGetEmptyList() {
        int minPrice = 200000;
        int maxPrice = 300000;
        Post post = new Post();
        post.setPrice(190000);
        postRepository.save(post);
        assertThat(postRepository.findPostByPriceInterval(minPrice, maxPrice)).isEqualTo(emptyList());
    }

    @Test
    void whenSearchingPostByMileageIntervalThenGetList() {
        long minMileage = 10000;
        long maxMileage = 200000;
        Post post1 = new Post();
        Post post2 = new Post();
        Car car1 = new Car();
        Car car2 = new Car();

        car1.setMileage(300000);
        car2.setMileage(15000);

        carRepository.save(car1);
        carRepository.save(car2);

        post1.setCar(car1);
        post2.setCar(car2);

        postRepository.save(post1);
        postRepository.save(post2);

        assertThat(postRepository.findPostByMileageInterval(minMileage, maxMileage)).isEqualTo(List.of(post2));
    }
}