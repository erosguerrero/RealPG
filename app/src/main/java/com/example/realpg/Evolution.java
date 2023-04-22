package com.example.realpg;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Evolution {
    private Integer id;
    private List<Integer> ids;
    private ArrayList<Integer> levels;
    private List<String> names;
    private double currentXp;


    public Evolution(){
        id = null;
        init();
    }

    public Evolution(Integer id, List<Integer> ids, ArrayList<Integer> levels, List<String> names, double currentXp) {
        this.id = id;
        this.ids = ids;
        this.levels = levels;
        this.names = names;
        this.currentXp = currentXp;
    }

    public Evolution(Integer id){
        this.id = id;
        init();
    }

    public Pokemon getCurrentPokemon(){
        int index = 0;
        for(int i = 0; i < levels.size(); i++) {
            if((int)Math.floor(currentXp) > levels.get(i)){
                index++;
            }
        }

        return new Pokemon(ids.get(index), names.get(index));
    }
    private void init(){
        ids = new ArrayList<Integer>();
        levels = new ArrayList<Integer>();
        names = new ArrayList<String>();
    }

    public void add(Integer id, Integer level, String name){
        ids.add(id);
        levels.add(level);
        names.add(name);
    }

    public JSONObject toJson()
    {
        JSONObject json = new JSONObject();
        try {
            JSONObject jsonData = new JSONObject();
            jsonData.put("level", this.currentXp);
            jsonData.put("levels", this.levels);
            jsonData.put("names", names);
            jsonData.put("ids", this.ids);
            json.put(String.valueOf(this.id),jsonData);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
    /**Genera un objeto nuevo de tipo Evolution de un JSON
     *
     * @param idEvolution Id de la cadena evolutiva
     * @param json el jsonData de la id en cuestión
     * @return
     */
    public static Evolution createEvolutionFromJson(int idEvolution, JSONObject json)
    {
        try {
            JSONObject selectedJSON = json.getJSONObject(String.valueOf(idEvolution));
            double xpCurrent = selectedJSON.getDouble("level");

            JSONArray levelsArray = selectedJSON.getJSONArray("levels");
            JSONArray namesArray = selectedJSON.getJSONArray("names");
            JSONArray idsArray = selectedJSON.getJSONArray("ids");


            ArrayList<Integer> levelsList = new ArrayList<Integer>();
            ArrayList<String> namesList = new ArrayList<String>();
            ArrayList<Integer> idsList = new ArrayList<Integer>();

            for(int i = 0; i < levelsArray.length(); i++) {levelsList.add(levelsArray.getInt(i));}
            for(int i = 0; i < namesArray.length(); i++) {namesList.add(namesArray.getString(i));}
            for(int i = 0; i < idsArray.length(); i++) {idsList.add(idsArray.getInt(i));}

            return new Evolution(idEvolution, idsList, levelsList, namesList, xpCurrent);


        } catch (JSONException e) {
            throw new RuntimeException(e);
        }


    }
    public Integer getId() {
        return id;
    }

    public List<Integer> getIds() {
        return ids;
    }

    public ArrayList<Integer> getLevels() {
        return levels;
    }

    public List<String> getNames() {
        return names;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public double getCurrentXp() {
        return currentXp;
    }

    @Override
    public String toString() {
        return "Evolution{" +
                "id=" + id +
                ", ids=" + ids +
                ", levels=" + levels +
                ", names=" + names +
                ", current=" + getCurrentPokemon().toString()+
                '}';
    }
}
