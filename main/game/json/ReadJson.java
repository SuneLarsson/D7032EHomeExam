package main.game.json;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class ReadJson {
    public static JSONArray jsonData(String filePath){
        try (InputStream fInputStream = new FileInputStream(filePath);
            Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {
    
            // Read the entire JSON file into a String
            String jsonString = scanner.hasNext() ? scanner.next() : "";
    
            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);
    
            // Get the "cards" array from the JSONObject
            JSONArray cardsArray = jsonObject.getJSONArray("cards");

            return cardsArray;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}