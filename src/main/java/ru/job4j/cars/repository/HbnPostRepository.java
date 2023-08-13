package ru.job4j.cars.repository;

import lombok.AllArgsConstructor;
import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Repository;
import ru.job4j.cars.model.Post;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@ThreadSafe
@Repository
@AllArgsConstructor
public class HbnPostRepository implements PostRepository {
    private final CrudRepository crudRepository;

    @Override
    public List<Post> findPostsOfToday() {
        return crudRepository.query("from Post p join fetch p.photos where p.created >= :fDate", Post.class,
                Map.of("fDate", LocalDateTime.now().minusDays(1)));
    }

    @Override
    public List<Post> findPostsOnlyWithPicture() {
        return crudRepository.query("from Post p join fetch p.photos where p.photos is not empty", Post.class);
    }

    @Override
    public List<Post> findPostsByName(String carName) {
        return crudRepository.query("from Post p join fetch p.photos where p.car.name = :fCarName", Post.class,
                Map.of("fCarName", carName));
    }
}
