package org.example;

import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

@Entity
public class Tower {
    @Id
    private String name;
    private int height;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mage> mages = new ArrayList<>();

    public Tower(String name, int height) {
        this.name = name;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public List<Mage> getMages() {
        return mages;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setMages(List<Mage> mages) {
        this.mages = mages;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tower{name=").append(name)
                .append(", height=").append(height)
                .append(", Mages=[");
        if (mages != null) {
            for (int i = 0; i < mages.size(); i++) {
                Mage mage = mages.get(i);
                stringBuilder.append("Mage{name=").append(mage.getName())
                        .append(", level=").append(mage.getLevel()).append("}");
                if (i < mages.size() - 1) {
                    stringBuilder.append(", ");
                }
            }
        }
        stringBuilder.append("]}");
        return stringBuilder.toString();
    }




}
