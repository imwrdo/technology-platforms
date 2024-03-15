package org.example;

import org.example.modules.ComparatorMage;
import org.example.modules.Mage;

import java.util.*;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        String sorting = "Alternative";
        if(sorting.compareTo("None") == 0){
            Mage child1 = new Mage("Max", 6, 1.0, new HashSet<>());
            Mage child2 = new Mage("Daniel", 8, 2.0, new HashSet<>());
            Mage child3 = new Mage("Mary", 3, 3.0, new HashSet<>());
            Mage child4 = new Mage("Jack", 7, 4.0, new HashSet<>());
            Mage child5 = new Mage("Richard", 9, 5.0, new HashSet<>());

            Mage parent1 = new Mage("Levi", 10, 11.0, new HashSet<>(List.of(child1)));
            Mage parent2 = new Mage("Luke", 15, 12.0, new HashSet<>(List.of(child2, child3)));
            Mage parent3 = new Mage("Ryder", 20, 13.0, new HashSet<>(List.of(child4, child5)));

            Mage grandparent1 = new Mage("Luna", 40, 110.0, new HashSet<>(List.of(parent1)));
            Mage grandparent2 = new Mage("Chase", 50, 200.0, new HashSet<>(List.of(parent2, parent3)));

            Mage root = new Mage ("Creator", 1000, 2500.0, new HashSet<>(List.of(grandparent1, grandparent2)));
            root.printOut("");
            Map<Mage, Integer> stats = root.statGen(sorting, root);
            for(Map.Entry<Mage, Integer> entry : stats.entrySet()){
                System.out.println(entry.getKey() + " has a total of " + entry.getValue() + " children");
            }
        }
        else if(sorting.compareTo("Natural") == 0){
            Mage child1 = new Mage("Max", 6, 1.0, new TreeSet<>());
            Mage child2 = new Mage("Daniel", 8, 2.0, new TreeSet<>());
            Mage child3 = new Mage("Mary", 8, 3.0, new TreeSet<>());
            Mage child4 = new Mage("Jack", 7, 4.0, new TreeSet<>());
            Mage child5 = new Mage("Richard", 9, 5.0, new TreeSet<>());

            Mage parent1 = new Mage("Levi", 10, 11.0, new TreeSet<>(List.of(child1)));
            Mage parent2 = new Mage("Luke", 15, 12.0, new TreeSet<>(List.of(child2, child3)));
            Mage parent3 = new Mage("Ryder", 20, 13.0, new TreeSet<>(List.of(child4, child5)));

            Mage grandparent1 = new Mage("Luna", 40, 110.0, new TreeSet<>(List.of(parent1)));
            Mage grandparent2 = new Mage("Chase", 50, 200.0, new TreeSet<>(List.of(parent2, parent3)));

            Mage root = new Mage ("Creator", 1000, 2500.0, new TreeSet<>(List.of(grandparent1, grandparent2)));
            root.printOut("");
            Map<Mage, Integer> stats = root.statGen(sorting, root);
            for(Map.Entry<Mage, Integer> entry : stats.entrySet()){
                System.out.println(entry.getKey() + " has a total of " + entry.getValue() + " children");
            }
        }
        else if(sorting.compareTo("Alternative") == 0) {
            Mage child1 = new Mage("Max", 6, 1.0, new TreeSet<>());
            Mage child2 = new Mage("Daniel", 8, 2.0, new TreeSet<>());
            Mage child3 = new Mage("Mary", 3, 3.0, new TreeSet<>());
            Mage child4 = new Mage("Jack", 7, 4.0, new TreeSet<>());
            Mage child5 = new Mage("Richard", 9, 5.0, new TreeSet<>());

            TreeSet<Mage> parent1Set = new TreeSet<>(new ComparatorMage());
            parent1Set.addAll(List.of(child1));
            Mage parent1 = new Mage("Levi", 10, 11.0, parent1Set);
            TreeSet<Mage> parent2Set = new TreeSet<>(new ComparatorMage());
            parent2Set.addAll(List.of(child2, child3));
            Mage parent2 = new Mage("Luke", 15, 12.0, parent2Set);
            TreeSet<Mage> parent3Set = new TreeSet<>(new ComparatorMage());
            parent3Set.addAll(List.of(child4, child5));
            Mage parent3 = new Mage("Ryder", 20, 13.0, parent3Set);

            TreeSet<Mage> grandparent1Set = new TreeSet<>(new ComparatorMage());
            grandparent1Set.addAll(List.of(parent1));
            Mage grandparent1 = new Mage("Luna", 40, 110.0, grandparent1Set);
            TreeSet<Mage> grandparent2Set = new TreeSet<>(new ComparatorMage());
            grandparent2Set.addAll(List.of(parent2, parent3));
            Mage grandparent2 = new Mage("Chase", 50, 200.0, grandparent2Set);

            TreeSet<Mage> rootSet = new TreeSet<>(new ComparatorMage());
            rootSet.addAll(List.of(grandparent1, grandparent2));
            Mage root = new Mage("Creator", 1000, 2500.0, rootSet);
            root.printOut("");
            Map<Mage, Integer> stats = root.statGen(sorting, root);
            for(Map.Entry<Mage, Integer> entry : stats.entrySet()){
                System.out.println(entry.getKey() + " has a total of " + entry.getValue() + " children");
            }
        }
    }
}