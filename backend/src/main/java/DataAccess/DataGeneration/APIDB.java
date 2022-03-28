package DataAccess.DataGeneration;

//import org.apache.commons.text.StringEscapeUtils;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DataAccess.DAO.APIs.APIBoardGameDAO;
import DataAccess.DAO.Interfaces.IBoardGameDAO;
import Entities.BoardGame;
import Entities.Ownership;
import Entities.User;
import Exceptions.DataAccessException;

public class APIDB {
    private static final String USER_DATA_PATH = "./backend/src/main/java/DataAccess/DataGeneration/JSON/user-data.json";
    private static final String UPC_DATA_PATH = "./backend/src/main/java/DataAccess/DataGeneration/JSON/board-game-upc.json";

    private static final String[] BGG_GAME_IDS = {"13", "74", "181", "320", "811", "822", "1258", "1294", "1406", "2223", "2375", "3955", "9209", "10547", "30549", "46213", "131357", "144344", "155703", "167791", "178900", "184267", "188834", "194655", "205398", "224037", "230802", "241724", "256916", "266192", "266524", "276182", "311475", "312786", "332290", "333481", "358816"};

    private static final int MAX_GAMES_PER_USER = 20;

    private static APIDB dbInstance = null;

    public HashMap<String, BoardGame> boardGameTable;
    public HashMap<String, String> upcToBoardGameTable;
    public HashMap<String, User> userTable;

    public List<Ownership> ownershipTable;

    private APIDB() throws DataAccessException {
        initBoardGameTable();
        initUPCToBoardGameTable();
        initUserTable();

        initOwnershipTable();
    }

    public static APIDB getInstance() throws DataAccessException {
        if (dbInstance == null) {
            dbInstance = new APIDB();
        }

        return dbInstance;
    }

    private void initBoardGameTable() throws DataAccessException {
        boardGameTable = new HashMap<>();

        IBoardGameDAO boardGameDAO = new APIBoardGameDAO();

        Map<String, JSONObject> supplementalDataMap = new HashMap<>();
        List<BoardGame> gamesList = boardGameDAO.getBoardGamesByIds(Arrays.asList(BGG_GAME_IDS), 0);

        for (BoardGame game : gamesList) {
            boardGameTable.put(game.getBggId(), game);
        }
    }

    private void initUPCToBoardGameTable() throws DataAccessException {
        upcToBoardGameTable = new HashMap<>();

//        List<JSONObject> rawUPCData = JsonUtils.getJSONObjectsFromFile(UPC_DATA_PATH);
//        for (JSONObject rawUPC : rawUPCData) {
//            String upc = (String) rawUPC.get(JsonKeys.UPC);
//            String bggId = (String) rawUPC.get(JsonKeys.UPC_BGG_ID);
//
//            upcToBoardGameTable.put(upc, bggId);
//        }
    }

    private void initUserTable() throws DataAccessException {
        userTable = new HashMap<>();

//        List<JSONObject> rawUsers = JsonUtils.getJSONObjectsFromFile(USER_DATA_PATH);
//        for (JSONObject rawUser : rawUsers) {
//            String firstName = (String) rawUser.get(JsonKeys.FIRST_NAME);
//            String lastName = (String) rawUser.get(JsonKeys.LAST_NAME);
//            String phoneNumber = (String) rawUser.get(JsonKeys.PHONE_NUMBER);
//            String password = (String) rawUser.get(JsonKeys.PASSWORD);
//
//            User user = new User(phoneNumber, firstName, lastName, password);
//            userTable.put(user.getPhoneNumber(), user);
//        }
    }

    private void initOwnershipTable() {
        ownershipTable = new ArrayList<>();
//
//        List<String> boardGameIds = new ArrayList<>(boardGameTable.keySet());
//        for (Map.Entry<String, User> userEntry : userTable.entrySet()) {
//            Random rand = new Random();
//            int numGamesForUser = rand.nextInt(MAX_GAMES_PER_USER);
//            for (int i = 0; i < numGamesForUser; i++) {
//                String randomBoardGameId = boardGameIds.get(rand.nextInt(boardGameIds.size()));
//                ownershipTable.add(new Ownership(userEntry.getKey(), randomBoardGameId));
//            }
//        }
    }
}
