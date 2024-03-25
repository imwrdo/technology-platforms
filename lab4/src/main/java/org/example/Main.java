package org.example;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class Main {

    private static EntityManagerFactory entities = Persistence.createEntityManagerFactory("jpa-hibernate-example");

    public static void main(String[] args) {
        EntityManager entityManager = entities.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();

        //Tworzenie towerow i magow
        Tower t1 = new Tower("Wieża1",20);
        Tower t2 = new Tower("Wieża2",20);
        Mage m1 = new Mage("Biały Nosacz", 15,t1);
        Mage m2 = new Mage("Czarny Nosacz", 25,t1);
        Mage m3 = new Mage("Brązowy Nosacz", 35,t1);

        entityManager.persist(t1);
        entityManager.persist(t2);
        entityManager.persist(m1);
        entityManager.persist(m2);
        entityManager.persist(m3);

        entityTransaction.commit();

        entityTransaction.begin();

        Tower towerToDelete = entityManager.find(Tower.class, "Wieża2");

        if (towerToDelete != null) {
            entityManager.remove(towerToDelete);
            System.out.println("Usunięto wieżę z bazy danych: " + towerToDelete.getName());
        } else {
            System.out.println("Wieża o podanym identyfikatorze nie istnieje.");
        }

        entityTransaction.commit();


        List<Mage> mages = entityManager.createQuery("SELECT m FROM Mage m", Mage.class).getResultList();
        for (Mage mage : mages) {
            System.out.println("Mage: " + mage.getName() + ", Level: " + mage.getLevel());
        }


        entityManager.close();
        entities.close();
    }
}