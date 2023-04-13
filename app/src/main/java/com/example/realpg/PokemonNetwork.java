package com.example.realpg;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.UnknownHostException;

public class PokemonNetwork {

    private static final String URL = "https://pokeapi.co/api/v2/pokemon/";

    public static String getPokemon(Integer id){

        HttpURLConnection urlConnection = null;
        String json = null;
        BufferedReader reader = null;

        Uri uri = Uri.parse(URL + id.toString());

        try{

            URL requestUrl = new URL(uri.toString());

            Log.d("POKEMONAPI", requestUrl.toString());

            urlConnection = (HttpURLConnection) requestUrl.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(inputStream));

            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null){
                builder.append(line);
                builder.append("\n");
            }

            if (builder.length() == 0) return null;

            json = builder.toString();

        }
        catch (UnknownHostException e){
            Log.e("POKEMONAPI", "Error al conectar con el host: " + e.getMessage());

        }
        catch (Exception e){
            Log.e("POKEMONAPI", "Error al llamar a la api: " + e.getMessage());

        }
        finally {
            try{
                if (urlConnection != null) urlConnection.disconnect();
                if (reader != null) reader.close();
            }
            catch (Exception e){
                Log.e("POKEMONAPI", "Error al cerrar las conexiones");
            }
        }


        return json;
    }

}
