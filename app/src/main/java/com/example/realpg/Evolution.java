package com.example.realpg;

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
    private int current;

    public Evolution(){
        id = null;
        init();
    }

    public Evolution(Integer id, List<Integer> ids, ArrayList<Integer> levels, List<String> names, double currentXp, int current) {
        this.id = id;
        this.ids = ids;
        this.levels = levels;
        this.names = names;
        this.currentXp = currentXp;
        this.current = current;
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
            double xpCurrent = json.getDouble("level");
            //levels
            //names
            //ids


            return new Evolution();


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
