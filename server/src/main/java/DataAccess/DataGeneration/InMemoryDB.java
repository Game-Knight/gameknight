package DataAccess.DataGeneration;

//import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import DataAccess.DAO.APIs.APIBoardGameDAO;
import DataAccess.DAO.Interfaces.IBoardGameDAO;
import DataAccess.DataGeneration.JSON.JsonKeys;
import DataAccess.DataGeneration.JSON.JsonUtils;
import Entities.BoardGame;
import Entities.Ownership;
import Entities.User;
import Exceptions.DataAccessException;

public class InMemoryDB {

    private static final String BOARD_GAME_DATA_PATH = "./server/src/main/java/DataAccess/DataGeneration/JSON/board-game-data.json";
    private static final String BOARD_GAME_DATA_SUPPLEMENTAL_PATH = "./server/src/main/java/DataAccess/DataGeneration/JSON/board-game-data-supplemental.json";
    private static final String USER_DATA_PATH = "./server/src/main/java/DataAccess/DataGeneration/JSON/user-data.json";
    private static final String UPC_DATA_PATH = "./server/src/main/java/DataAccess/DataGeneration/JSON/board-game-upc.json";

    private static final String[] BGG_GAME_IDS = {"13", "74", "181", "320", "811", "822", "1258", "1294", "1406", "2223", "2375", "3955", "9209", "10547", "30549", "46213", "131357", "144344", "155703", "167791", "178900", "184267", "188834", "194655", "205398", "224037", "230802", "241724", "256916", "266192", "266524", "276182", "311475", "312786", "333481", "358816"};

    private static final int MAX_GAMES_PER_USER = 20;

    private static InMemoryDB dbInstance = null;

    public HashMap<String, BoardGame> boardGameTable;
    public HashMap<String, String> upcToBoardGameTable;
    public HashMap<String, User> userTable;

    public List<Ownership> ownershipTable;

    private InMemoryDB() throws DataAccessException {
        initBoardGameTable();
        initUPCToBoardGameTable();
        initUserTable();

        initOwnershipTable();
    }

    public static InMemoryDB getInstance() throws DataAccessException {
        if (dbInstance == null) {
            dbInstance = new InMemoryDB();
        }

        return dbInstance;
    }

    private void initBoardGameTable() throws DataAccessException {
        boardGameTable = new HashMap<>();

        List<JSONObject> rawSupplementalData = JsonUtils.getJSONObjectsFromFile(BOARD_GAME_DATA_SUPPLEMENTAL_PATH);
        Map<String, JSONObject> supplementalDataMap = new HashMap<>();
        for (JSONObject supplemental : rawSupplementalData) {
            supplementalDataMap.put((String) supplemental.get(JsonKeys.BGG_ID), supplemental);
        }

        List<JSONObject> rawBoardGames = JsonUtils.getJSONObjectsFromFile(BOARD_GAME_DATA_PATH);
        for (JSONObject rawBoardGame : rawBoardGames) {
            BoardGame boardGame = new BoardGame(
                    (String) rawBoardGame.get(JsonKeys.BGG_ID),
                    (String) rawBoardGame.get(JsonKeys.THUMBNAIL),
                    (String) rawBoardGame.get(JsonKeys.IMAGE),
                    JsonUtils.getInnerObjectStringValue(rawBoardGame, JsonKeys.NAME),
                    (String) rawBoardGame.get(JsonKeys.DESCRIPTION),
//                    StringEscapeUtils.unescapeHtml4((String) rawBoardGame.get(JsonKeys.DESCRIPTION)),
                    JsonUtils.getInnerObjectIntValue(rawBoardGame, JsonKeys.YEAR_PUBLISHED),
                    JsonUtils.getInnerObjectIntValue(rawBoardGame, JsonKeys.MIN_PLAYERS),
                    JsonUtils.getInnerObjectIntValue(rawBoardGame, JsonKeys.MAX_PLAYERS),
                    JsonUtils.getInnerObjectIntValue(rawBoardGame, JsonKeys.PLAYING_TIME),
                    JsonUtils.getInnerObjectIntValue(rawBoardGame, JsonKeys.MIN_PLAY_TIME),
                    JsonUtils.getInnerObjectIntValue(rawBoardGame, JsonKeys.MAX_PLAY_TIME),
                    JsonUtils.getInnerObjectIntValue(rawBoardGame, JsonKeys.MIN_AGE),
                    (String) supplementalDataMap.get((String) rawBoardGame.get(JsonKeys.BGG_ID)).get(JsonKeys.RULES_URL)
            );

            boardGameTable.put(boardGame.getBggId(), boardGame);
        }
    }

    private void initUPCToBoardGameTable() throws DataAccessException {
        upcToBoardGameTable = new HashMap<>();

        List<JSONObject> rawUPCData = JsonUtils.getJSONObjectsFromFile(UPC_DATA_PATH);
        for (JSONObject rawUPC : rawUPCData) {
            String upc = (String) rawUPC.get(JsonKeys.UPC);
            String bggId = (String) rawUPC.get(JsonKeys.UPC_BGG_ID);

            upcToBoardGameTable.put(upc, bggId);
        }
    }

    private void initUserTable() throws DataAccessException {
        userTable = new HashMap<>();

        List<JSONObject> rawUsers = JsonUtils.getJSONObjectsFromFile(USER_DATA_PATH);
        for (JSONObject rawUser : rawUsers) {
            String firstName = (String) rawUser.get(JsonKeys.FIRST_NAME);
            String lastName = (String) rawUser.get(JsonKeys.LAST_NAME);
            String phoneNumber = (String) rawUser.get(JsonKeys.PHONE_NUMBER);
            String password = (String) rawUser.get(JsonKeys.PASSWORD);

            User user = new User(phoneNumber, firstName, lastName, password);
            userTable.put(user.getPhoneNumber(), user);
        }
    }

    private void initOwnershipTable() {
        ownershipTable = new ArrayList<>();

        List<String> boardGameIds = new ArrayList<>(boardGameTable.keySet());
        for (Map.Entry<String, User> userEntry : userTable.entrySet()) {
            Random rand = new Random();
            int numGamesForUser = rand.nextInt(MAX_GAMES_PER_USER);
            for (int i = 0; i < numGamesForUser; i++) {
                String randomBoardGameId = boardGameIds.get(rand.nextInt(boardGameIds.size()));
                ownershipTable.add(new Ownership(userEntry.getKey(), randomBoardGameId));
            }
        }
    }
}
