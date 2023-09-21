package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.Color;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnColorRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static ColorRepository colorRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        colorRepository = new HbnColorRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<Color> colors = colorRepository.findAll();
        for (Color color : colors) {
            colorRepository.delete(color.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    @Test
    void whenSaveColorThenRepositoryHasTheSaneColor() {
        Color color = new Color();
        color.setName("Красный");
        colorRepository.save(color);
        Color result = colorRepository.findById(color.getId()).get();
        assertThat(result).isEqualTo(color);
    }

    @Test
    void whenDeleteThenGetEmptyOptional() {
        Color color = new Color();
        color.setName("Красный");
        colorRepository.save(color);
        colorRepository.delete(color.getId());
        assertThat(colorRepository.findById(color.getId())).isEqualTo(Optional.empty());
    }


    @Test
    void whenFindByNameThenGetColor() {
        Color color = new Color();
        color.setName("Красный");
        colorRepository.save(color);
        assertThat(colorRepository.findByName("Красный")).isEqualTo(Optional.of(color));
    }
}