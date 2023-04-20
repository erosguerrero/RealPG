package com.example.realpg;

public class ActivityBasicInfo{
    private String name;
    private int id;

    private Category cat;

    public ActivityBasicInfo(String name, int id, Category cat){this.name = name; this.id = id; this.cat = cat;}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Category getCat() {
        return cat;
    }

    public void setCat(Category cat) {
        this.cat = cat;
    }
}
