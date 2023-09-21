package ru.job4j.cars.repository;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cars.model.GearBox;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class HbnGearBoxRepositoryTest {
    private static StandardServiceRegistry registry;
    private static SessionFactory sf;
    private static GearBoxRepository gearBoxRepository;

    @BeforeAll
    public static void initRepository() {
        registry = new StandardServiceRegistryBuilder().configure().build();
        sf = new MetadataSources().buildMetadata(registry).buildSessionFactory();
        gearBoxRepository = new HbnGearBoxRepository(new CrudRepository(sf));
    }

    @AfterEach
    public void deleteModels() {
        List<GearBox> gearBoxes = gearBoxRepository.findAll();
        for (GearBox gearBox : gearBoxes) {
            gearBoxRepository.delete(gearBox.getId());
        }
    }

    @AfterAll
    public static void closeConnection() {
        StandardServiceRegistryBuilder.destroy(registry);
    }


    @Test
    void whenSaveGearBoxThenRepositoryHasTheSameGearBox() {
        GearBox gearBox1 = new GearBox();
        gearBox1.setName("Вариатор");
        gearBoxRepository.save(gearBox1);
        GearBox result = gearBoxRepository.findById(gearBox1.getId()).get();
        assertThat(result).isEqualTo(gearBox1);
    }

    @Test
    void whenDeleteThenGetEmptyOptional() {
        GearBox gearBox1 = new GearBox();
        gearBox1.setName("Вариатор");
        gearBoxRepository.save(gearBox1);
        gearBoxRepository.delete(gearBox1.getId());
        assertThat(gearBoxRepository.findById(gearBox1.getId())).isEqualTo(Optional.empty());
    }

    @Test
    void whenFindByNameThenGetGearBox() {
        GearBox gearBox1 = new GearBox();
        gearBox1.setName("Вариатор");
        gearBoxRepository.save(gearBox1);
        assertThat(gearBoxRepository.findByName("Вариатор")).isEqualTo(Optional.of(gearBox1));
    }
}