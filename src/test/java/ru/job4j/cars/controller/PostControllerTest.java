package ru.job4j.cars.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cars.model.*;
import ru.job4j.cars.service.carBrand.CarBrandService;
import ru.job4j.cars.service.carModel.CarModelService;
import ru.job4j.cars.service.post.PostService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostControllerTest {
    private CarBrandService carBrandService;
    private CarModelService carModelService;
    private PostService postService;
    private PostController postController;
    private HttpServletRequest request;
    private HttpSession session;

    @BeforeEach
    public void initServices() {
        carBrandService = mock(CarBrandService.class);
        carModelService = mock(CarModelService.class);
        postService = mock(PostService.class);
        postController = new PostController(postService);
        postController.setCarBrandService(carBrandService);
        postController.setCarModelService(carModelService);
        request = Mockito.mock(HttpServletRequest.class);
        session = Mockito.mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void whenRequestAllPostsPageThenGetAllPostsPage() {
        CarBrand carBrand = new CarBrand();
        CarModel carModel = new CarModel();

        carModel.setCarBrand(carBrand);

        Car car1 = new Car();
        Car car2 = new Car();

        car1.setCarBrand(carBrand);
        car1.setCarModel(carModel);
        car2.setCarBrand(carBrand);
        car2.setCarModel(carModel);

        Post post1 = new Post(1, "desc1", 1, LocalDateTime.now(), 1, car1, new HashSet<>());
        Post post2 = new Post(2, "desc2", 2, LocalDateTime.now(), 2, car2, new HashSet<>());
        List<Post> expectedPosts = List.of(post1, post2);

        when(carBrandService.findAll()).thenReturn(List.of(carBrand));
        when(carModelService.findAll()).thenReturn(List.of(carModel));
        when(postService.findAll()).thenReturn(expectedPosts);

        ConcurrentModel model = new ConcurrentModel();
        String view = postController.getAll(model, null, null, null, null, null, null);
        Object actualPosts = model.getAttribute("posts");

        assertThat(view).isEqualTo("posts/all");
        assertThat(actualPosts).isEqualTo(expectedPosts);
    }

    @Test
    public void whenRequestOnePostPageAndErrorThenGetError404() {
        Post findingPost = new Post(1, "desc1", 1, LocalDateTime.now(), 1, new Car(), new HashSet<>());
        String expectedMessage = "Объявление с указанным id не найдено";

        when(postService.findPostById(anyInt())).thenReturn(Optional.empty());

        ConcurrentModel model = new ConcurrentModel();
        String view = postController.getById(findingPost.getId(), model);
        Object actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("redirect:/errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenRequestOnePostPageAndSuccessThenGetPageWithPost() {
        Car car = new Car(1, new CarBrand(), new CarModel(), 2022, 5, new Engine(1, "3.0"), new Body(1, "Внедорожник"),
                new GearBox(1, "Автомат"), new FuelType(1, "Дизель"), new DriveType(1, "Полный"),
                new Color(1, "Голубой"));
        Post findingPost = new Post(1, "desc1", 1, LocalDateTime.now(), 1, car, new HashSet<>());

        when(postService.findPostById(findingPost.getId())).thenReturn(Optional.of(findingPost));

        ConcurrentModel model = new ConcurrentModel();
        String view = postController.getById(findingPost.getId(), model);
        Object expectedPost = model.getAttribute("post");
        Object expectedCar = model.getAttribute("car");
        Object expectedBody = model.getAttribute("body");
        Object expectedGearBox = model.getAttribute("gearBox");
        Object expectedDriveType = model.getAttribute("driveType");
        Object expectedFuelType = model.getAttribute("fuelType");
        Object expectedEngine = model.getAttribute("engine");
        Object expectedColor = model.getAttribute("color");

        assertThat(view).isEqualTo("posts/one");
        assertThat(expectedPost).isEqualTo(findingPost);
        assertThat(expectedCar).isEqualTo(findingPost.getCar());
        assertThat(expectedBody).isEqualTo(findingPost.getCar().getBody());
        assertThat(expectedGearBox).isEqualTo(findingPost.getCar().getGearBox());
        assertThat(expectedDriveType).isEqualTo(findingPost.getCar().getDriveType());
        assertThat(expectedFuelType).isEqualTo(findingPost.getCar().getFuelType());
        assertThat(expectedEngine).isEqualTo(findingPost.getCar().getEngine());
        assertThat(expectedColor).isEqualTo(findingPost.getCar().getColor());
    }

    @Test
    public void whenRequestDeletePostAndSuccessThenGetAllPostsPage() {
        Post deletingPost = new Post(1, "desc1", 1, LocalDateTime.now(), 1, new Car(), new HashSet<>());

        when(postService.delete(anyInt())).thenReturn(true);

        ConcurrentModel model = new ConcurrentModel();
        String view = postController.deletePost(deletingPost.getId(), model);

        assertThat(view).isEqualTo("redirect:/posts/all");
    }

    @Test
    public void whenRequestDeletePostAndFailThenGetGetError404() {
        Post deletingPost = new Post(1, "desc1", 1, LocalDateTime.now(), 1, new Car(), new HashSet<>());
        String expectedMessage = "Объявление с указанным id не найдено";

        when(postService.delete(anyInt())).thenReturn(false);

        ConcurrentModel model = new ConcurrentModel();
        String view = postController.getById(deletingPost.getId(), model);
        Object actualMessage = model.getAttribute("message");

        assertThat(view).isEqualTo("redirect:/errors/404");
        assertThat(actualMessage).isEqualTo(expectedMessage);
    }

    @Test
    public void whenRequestPostsOfTheUserPageThenGetPageWithPostsOfTheUser() {
        User user = new User();
        Post post1 = new Post(1, "desc1", 1, LocalDateTime.now(), user.getId(), new Car(), new HashSet<>());

        when(postService.findPostsOfUser(anyInt())).thenReturn(List.of(post1));
        when(session.getAttribute("user")).thenReturn(user);

        ConcurrentModel model = new ConcurrentModel();
        String view = postController.getPostsOfUserPage(model, request);
        Object actualList = model.getAttribute("posts");

        assertThat(view).isEqualTo("posts/userPosts");
        assertThat(actualList).isEqualTo(List.of(post1));
    }
}