package ru.job4j.cars.repository.post;

import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    List<Post> findAll();
    List<Post> findPostsByBrand(String carName);
    List<Post> findPostsByModel(String carModel);
    List<Post> findPostByPriceInterval(int priceFrom, int priceUntil);
    List<Post> findPostByMileageInterval(long mileageFrom, long mileageUntil);
    List<Post> findPostsOfUser(int userId);
    Optional<Post> findPostById(int id);
    boolean delete(int id);
    void update(Post post);
    boolean changePrice(int id, int price);
}