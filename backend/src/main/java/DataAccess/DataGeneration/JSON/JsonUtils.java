package DataAccess.DataGeneration.JSON;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import DataAccess.DataGeneration.JSON.JsonKeys;
import Exceptions.DataAccessException;

public class JsonUtils {

    public static String getInnerObjectStringValue(JSONObject jsonObject, String key) {
        JSONObject innerObject = (JSONObject) jsonObject.get(key);
        return (String) (innerObject).get(JsonKeys.VALUE);
    }

    public static int getInnerObjectIntValue(JSONObject jsonObject, String key) {
        return Integer.parseInt(getInnerObjectStringValue(jsonObject, key));
    }

    public static List<JSONObject> getJSONObjectsFromFile(String filePath) throws DataAccessException {
        try {
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader(filePath));

            List<JSONObject> jsonObjects = new ArrayList<>();
            for (Object jsonObject : jsonArray) {
                jsonObjects.add((JSONObject) jsonObject);
            }

            return jsonObjects;
        }
        catch (Exception ex) {
            throw new DataAccessException("Unable to extract objects from JSON data file: " + ex.getMessage());
        }
    }
}
