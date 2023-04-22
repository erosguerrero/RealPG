package com.example.realpg;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Pokemon {

    private Integer id;
    private String name;
    private String image;


    public Pokemon(){
        this.id = null;
        this.name = null;
        this.image = null;
    }

    public Pokemon(Integer id, String name,  String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Pokemon(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.image = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+id+".png";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pokemon pokemon = (Pokemon) o;
        return id.equals(pokemon.id) && name.equals(pokemon.name) && image.equals(pokemon.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, image);
    }

    @NonNull
    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
