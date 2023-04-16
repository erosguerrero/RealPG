package com.example.realpg.ui.main;

public class PokeRecyclerInfo {

    public String name;
    //public boolean isSelected = false;

    private int idPoke = 0;

    //TODO mas adelante valorar si esto sera una url a la imagen como ahora, o ya directamente la imagen como bitmap
   //TODO, aun asi al utilizar la libreria Glide, alamacena las imaganes en cache, tardando solo la primera vez
    private String urlToImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/";

    public PokeRecyclerInfo(String name, int id){
        this.name = name;
        this.idPoke =  id;
        urlToImage = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/"+idPoke+".png";
    }

    public String getImgUrlStr()
    {
        return  urlToImage;
    }

    public int getIdPoke(){
        return idPoke;
    }

}
