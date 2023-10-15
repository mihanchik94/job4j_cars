package ru.job4j.cars.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.CarRepository;
import ru.job4j.cars.repository.PostRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimplePostService implements PostService {
    private final PostRepository postRepository;
    private final FileService fileService;
    private final CarRepository carRepository;

    @Override
    public void save(Post post, Set<FileDto> images) {
        Post savedPost = postRepository.save(post);
        saveNewFiles(savedPost, images);
    }

    private void saveNewFiles(Post post, Set<FileDto> images) {
        Set<File> postFiles = new HashSet<>();
        for (FileDto image : images) {
            File resultFile = fileService.save(image, post);
            postFiles.add(resultFile);
        }
        post.setPhotos(postFiles);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findPostsOfToday() {
        return postRepository.findPostsOfToday();
    }

    @Override
    public List<Post> findPostsOnlyWithPicture() {
        return postRepository.findPostsOnlyWithPicture();
    }

    @Override
    public List<Post> findPostsByName(String carName) {
        return postRepository.findPostsByName(carName);
    }

    @Override
    public Optional<Post> findPostById(int id) {
        return postRepository.findPostById(id);
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        Optional<Post> optionalPost = postRepository.findPostById(id);
        if (optionalPost.isEmpty()) {
            return result;
        } else {
            Set<File> postFiles = optionalPost.get().getPhotos();
            postFiles.forEach(file -> fileService.deleteById(file.getId()));
            result = postRepository.delete(id);
            carRepository.delete(optionalPost.get().getCar().getId());
        }
        return result;
    }

    @Override
    public void update(Post post, Set<FileDto> images) {
        if (!images.isEmpty()) {
            Set<File> postFiles = post.getPhotos();
            postFiles.forEach(file -> fileService.deleteById(file.getId()));
            saveNewFiles(post, images);
        }
        postRepository.update(post);
    }
}