package org.example;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Scanner;
import java.lang.System;

public class Main {

    private static EntityManagerFactory entities = Persistence.createEntityManagerFactory("jpa-hibernate-example");

    public static void main(String[] args) {
        EntityManager entityManager = entities.createEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();

        entityTransaction.begin();


        Tower t1 = new Tower("Wieża_1",20);
        Tower t2 = new Tower("Wieża_2",20);
        Mage m1 = new Mage("Czarny Aleksander", 15,t1);
        Mage m2 = new Mage("Wielki Ziemniak", 25,t1);
        Mage m3 = new Mage("Wieczny Pawel", 35,t1);
        Mage m4 = new Mage("Nie wieczny Mikołaj", 37,t2);

        Tower[] towers_list = {t1, t2};
        Mage[] mages_list = {m1, m2, m3, m4};

        for (Tower tower : towers_list) {
            entityManager.persist(tower);
        }

        for (Mage mage : mages_list) {
            entityManager.persist(mage);
            mage.getTower().getMages().add(mage);
        }

        for (Tower tower : towers_list) {
            entityManager.merge(tower);
        }

        entityTransaction.commit();



        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        mainloop:
        while(isRunning){
            System.out.println("\nMenu:");
            System.out.println("1 - Dodać maga\n2 - Usunąć maga\n3 - Wypisać wszystkich magów\n4 - Dodać wieżę\n5 - Usunąć wieżę\n6 - Wypisać wszystkie wieże\n7 - Komenda\n8 - Wyjść");
            int choise = Integer.parseInt(sc.nextLine());
            if(choise == 1){
                entityTransaction.begin();
                System.out.println("Podaj imię:");
                String name = sc.nextLine();
                String mageName = name;
                System.out.println("Podaj level:");
                String level = sc.nextLine();
                int mageLevel = Integer.parseInt(level);
                System.out.println("Podaj nazwę wieży do której dodajemy maga:");
                String towerMag = sc.nextLine();
                Tower tower = entityManager.find(Tower.class, towerMag);
                if (tower != null) {
                    System.out.println("Wybrana wieża jest znależiona : " + tower.getName());
                } else {
                    System.out.println("Takiej wieży nie istnieje");
                }
                Mage ma = new Mage(mageName, mageLevel,tower);
                entityManager.persist(ma);
                tower.getMages().add(ma);
                entityManager.merge(tower);
                entityTransaction.commit();

                System.out.println("Mag został dodany\n");

            }
            else if(choise == 2){
                entityTransaction.begin();
                System.out.println("Podaj Nazwe maga do usunięcia:");
                String mageToDeleteBufor = sc.nextLine();
                Mage mageToDelete = entityManager.find(Mage.class, mageToDeleteBufor);

                if (mageToDelete != null) {
                    Tower mageTower = mageToDelete.getTower();
                    mageTower.getMages().remove(mageToDelete);
                    entityManager.remove(mageToDelete);
                    System.out.println("Usunięto maga : " + mageToDelete.getName());
                } else {
                    System.out.println("Maga o podanym identyfikatorze nie istnieje.");
                }
                entityTransaction.commit();
            }
            else if(choise == 3){
                System.out.println("\nWszystkie magi:");
                List<Mage> mages = entityManager.createQuery("SELECT m FROM Mage m", Mage.class).getResultList();
                for (Mage mage : mages) {
                    System.out.println("Mage: " + mage.getName() + ", Level: " + mage.getLevel() + ", Tower: " + mage.getTower().getName());
                }
            }
            else if(choise == 4){
                entityTransaction.begin();
                System.out.println("Podaj Nazwe:");
                String nameTower = sc.nextLine();
                System.out.println("Podaj wysokość wieży:");
                String height = sc.nextLine();
                int towerHeight = Integer.parseInt(height);
                Tower ta = new Tower(nameTower, towerHeight);
                entityManager.persist(ta);
                entityTransaction.commit();

                System.out.println("Wieża została dodana\n");
            }
            else if(choise == 5){
                entityTransaction.begin();
                System.out.println("Podaj nazwę wieży do usunięcia:");
                String towerToDeleteBufor = sc.nextLine();
                Tower towerToDelete = entityManager.find(Tower.class, towerToDeleteBufor);

                if (towerToDelete != null) {
                    entityManager.remove(towerToDelete);
                    System.out.println("Usunięto wieżę : " + towerToDelete.getName());
                } else {
                    System.out.println("Wieży o podanym identyfikatorze nie istnieje.");
                }
                entityTransaction.commit();
            }
            else if(choise == 6){
                entityTransaction.begin();
                System.out.println("\nWszystkie Wieże:");
                List<Tower> towers = entityManager.createQuery("SELECT t FROM Tower t", Tower.class).getResultList();
                for (Tower tower : towers) {
                    System.out.println("Wieża: " + tower.getName() + ", Wysokość: " + tower.getHeight() + ", Magowie:");
                    for (Mage mage : tower.getMages()) {
                        System.out.println("  - " + mage.getName() + ", Level: " + mage.getLevel());
                    }
                }
                entityTransaction.commit();
            }
            else if(choise == 7){
                entityTransaction.begin();
                System.out.println("\nWieżę z więcej niż dwoma magami:");

                List<Tower> towersWithMoreThanTwoMages = entityManager.createQuery(
                                "SELECT t FROM Tower t WHERE SIZE(t.mages) > 2", Tower.class)
                        .getResultList();

                for (Tower tower : towersWithMoreThanTwoMages) {
                    System.out.println("Wieża: " + tower.getName() + ", Wysokość: " + tower.getHeight() + ", Magowie:");
                    for (Mage mage : tower.getMages()) {
                        System.out.println("  - " + mage.getName() + ", Level: " + mage.getLevel());
                    }
                }
                entityTransaction.commit();
            }
            else if(choise == 8){
                entityTransaction.begin();
                System.out.println("\nExiting...");
                break mainloop;
            }

        }


        entityManager.close();
        entities.close();
    }
}