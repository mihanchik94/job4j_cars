package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Engine;
import ru.job4j.cars.repository.engine.EngineRepository;
import ru.job4j.cars.repository.engine.HbnEngineRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnEngineRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static EngineRepository engineRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        engineRepository = new HbnEngineRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<Engine> engines = engineRepository.findAll();
        for (Engine engine : engines) {
            engineRepository.delete(engine.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    void whenFindAllThenGetList() {
        Engine engine1 = new Engine();
        engine1.setSize("2.4");
        Engine engine2 = new Engine();
        engine2.setSize("3.6");
        engineRepository.save(engine1);
        engineRepository.save(engine2);

        assertThat(engineRepository.findAll()).isEqualTo(List.of(engine1, engine2));
    }

    @Test
    void whenSaveEngineThenRepositoryHasTheSameEngine() {
        Engine engine = new Engine();
        engine.setSize("2.4");
        engineRepository.save(engine);
        Engine result = engineRepository.findById(engine.getId()).get();
        assertThat(result).isEqualTo(engine);
    }

    @Test
    void whenFindByNameThenGetEngine() {
        Engine engine = new Engine();
        engine.setSize("2.4");
        engineRepository.save(engine);
        assertThat(engineRepository.findByName("2.4")).isEqualTo(List.of(engine));
    }

    @Test
    void whenDeleteThenEmptyOptional() {
        Engine engine = new Engine();
        engine.setSize("2.4");
        engineRepository.save(engine);
        int id = engine.getId();
        engineRepository.delete(id);
        assertThat(engineRepository.findById(id)).isEqualTo(Optional.empty());
    }
}