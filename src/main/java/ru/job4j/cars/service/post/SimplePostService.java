package ru.job4j.cars.service.post;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.cars.dto.FileDto;
import ru.job4j.cars.model.File;
import ru.job4j.cars.model.Post;
import ru.job4j.cars.repository.car.CarRepository;
import ru.job4j.cars.repository.post.PostRepository;
import ru.job4j.cars.service.file.FileService;

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
    public List<Post> findPostsByBrand(String carBrand) {
        return postRepository.findPostsByBrand(carBrand);
    }

    @Override
    public List<Post> findPostsByModel(String carModel) {
        return postRepository.findPostsByModel(carModel);
    }

    @Override
    public List<Post> findPostByPriceInterval(Integer priceFrom, Integer priceUntil) {
        if (priceFrom == null) {
            priceFrom = 0;
        }
        if (priceUntil == null) {
            priceUntil = Integer.MAX_VALUE;
        }
        return postRepository.findPostByPriceInterval(priceFrom, priceUntil);
    }

    @Override
    public List<Post> findPostByMileageInterval(Long mileageFrom, Long mileageUntil) {
        if (mileageFrom == null) {
            mileageFrom = 0L;
        }
        if (mileageUntil == null) {
            mileageUntil = Long.MAX_VALUE;
        }
        return postRepository.findPostByMileageInterval(mileageFrom, mileageUntil);
    }

    @Override
    public List<Post> findPostsOfUser(int userId) {
        return postRepository.findPostsOfUser(userId);
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

    @Override
    public boolean changePrice(int id, int price) {
        return postRepository.changePrice(id, price);
    }
}