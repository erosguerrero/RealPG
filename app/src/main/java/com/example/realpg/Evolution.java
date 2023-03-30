package com.example.realpg;

import java.util.ArrayList;
import java.util.List;

public class Evolution {

    private Integer id;
    private List<String> chain;
    private int current;

    public Evolution(){
        this.id = null;
        this.chain = new ArrayList<String>();
        this.current = 0;
    }

    public Evolution(Integer id){
        this.id = id;
        this.chain = new ArrayList<String>();
        this.current = 0;
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public List<String> getChain(){
        return this.chain;
    }

    public void setChain(List<String> chain){
        this.chain = chain;
    }

    public void add(String pokeUrl){
        this.chain.add(pokeUrl);
    }

    public String getPokemon(){
        return this.chain.get(current);
    }

    public void evolve(){
        if (current < this.chain.size() - 1)
            this.current++;
    }




}
