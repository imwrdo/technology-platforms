package org.example;

import org.example.Mage;
import org.example.MageRepository;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

public class MageRepositoryTest {

    @Test
    public void testFindNonExistingMage() {
        // Arrange
        MageRepository repository = new MageRepository();

        // Act
        Optional<Mage> result = repository.find("NieIstnieje");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    public void testFindExistingMage() {
        // Arrange
        MageRepository repository = new MageRepository();
        Mage mage = new Mage();
        mage.setName("Mag_1");
        mage.setLevel(1);
        repository.save(mage);

        // Act
        Optional<Mage> result = repository.find("Mag_1");

        // Assert
        assertThat(result).isPresent().contains(mage);
    }

    @Test
    public void testSaveNewMage() {
        // Arrange
        MageRepository repository = new MageRepository();
        Mage mage = new Mage();
        mage.setName("NowyMag");
        mage.setLevel(1);

        // Act
        String result = repository.save(mage);

        // Assert
        assertThat(result).isEqualTo("Zrobiono");
    }

    @Test
    void testDeleteExistingMage() {
        MageRepository repository = new MageRepository();
        Mage mage = new Mage();
        mage.setName("Gendalf");
        repository.save(mage);

        Optional<Mage> deletedMage = repository.delete("Gendalf");

        assertTrue(deletedMage.isPresent());
        assertEquals(mage, deletedMage.get());

        Optional<Mage> foundMage = repository.find("Gendalf");
        assertFalse(foundMage.isPresent());
    }

    @Test
    void testDeleteNonExistingMage() {
        MageRepository repository = new MageRepository();
        assertThrows(IllegalArgumentException.class, () -> repository.delete("Merlin"));
    }


    @Test
    void testSaveExistingMage() {
        MageRepository repository = new MageRepository();
        Mage mage = new Mage();
        mage.setName("Gendalf");
        repository.save(mage);

        assertThrows(IllegalArgumentException.class, () -> repository.save(mage));
    }
}
