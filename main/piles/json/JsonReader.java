package main.piles.json;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Reads a JSON file and returns the data as a JSONArray.
 */

public class JsonReader {

    /**
     * Reads a JSON file and returns the data as a JSONArray.
     * 
     * @param filePath The path to the JSON file.
     * @return The data from the JSON file as a JSONArray.
     */
    public JSONArray jsonData(String filePath) {
        try (InputStream fInputStream = new FileInputStream(filePath);
            Scanner scanner = new Scanner(fInputStream, "UTF-8").useDelimiter("\\A")) {
    
            // Read the entire JSON file into a String
            String jsonString = scanner.hasNext() ? scanner.next() : "";
            scanner.close();
    
            // Parse the JSON string into a JSONObject
            JSONObject jsonObject = new JSONObject(jsonString);
    
            // Get the "cards" array from the JSONObject
            // JSONArray cardsArray = jsonObject.getJSONArray("cards");
            return jsonObject.getJSONArray("cards");

        } catch (IOException e) {
            System.out.println("File error: " + e.getMessage());
            return new JSONArray(); // Return empty JSONArray instead of null
        } catch (JSONException e) {
            System.out.println("JSON parsing error: " + e.getMessage());
            return new JSONArray(); // Return empty JSONArray instead of null
        }

    }
}