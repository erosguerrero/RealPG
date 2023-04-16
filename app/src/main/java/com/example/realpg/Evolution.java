package com.example.realpg;

import java.util.ArrayList;
import java.util.List;

public class Evolution {

    private Integer id;
    private List<Integer> chain;
    private int current;

    public Evolution(){
        this.id = null;
        this.chain = new ArrayList<Integer>();
        this.current = 0;
    }

    public Evolution(Integer id){
        this.id = id;
        this.chain = new ArrayList<Integer>();
        this.current = 0;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public List<Integer> getChain(){
        return this.chain;
    }

    public void setChain(List<Integer> chain){
        this.chain = chain;
    }

    public void add(Integer pokeId){
        this.chain.add(pokeId);
    }

    public Integer getPokemon(){
        return this.chain.get(current);
    }

    public void evolve(){
        if (current < this.chain.size() - 1)
            this.current++;
    }

    @Override
    public String toString() {
        return "Evolution{" +
                "id=" + id +
                ", chain=" + chain +
                ", current=" + current +
                '}';
    }
}
