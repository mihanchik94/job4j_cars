package ru.job4j.cars.repository;

import ru.job4j.cars.model.Post;

import java.util.List;

public interface PostRepository {
    List<Post> findPostsOfToday();
    List<Post> findPostsOnlyWithPicture();
    List<Post> findPostsByName(String carName);
}