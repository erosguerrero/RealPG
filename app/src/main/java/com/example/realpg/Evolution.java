package com.example.realpg;

import java.util.ArrayList;
import java.util.List;

public class Evolution {
    private Integer id;
    private List<Integer> ids;
    private List<Integer> levels;
    private List<String> names;
    private int current;

    public Evolution(){
        id = null;
        init();
    }

    public Evolution(Integer id){
        this.id = id;
        init();
    }

    private void init(){
        ids = new ArrayList<Integer>();
        levels = new ArrayList<Integer>();
        names = new ArrayList<String>();
        current = 0;
    }

    public void add(Integer id, Integer level, String name){
        ids.add(id);
        levels.add(level);
        names.add(name);
    }

    public Integer getId() {
        return id;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public List<Integer> getLevels() {
        return levels;
    }

    public List<String> getNames() {
        return names;
    }

    public int getCurrent() {
        return current;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Evolution{" +
                "id=" + id +
                ", ids=" + ids +
                ", levels=" + levels +
                ", names=" + names +
                ", current=" + current +
                '}';
    }
}
