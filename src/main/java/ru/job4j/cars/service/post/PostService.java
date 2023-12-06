package ru.job4j.cars.service.post;

import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.Post;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PostService {
    void save(Post post, Set<FileDto> images);
    List<Post> findAll();
    List<Post> findPostsByBrand(String carName);
    List<Post> findPostsByModel(String carModel);
    List<Post> findPostByPriceInterval(Integer priceFrom, Integer priceUntil);
    List<Post> findPostByMileageInterval(Long mileageFrom, Long mileageUntil);
    List<Post> findPostsOfUser(int userId);
    Optional<Post> findPostById(int id);
    boolean delete(int id);
    void update(Post post, Set<FileDto> images);
    boolean changePrice(int id, int price);
}