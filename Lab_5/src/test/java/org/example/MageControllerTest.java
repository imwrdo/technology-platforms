package org.example;

import org.example.Mage;
import org.example.MageController;
import org.example.MageRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNull;

public class MageControllerTest {


    @Test
    public void testDeleteNonExistingMage() {
        MageRepository repositoryMock = Mockito.mock(MageRepository.class);

        MageController controller = new MageController(repositoryMock);
        String result = controller.delete("Gendalf");

        assertThat(result).isEqualTo("not found");
    }

    @Test
    public void testFindNonExistingMage() {
        MageRepository repositoryMock = Mockito.mock(MageRepository.class);
        Mockito.when(repositoryMock.find("Gendalf")).thenReturn(Optional.empty());

        MageController controller = new MageController(repositoryMock);
        String result = controller.find("Gendalf");

        assertThat(result).isEqualTo("not found");
    }

    @Test
    public void testFindExistingMage() {
        MageRepository repositoryMock = Mockito.mock(MageRepository.class);
        Mage existingMage = new Mage();
        existingMage.setName("Mag_1");
        existingMage.setLevel(1);
        Mockito.when(repositoryMock.find("Mag_1")).thenReturn(Optional.of(existingMage));

        MageController controller = new MageController(repositoryMock);
        String result = controller.find("Mag_1");

        assertThat(result).isEqualTo("Name: Mag_1, Level: 1");
    }
    @Test
    public void testDeleteExistingMage(){
        MageRepository repositoryMock = Mockito.mock(MageRepository.class);
        Mage existingMage = new Mage();
        existingMage.setName("Mag_1");
        existingMage.setLevel(1);
        Mockito.when(repositoryMock.delete("Mag_1")).thenReturn(Optional.of(existingMage));

        MageController controller = new MageController(repositoryMock);
        String result = controller.delete("Mag_1");

        assertThat(result).isEqualTo("done");
    }
    @Test
    public void testSaveExistingMage() {
        MageRepository repositoryMock = Mockito.mock(MageRepository.class);
        Mage existingMage = new Mage();
        existingMage.setName("Mag_1");
        existingMage.setLevel(1);

        Mockito.when(repositoryMock.find("Mag_1")).thenReturn(Optional.of(existingMage));

        Mockito.when(repositoryMock.save(existingMage)).thenReturn("bad request");

        MageController controller = new MageController(repositoryMock);
        String result = controller.save("Mag_1", "1");
        String result2 = controller.save("Mag_1", "1");

        assertThat(result2).isEqualTo("bad request");
    }

    @Test
    public void testSaveNonExistingMage() {
        MageRepository repositoryMock = Mockito.mock(MageRepository.class);

        Mockito.when(repositoryMock.find("Mag_1")).thenReturn(Optional.empty());
        Mockito.when(repositoryMock.save(Mockito.any())).thenReturn("done");

        MageController controller = new MageController(repositoryMock);
        String result = controller.save("Mag_1", "1");

        assertThat(result).isEqualTo("done");
    }

}


