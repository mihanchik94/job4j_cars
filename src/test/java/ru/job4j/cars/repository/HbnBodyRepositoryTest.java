package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Body;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnBodyRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static BodyRepository bodyRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        bodyRepository = new HbnBodyRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<Body> bodies = bodyRepository.findAll();
        for (Body body : bodies) {
            bodyRepository.delete(body.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }


    @Test
    void whenSaveBodyThenRepositoryHasTheSameBody() {
        Body body = new Body();
        body.setName("Минивэн");
        bodyRepository.save(body);
        Body result = bodyRepository.findById(body.getId()).get();
        assertThat(result).isEqualTo(body);
    }

    @Test
    void whenFindByNameThenGetEngine() {
        Body body = new Body();
        body.setName("Минивэн");
        bodyRepository.save(body);
        assertThat(bodyRepository.findByName("Минивэн")).isEqualTo(Optional.of(body));
    }

    @Test
    void whenDeleteThenEmptyOptional() {
        Body body = new Body();
        body.setName("Минивэн");
        bodyRepository.save(body);
        int id = body.getId();
        bodyRepository.delete(id);
        assertThat(bodyRepository.findById(id)).isEqualTo(Optional.empty());
    }
}