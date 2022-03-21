package DataAccess.DataGeneration;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Entities.BoardGame;
import Entities.GameNight;
import Entities.Ownership;
import Entities.User;
import Exceptions.DataAccessException;

public class InMemoryDB {

    private static final String BOARD_GAME_DATA_PATH = "./backend/src/main/java/DataAccess/DataGeneration/board-game-data.json";
    private static final String USER_DATA_PATH = "./backend/src/main/java/DataAccess/DataGeneration/user-data.json";

    private static InMemoryDB dbInstance = null;

    public HashMap<String, BoardGame> boardGameTable;
    public HashMap<String, GameNight> gameNightByIdTable;
    public HashMap<String, GameNight> gameNightByOwnerTable;
    public List<Ownership> ownershipTable;
    public HashMap<String, User> userTable;

    private InMemoryDB() throws DataAccessException {
        initBoardGameTable();
        initUserTable();

        gameNightByIdTable = new HashMap<>();
        gameNightByOwnerTable = new HashMap<>();
        ownershipTable = new ArrayList<>();
    }

    public static InMemoryDB getInstance() throws DataAccessException {
        if (dbInstance == null) {
            dbInstance = new InMemoryDB();
        }

        return dbInstance;
    }

    private void initBoardGameTable() throws DataAccessException {
        boardGameTable = new HashMap<>();

        List<JSONObject> rawBoardGames = getJSONObjectsFromFile(BOARD_GAME_DATA_PATH);
        for (JSONObject rawBoardGame : rawBoardGames) {
            JSONObject rawBoardGameInfo = (JSONObject) ((JSONArray) rawBoardGame.get("items")).get(0);

            String title = (String) rawBoardGameInfo.get("title");
            String description = (String) rawBoardGameInfo.get("description");
            String imageUrl = (String) ((JSONArray) rawBoardGameInfo.get("images")).get(0);

            BoardGame boardGame = new BoardGame(title, description, imageUrl);
            boardGameTable.put(boardGame.getId(), boardGame);
        }
    }

    private void initUserTable() throws DataAccessException {
        userTable = new HashMap<>();

        List<JSONObject> rawUsers = getJSONObjectsFromFile(USER_DATA_PATH);
        for (JSONObject rawUser : rawUsers) {
            String firstName = (String) rawUser.get("firstName");
            String lastName = (String) rawUser.get("lastName");
            String phoneNumber = (String) rawUser.get("phoneNumber");
            String password = (String) rawUser.get("password");

            User user = new User(phoneNumber, firstName, lastName, password);
            userTable.put(user.getPhoneNumber(), user);
        }
    }

    private List<JSONObject> getJSONObjectsFromFile(String filePath) throws DataAccessException {
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
