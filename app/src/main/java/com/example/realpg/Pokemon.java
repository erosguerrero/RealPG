package com.example.realpg;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Pokemon {

    private Integer id;
    private String name;
    private String description;
    private String image;


    public Pokemon(){
        this.id = null;
        this.name = null;
        this.description = null;
        this.image = null;
    }

    public Pokemon(Integer id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
        return Objects.equals(id, pokemon.id) && Objects.equals(name, pokemon.name) && Objects.equals(description, pokemon.description) && Objects.equals(image, pokemon.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, image);
    }

    @NonNull
    @Override
    public String toString() {
        return "Pokemon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
