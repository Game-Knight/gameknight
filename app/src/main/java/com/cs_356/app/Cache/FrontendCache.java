package com.cs_356.app.Cache;

import android.util.Log;

import com.cs_356.app.Utils.Constants;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

import Entities.BoardGame;
import Entities.GameNight;
import Entities.Ownership;
import Entities.User;
import Enums.RSVP;

public class FrontendCache {

    private static final Random RANDOM = new Random();
    private static List<BoardGame> gamesList = null;
    private static Map<String, BoardGame> gamesMap = null;
    private static Map<String, String> upcMappings = null;
    private static List<User> userList = null;
    private static Map<String, User> userMap = null;
    private static Set<Ownership> ownershipSet = null;
    private final static String LOG_TAG = "FRONTEND_CACHE";

    private static final User authenticatedUser = new User(
            "123-456-7890", "Test", "User", "test");
    private static List<BoardGame> gamesListForAuthenticatedUser = null;
    /* This is also only for the authenticatedUser since we have no need for seeing
     * other game nights */
    private static List<GameNight> gameNightList = null;
    private static Map<String, GameNight> gameNightMap = null;

    /**
     * This section is for cache getters
     */

    public static void addGameOwnershipForAuthUser(BoardGame game) {
        getOwnershipSet().add(new Ownership(authenticatedUser.getPhoneNumber(), game.getBggId()));
        if (getGamesMap().get(game.getBggId()) == null) {
            getGamesList().add(game);
            getGamesMap().put(game.getBggId(), game);

            if (game.getUpc() != null && !game.getUpc().equals("")) {
                getUPCMappings().put(game.getUpc(), game.getBggId());
            }
        }

        getGamesForAuthenticatedUser().add(getGamesMap().get(game.getBggId()));
    }

    public static Set<Ownership> getOwnershipSet() {
        if (ownershipSet == null) {
            Log.d(LOG_TAG, "running initOwenershipSetCache()");
            initOwnershipSetCache();
            Log.d(LOG_TAG, "finished initOwenershipSetCache()");
        }

        return ownershipSet;
    }

    public static Map<String, User> getUserMap() {
        if (userMap == null) {
            Log.d(LOG_TAG, "running initUsersMapCache()");
            initUsersMapCache();
            Log.d(LOG_TAG, "finished initUsersMapCache()");
        }

        return userMap;
    }

    public static List<User> getUserList() {
        if (userList == null) {
            Log.d(LOG_TAG, "running initUsersCache()");
            initUsersCache();
            Log.d(LOG_TAG, "finished initUsersCache()");
        }

        return userList;
    }

    public static BoardGame getGameMatchingUPC(String upc) {
        String bggId = getUPCMappings().get(upc);
        return bggId != null ? getGamesMap().get(bggId) : null;
    }

    public static Map<String, BoardGame> getGamesMap() {
        if (gamesMap == null) {
            Log.d(LOG_TAG, "running initGamesMapCache()");
            initGamesMapCache();
            Log.d(LOG_TAG, "finished initGamesMapCache()");
        }

        return gamesMap;
    }

    public static Map<String, String> getUPCMappings() {
        if (upcMappings == null) {
            Log.d(LOG_TAG, "running initUPCCache()");
            initUPCCache();
            Log.d(LOG_TAG, "finished initUPCCache()");
        }

        return upcMappings;
    }

    public static Set<BoardGame> getGamesAvailableForGameNight(String gameNightId) {
        GameNight gameNight = getGameNightMap().get(gameNightId);

        Set<BoardGame> availableGames = new HashSet<>();
        Set<String> userIds = new HashSet<>();
        userIds.add(authenticatedUser.getPhoneNumber());

        if (gameNight != null && gameNight.getGuestList() != null && gameNight.getGuestList().size() > 0) {
            for (Map.Entry<String, RSVP> guest : gameNight.getGuestList().entrySet()) {
                if (guest.getValue() != RSVP.NO) {
                    userIds.add(guest.getKey());
                }
            }
        }

        for (Ownership ownership : getOwnershipSet()) {
            if (userIds.contains(ownership.getUserId())) {
                availableGames.add(getGamesMap().get(ownership.getBoardGameId()));
            }
        }

        return availableGames;
    }

    public static List<GameNight> getGameNightsForAuthenticatedUser() {
        List<GameNight> gameNights = new ArrayList<>();
        return gameNights;
    }

    public static List<GameNight> getGameNightsHostedByAuthenticatedUser() {
        List<GameNight> gameNights = new ArrayList<>();
        return gameNights;
    }

    public static List<BoardGame> getGamesForAuthenticatedUser() {
        if (gamesListForAuthenticatedUser == null) {
            gamesListForAuthenticatedUser = getGamesForUser(authenticatedUser.getPhoneNumber());
        }

        return gamesListForAuthenticatedUser;
    }

    public static List<BoardGame> getGamesForUser(String phoneNumber) {
        List<BoardGame> boardGames = new ArrayList<>();

        for (Ownership ownership : getOwnershipSet()) {
            if (ownership.getUserId().equals(phoneNumber)) {
                boardGames.add(getGamesMap().get(ownership.getBoardGameId()));
            }
        }

        return boardGames;
    }

    public static List<BoardGame> getGamesList() {
        if (gamesList == null) {
            Log.d(LOG_TAG, "running initGamesCache()");
            initGamesCache();
            Log.d(LOG_TAG, "finished initGamesCache()");
        }

        return gamesList;
    }

    public static Map<String, GameNight> getGameNightMap() {
        if (gameNightMap == null) {
            Log.d(LOG_TAG, "running initGameNightMapCache()");
            initGameNightMapCache();
            Log.d(LOG_TAG, "finished initGameNightMapCache()");
        }

        return gameNightMap;
    }

    public static List<GameNight> getGameNightList() {
        if (gameNightList == null) {
            Log.d(LOG_TAG, "running initGameNightCache()");
            initGameNightCache();
            Log.d(LOG_TAG, "finished initGameNightCache()");
        }

        return gameNightList;
    }

    /**
     * The section below is for cache initialization methods.
     */

    private static void initOwnershipSetCache() {
        ownershipSet = new HashSet<>();

        List<String> boardGameIds = new ArrayList<>(getGamesMap().keySet());
        for (Map.Entry<String, User> userEntry : getUserMap().entrySet()) {
            if (userEntry.getKey().equals(authenticatedUser.getPhoneNumber())) {
                ownershipSet.add(new Ownership(userEntry.getKey(), "13"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "74"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "181"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "320"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "1258"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "1294"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "1406"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "2223"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "131357"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "178900"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "188834"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "266192"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "256916"));
                ownershipSet.add(new Ownership(userEntry.getKey(), "822"));
            }
            else {
                int numGamesForUser = RANDOM.nextInt(Constants.MAX_GAMES_PER_USER);

                int gamesAdded = 0;
                while (gamesAdded < numGamesForUser) {
                    String randomBoardGameId = boardGameIds.get(RANDOM.nextInt(boardGameIds.size()));
                    Ownership ownership = new Ownership(userEntry.getKey(), randomBoardGameId);

                    boolean success = ownershipSet.add(ownership);
                    if (success) {
                        gamesAdded++;
                    }
                }
                Log.d(LOG_TAG, "Added " + gamesAdded + " games for user " + userEntry.getKey());
            }
        }
    }

    private static void initGameNightMapCache() {
        gameNightMap = new HashMap<>();

        for (GameNight gameNight : getGameNightList()) {
            gameNightMap.put(gameNight.getId(), gameNight);
        }
    }

    private static void initUsersMapCache() {
        userMap = new HashMap<>();

        for (User user : getUserList()) {
            userMap.put(user.getPhoneNumber(), user);
        }
    }

    private static void initUsersCache() {
        userList = new ArrayList<>();

        userList.add(authenticatedUser);
        userList.add(new User("665-123-3838", "Sorcha", "Paintain", "Red"));
        userList.add(new User("936-943-9307", "Wat", "Rubenovic", "Maroon"));
        userList.add(new User("599-736-4222", "Viva", "Espinel", "Crimson"));
        userList.add(new User("963-474-2980", "Jessamine", "Mealand", "Red"));
        userList.add(new User("420-788-9562", "Al", "Francillo", "Green"));
        userList.add(new User("880-781-6016", "Noby", "Pashe", "Aquamarine"));
        userList.add(new User("571-567-0176", "Melessa", "Mayall", "Khaki"));
        userList.add(new User("533-617-3529", "Donnajean", "Mathys", "Mauv"));
        userList.add(new User("715-743-9381", "Farand", "Ceaplen", "Violet"));
        userList.add(new User("102-280-1984", "Nan", "Blanckley", "Red"));
        userList.add(new User("697-219-5262", "Junina", "Mahony", "Crimson"));
        userList.add(new User("867-720-0421", "Alvie", "McRae", "Crimson"));
        userList.add(new User("152-437-3026", "Harmonia", "Nield", "Fuscia"));
        userList.add(new User("231-458-3966", "Antonella", "Yesenev", "Blue"));
        userList.add(new User("587-276-8711", "Alric", "Doust", "Violet"));
        userList.add(new User("755-644-5425", "Kellen", "Lockhart", "Fuscia"));
        userList.add(new User("269-105-6282", "Estele", "Cote", "Crimson"));
        userList.add(new User("908-949-3803", "Sharona", "Janssen", "Blue"));
        userList.add(new User("629-433-1900", "Nikkie", "Sudddard", "Turquoise"));
        userList.add(new User("949-698-9852", "Jonas", "Santo", "Maroon"));
        userList.add(new User("381-984-5567", "Corrie", "Abbott", "Goldenrod"));
        userList.add(new User("688-237-9359", "Charin", "Vertey", "Pink"));
        userList.add(new User("976-115-5792", "Ilka", "Brigg", "Crimson"));
        userList.add(new User("887-739-5788", "Thekla", "Chatterton", "Yellow"));
        userList.add(new User("171-380-7033", "Angele", "Cholonin", "Orange"));
        userList.add(new User("981-907-1748", "Alvinia", "MacNeachtain", "Green"));
        userList.add(new User("829-223-5549", "Raynor", "Guillond", "Purple"));
        userList.add(new User("342-841-3740", "Parker", "Blofeld", "Teal"));
        userList.add(new User("701-168-1756", "Beltran", "Chape", "Orange"));
        userList.add(new User("213-477-4846", "Nefen", "Brunner", "Blue"));
        userList.add(new User("646-885-1661", "Melicent", "Kemell", "Orange"));
        userList.add(new User("188-260-3416", "Ginger", "Guinane", "Red"));
        userList.add(new User("622-586-6456", "Boigie", "Dimond", "Purple"));
        userList.add(new User("152-336-0716", "Land", "Faulkner", "Violet"));
        userList.add(new User("966-349-8785", "Allan", "Hogsden", "Aquamarine"));
        userList.add(new User("991-602-5145", "Janessa", "Furlonge", "Purple"));
        userList.add(new User("443-308-4572", "Sandra", "Clemmey", "Indigo"));
        userList.add(new User("844-422-2573", "Queenie", "Tunmore", "Orange"));
        userList.add(new User("819-343-4770", "Datha", "Kersting", "Maroon"));
        userList.add(new User("902-450-5022", "Cilka", "Putland", "Pink"));
        userList.add(new User("532-309-7259", "Jackquelin", "Arrell", "Teal"));
        userList.add(new User("246-676-0569", "Riley", "Yushkov", "Mauv"));
        userList.add(new User("748-915-1758", "Skelly", "Howles", "Mauv"));
//        userList.add(new User("647-130-6590", "Eamon", "Spuffard", "Indigo"));
//        userList.add(new User("500-536-8337", "Koren", "Aindriu", "Yellow"));
//        userList.add(new User("344-656-6380", "Chrisse", "Godman", "Puce"));
//        userList.add(new User("227-726-2979", "Antin", "Mechan", "Crimson"));
//        userList.add(new User("993-598-1272", "Oates", "Dominetti", "Green"));
//        userList.add(new User("214-551-8750", "Tammie", "Kolak", "Puce"));
//        userList.add(new User("138-765-5942", "Upton", "Karczinski", "Yellow"));
//        userList.add(new User("589-487-0845", "Roda", "Squibbs", "Turquoise"));
//        userList.add(new User("630-780-0434", "Vanny", "Lionel", "Maroon"));
//        userList.add(new User("248-992-7467", "Loydie", "Goomes", "Goldenrod"));
//        userList.add(new User("273-487-2364", "Tamma", "Lawranson", "Aquamarine"));
//        userList.add(new User("425-947-6739", "Josephina", "Tynan", "Orange"));
//        userList.add(new User("907-324-8336", "Karyl", "Allott", "Teal"));
//        userList.add(new User("942-834-4688", "Klara", "Lepick", "Fuscia"));
//        userList.add(new User("753-271-9616", "Sarge", "Madine", "Orange"));
//        userList.add(new User("910-350-6880", "Slade", "Duell", "Mauv"));
//        userList.add(new User("364-806-8479", "Miran", "Duhig", "Yellow"));
//        userList.add(new User("934-675-0756", "Barney", "Bushaway", "Aquamarine"));
//        userList.add(new User("306-860-5599", "Darill", "Lynch", "Maroon"));
//        userList.add(new User("465-456-1021", "Bliss", "Daton", "Yellow"));
//        userList.add(new User("791-997-1729", "Ashli", "Woodroffe", "Maroon"));
//        userList.add(new User("828-109-5117", "Madge", "Ripon", "Puce"));
//        userList.add(new User("252-745-5999", "Carce", "Giannassi", "Pink"));
//        userList.add(new User("588-221-6263", "Enrique", "Colt", "Crimson"));
//        userList.add(new User("759-390-4604", "Tobie", "Latta", "Purple"));
//        userList.add(new User("147-470-6133", "Zahara", "Good", "Indigo"));
//        userList.add(new User("902-193-8394", "Scotty", "Giacomazzo", "Teal"));
//        userList.add(new User("276-687-3370", "Kare", "Battlestone", "Turquoise"));
//        userList.add(new User("846-617-9925", "Sharon", "Inder", "Orange"));
//        userList.add(new User("874-221-4929", "Shoshana", "Pearse", "Puce"));
//        userList.add(new User("555-535-8569", "Analise", "Lapenna", "Fuscia"));
//        userList.add(new User("199-583-5166", "Alejoa", "Sydney", "Green"));
//        userList.add(new User("698-944-3495", "Taber", "Keogh", "Pink"));
//        userList.add(new User("640-697-0623", "Teodorico", "Dubs", "Orange"));
//        userList.add(new User("745-932-3392", "Belvia", "Tivenan", "Blue"));
//        userList.add(new User("522-326-7150", "Ron", "Bouette", "Teal"));
//        userList.add(new User("370-756-2496", "Imogene", "Kirkup", "Yellow"));
//        userList.add(new User("808-545-4170", "Erwin", "Dradey", "Fuscia"));
//        userList.add(new User("704-432-9112", "Gregg", "Vassar", "Fuscia"));
//        userList.add(new User("826-533-9872", "Wainwright", "Polycote", "Khaki"));
//        userList.add(new User("569-565-5844", "Ely", "McIntosh", "Aquamarine"));
//        userList.add(new User("974-445-3971", "Rachele", "Betz", "Teal"));
//        userList.add(new User("477-191-5578", "Bailie", "Sleight", "Yellow"));
//        userList.add(new User("670-728-2872", "Son", "Bodycomb", "Maroon"));
//        userList.add(new User("904-413-7444", "Elene", "Petkens", "Teal"));
//        userList.add(new User("228-984-7477", "Wynn", "Covil", "Aquamarine"));
//        userList.add(new User("707-279-6977", "Arda", "Deniset", "Blue"));
//        userList.add(new User("775-190-5582", "Joelynn", "Cuthill", "Aquamarine"));
//        userList.add(new User("560-776-4501", "Wakefield", "Duckhouse", "Mauv"));
//        userList.add(new User("298-725-9105", "Rolland", "Mersey", "Fuscia"));
//        userList.add(new User("663-263-7409", "Staford", "Ockland", "Violet"));
//        userList.add(new User("745-809-8601", "Callida", "Twitchings", "Khaki"));
//        userList.add(new User("363-279-2491", "Kelcie", "Secrett", "Purple"));
//        userList.add(new User("345-387-9589", "Fernande", "De Cruz", "Red"));
//        userList.add(new User("146-384-3573", "Sanderson", "Piper", "Teal"));
//        userList.add(new User("309-946-3150", "Kip", "Dawid", "Blue"));
//        userList.add(new User("797-634-1875", "Feodora", "Hewkin", "Purple"));
    }

    private static void initGamesMapCache() {
        gamesMap = new HashMap<>();

        List<BoardGame> games = getGamesList();
        for (BoardGame game : games) {
            gamesMap.put(game.getBggId(), game);
        }
    }

    private static void initUPCCache() {
        upcMappings = new HashMap<>();

        upcMappings.put("029877030712", "13");
        upcMappings.put("029877030729", "13");
        upcMappings.put("600074120835", "13");
        upcMappings.put("520295188448", "13");
        upcMappings.put("027084645194", "74");
        upcMappings.put("746775321543", "74");
        upcMappings.put("630509453436", "181");
        upcMappings.put("653569566069", "181");
        upcMappings.put("5010993312269", "181");
        upcMappings.put("653569973720", "320");
        upcMappings.put("032244040245", "320");
        upcMappings.put("032244040344", "320");
        upcMappings.put("784311715982", "320");
        upcMappings.put("021853004007", "811");
        upcMappings.put("021853004076", "811");
        upcMappings.put("021853014082", "811");
        upcMappings.put("021853014105", "811");
        upcMappings.put("783318511887", "811");
        upcMappings.put("746775054540", "1258");
        upcMappings.put("887961437225", "1258");
        upcMappings.put("604945379537", "1258");
        upcMappings.put("653569308584", "1294");
        upcMappings.put("630509477722", "1294");
        upcMappings.put("073000000097", "1406");
        upcMappings.put("073000000103", "1406");
        upcMappings.put("078206010017", "2223");
        upcMappings.put("074299419409", "2223");
        upcMappings.put("78206020016", "2223");
        upcMappings.put("352610800274", "2375");
        upcMappings.put("035261080027", "2375");
        upcMappings.put("824968717912", "9209");
        upcMappings.put("681706711003", "30549");
        upcMappings.put("032057614404", "30549");
        upcMappings.put("794965251583", "30549");
        upcMappings.put("700304045973", "46213");
        upcMappings.put("700304043542", "46213");
        upcMappings.put("787799688953", "46213");
//        upcMappings.put("8032611691003", "76085");
        upcMappings.put("722301926246", "131357");
        upcMappings.put("792273251066", "131357");
        upcMappings.put("717356247040", "178900");
        upcMappings.put("046139085174", "178900");
        upcMappings.put("704679648203", "178900");
        upcMappings.put("8594156310394", "178900");
        upcMappings.put("8594156310318", "178900");
        upcMappings.put("884925011316", "188834");
        upcMappings.put("711746875073", "188834");
        upcMappings.put("675905281368", "188834");
        upcMappings.put("0675905281368", "188834");
        upcMappings.put("859145631040", "224037");
        upcMappings.put("702874124201", "224037");
        upcMappings.put("8594156310400", "224037");
        upcMappings.put("826956400202", "23082");
        upcMappings.put("826956600107", "23082");
        upcMappings.put("604945379711", "23082");
        upcMappings.put("766214814955", "266192");
        upcMappings.put("644216627721", "266192");
        upcMappings.put("653341029102", "266192");
//        upcMappings.put("653341029102", "276182");
        upcMappings.put("889698605014", "358816");
//        upcMappings.put("653341029102", "311475");
        upcMappings.put("778988704691", "194655");
//        upcMappings.put("653341029102", "333481");
//        upcMappings.put("653341029102", "332290");
//        upcMappings.put("653341029102", "3955");
        upcMappings.put("850003498027", "266524");
        upcMappings.put("892884000821", "155703");
//        upcMappings.put("653341029102", "241724");
        upcMappings.put("696859265808", "167791");
//        upcMappings.put("653341029102", "184267");
//        upcMappings.put("653341029102", "10547");
//        upcMappings.put("653341029102", "144344");
        upcMappings.put("852131006303", "312786");
        upcMappings.put("841333102067", "205398");
        upcMappings.put("681706781006", "822");
//        upcMappings.put("653341029102", "230802");
        upcMappings.put("655132005616", "256916");
    }

    private static void initGamesCache() {
        gamesList = new ArrayList<>();

        gamesList.add(new BoardGame("188834", "cb1DcPrnkz", "https://cf.geekdo-images.com/rAQ3hIXoH6xDcj41v9iqCg__thumb/img/xA2T7PiwN3Z8pwAksicoCOA1tf0=/fit-in/200x150/filters:strip_icc()/pic5164305.jpg", "https://cf.geekdo-images.com/rAQ3hIXoH6xDcj41v9iqCg__original/img/7oKwNUYakx3-vHUBLHWVuFNKfl4=/0x0/filters:format(jpeg)/pic5164305.jpg", "Secret Hitler", "Secret Hitler is a dramatic game of political intrigue and betrayal set in 1930s Germany. Each player is randomly and secretly assigned to be a liberal or a fascist, and one player is Secret Hitler. The fascists coordinate to sow distrust and install their cold-blooded leader; the liberals must find and stop the Secret Hitler before it's too late. The liberal team always has a majority.&#10;&#10;At the beginning of the game, players close their eyes, and the fascists reveal themselves to one another. Secret Hitler keeps his eyes closed, but puts his thumb up so the fascists can see who he is. The fascists learn who Hitler is, but Hitler doesn't know who his fellow fascists are, and the liberals don't know who anyone is.&#10;&#10;Each round, players elect a President and a Chancellor who will work together to enact a law from a random deck. If the government passes a fascist law, players must try to figure out if they were betrayed or simply unlucky. Secret Hitler also features government powers that come into play as fascism advances. The fascists will use those powers to create chaos unless liberals can pull the nation back from the brink of war.&#10;&#10;The objective of the liberal team is to pass five liberal policies or assassinate Secret Hitler. The objective of the fascist team is to pass six fascist policies or elect Secret Hitler chancellor after three fascist policies have passed.&#10;&#10;", 2016, 5, 10, 45, 45, 45, 13, "Secret Hitler", "Mike Boxleiter", "http://secrethitler.com/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "http://secrethitler.com/assets/Secret_Hitler_Rules.pdf", null));
        gamesList.add(new BoardGame("2375", "xxXmJiNj5e", "https://cf.geekdo-images.com/VosGBqkOjkhgC2QFS1o3_g__thumb/img/PNwWrQIQNo757BtoW6Hniy3P-cw=/fit-in/200x150/filters:strip_icc()/pic212893.jpg", "https://cf.geekdo-images.com/VosGBqkOjkhgC2QFS1o3_g__original/img/ZIc1p75HIEOIwtKy9ZIHsP2l0zQ=/0x0/filters:format(jpeg)/pic212893.jpg", "Sequence", "Sequence is a board and card game. The board shows all the cards (except for the Jacks) of two (2) standard 52-card decks, laid in a 10 x 10 pattern. The four corners are free spaces and count for all players equally.&#10;&#10;The players compete to create rows, columns or diagonals of 5 connected checkers placed on the cards that the player has laid down. Two-eyed Jacks are wild, while one-eyed Jacks allow an opponent's checker to be removed. The game ends when someone has reached a specified number of connections.&#10;&#10;", 1982, 2, 12, 30, 10, 30, 7, "Jax, Ltd.", null, null, "https://www.jaxgames.com/sequence-game-rules/", "035261080027"));
        gamesList.add(new BoardGame("276182", "dWHx1DwGHl", "https://cf.geekdo-images.com/4q_5Ox7oYtK3Ma73iRtfAg__thumb/img/TU4UOoot_zqqUwCEmE_wFnLRRCY=/fit-in/200x150/filters:strip_icc()/pic4650725.jpg", "https://cf.geekdo-images.com/4q_5Ox7oYtK3Ma73iRtfAg__original/img/67KL6z6m8eD2MprJ7JfsxXoAPlM=/0x0/filters:format(jpeg)/pic4650725.jpg", "Dead Reckoning", "Dead Reckoning is a game of exploration, piracy, and influence based in a Caribbean-esque setting. Each player commands a ship and crew and seeks to amass the greatest fortune. They do this through pirating, trading, treasure hunting, and (importantly) capturing and maintaining control over the uninhabited but resource-rich islands of the region. During the game, you can:&#10;&#10;&bull; Customize your ship: Your ship is represented by a token on the board. The board starts mostly unexplored and will be revealed as you venture into uncharted waters. You also have a ship board where you load cargo and treasure, and you can customize the guns, speed, or holding space of your ship.&#10;&#10;&bull; Card-craft your crew: You have a small deck of cards that will drive your actions in the game, with each card representing one of your crew members. This deck functions like one in a deck-building game, but the cards in the deck are sleeved, and rather than add new crew cards to your deck, you improve the skill and abilities of your crew cards by placing transparent &quot;advancement&quot; cards in those sleeves. Aside from the transparent advancements, your crew will also &quot;level up&quot; naturally during the game using a new card-leveling mechanism not seen in other card-crafting games such as Mystic Vale.&#10;&#10;&bull; Control the region: The region is filled with many deserted islands. These islands are a major source of treasure, and players will battle for control of these islands.&#10;&#10;&bull; Battle via a dynamic cube-tower: You can battle other players' ships or NPC merchant ships, and these battles are resolved via a new take on what a cube tower can be, with crew cards and ship powers increasing your chances of victory.&#10;&#10;&bull; Uncover secrets of the sea: Expansions for Dead Reckoning use a &quot;saga&quot; system in which certain content remains hidden and is discovered and added to the game organically only via playing. Rather than add everything at once, you gradually add it by playing and discovering. Depending on luck and player choice, less or more new content may get added each game.&#10;&#10;&mdash;description from the publisher&#10;&#10;", 2022, 1, 4, 150, 90, 150, 14, "Alderac Entertainment Group", "John D. Clair", "https://www.kickstarter.com/projects/alderac/dead-reckoning-the-swashbuckling-card-crafting-game-from-aeg?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.alderac.com/wp-content/uploads/2022/02/DeadReckoning_Deckhand_Rulebook_FINAL.pdf", null));
        gamesList.add(new BoardGame("224037", "9e0nNBNyOU", "https://cf.geekdo-images.com/0d1EaMVmAiIBROI2QstFSQ__thumb/img/J-thZd7Pbbh1CE8QHcvEE4w8FBE=/fit-in/200x150/filters:strip_icc()/pic3596681.jpg", "https://cf.geekdo-images.com/0d1EaMVmAiIBROI2QstFSQ__original/img/udyeiYgfDrEymxhfgtqODm0GwwA=/0x0/filters:format(jpeg)/pic3596681.jpg", "Codenames: Duet", "Codenames Duet keeps the basic elements of Codenames &mdash; give one-word clues to try to get someone to identify your agents among those on the table &mdash; but now you're working together as a team to find all of your agents. (Why you don't already know who your agents are is a question that Congressional investigators will get on your back about later!)&#10;&#10;To set up play, lay out 25 word cards in a 5&times;5 grid. Place a key card in the holder so that each player sees one side of the card. Each player sees a 5&times;5 grid on the card, with nine of the squares colored green (representing your agents) and three squares colored black (representing assassins). Three of the nine squares on each side are also green on the other side, one assassin is black on both sides, one is green on the other side and the other is an innocent bystander on the other side.&#10;&#10;Collectively, you need to reveal all fifteen agents &mdash; without revealing an assassin &mdash; before time runs out in order to win the game. Either player can decide to give the first one-word clue to the other player, along with a number. Whoever receives the clue places a finger on a card to identify that agent. If correct, they can attempt to identify another one. If they identify a bystander, then their guessing time ends. If they identify an assassin, you both lose! Unlike regular Codenames, they can keep guessing as long as they keep identifying an agent each time; this is useful for going back to previous clues and finding ones they missed earlier. After the first clue is given, players alternate giving clues.&#10;&#10;", 2017, 2, 2, 30, 15, 30, 11, "Czech Games Edition", "Vlaada Chvátil", "https://czechgames.com/en/codenames-duet/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", null, null));
        gamesList.add(new BoardGame("358816", "l3YvMDBUMf", "https://cf.geekdo-images.com/GyMVRe5N2fqzW20820Wgkg__thumb/img/H5bueSSZe4IwDJ-MrGg8BiMXG6I=/fit-in/200x150/filters:strip_icc()/pic6743744.png", "https://cf.geekdo-images.com/GyMVRe5N2fqzW20820Wgkg__original/img/Oj6aI5k1sXBP4k4n4GnrXGtVQfo=/0x0/filters:format(png)/pic6743744.png", "Rear Window", "Experience Alfred Hitchcock's masterpiece film Rear Window in a game of deduction and suspense. Carefully observe strange clues and ominous patterns in the things going on in the apartments across the way. There are parties, knives, a saw, bickering, laughing, music...and a mysterious trunk. Do you detect a murder? Or is the secret, private world of the neighbors planting frightening ideas in your mind?&#10;&#10;In Rear Window, one player takes the role of director Alfred Hitchcock &mdash; the &quot;Master of Suspense&quot; &mdash; and communicates via building windows clues and signs for the other players without ever uttering a word, ideally giving them enough to go on that they can figure out who the murderer is &mdash; or whether a murder even took place.&#10;&#10;If a murderer is out there, you need to nail down all eight attributes of that person by the end of four rounds without them catching on to what you see and know.&#10;&#10;", 2022, 3, 5, 40, 40, 40, 13, "Funko", "Prospero Hall", null, null, "889698605014"));
        gamesList.add(new BoardGame("311475", "2WfoCjNxqx", "https://cf.geekdo-images.com/fOOSe4ToH3zg4Y9KxsWQZw__thumb/img/A9el7cgyOgEuy-5gSCpmo-nKafI=/fit-in/200x150/filters:strip_icc()/pic6437594.png", "https://cf.geekdo-images.com/fOOSe4ToH3zg4Y9KxsWQZw__original/img/utEpUCcJm-_y9wGUTz-1u6Gymu0=/0x0/filters:format(png)/pic6437594.png", "Masters of the Universe: The Board Game – Clash for Eternia", "When Eternia is threatened by Skeletor and his vile minions, the noble Prince Adam holds aloft the Sword of Power and proclaims, &quot;By the Power of Grayskull, I have the power!&quot;, transforming into He-Man!&#10;&#10;Masters of the Universe: The Board Game - Clash for Eternia is a competitive one vs many or fully cooperative action-driven board game. Choose to play as the Heroic or Evil Warriors, use unique abilities on the battlefield, upgrade skills, and by the power of Grayskull, win the fight for Eternia!&#10;&#10;Players use dice rolling and character stats to move over a hex grid board and control Castle Greyskull.&#10;&#10;", 2022, 1, 5, 0, 60, 0, 14, "CMON", "Leo Almeida", "https://www.kickstarter.com/projects/cmon/masters-of-the-universe-the-board-game-clash-for-eternia?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", null, ""));
        gamesList.add(new BoardGame("1258", "hQLfeykeLS", "https://cf.geekdo-images.com/z6tNBDWK-fmZB6dHGML3Gg__thumb/img/j_78tIWNelVnffQS8nQi-AEf7kk=/fit-in/200x150/filters:strip_icc()/pic6685757.jpg", "https://cf.geekdo-images.com/z6tNBDWK-fmZB6dHGML3Gg__original/img/D4RHkuoqJFWClN2LJ-Rer8kRz6E=/0x0/filters:format(jpeg)/pic6685757.jpg", "Phase 10", "A rummy-type card game where players compete to be the first to finish completing all ten phases.  Phases include collecting runs of numbers, collecting certain number of a given color cards, etc.  The first player to finish completing the 10th phase wins.  In case of ties, the player with the fewest number of points wins.&#10;&#10;", 1982, 2, 6, 45, 45, 45, 8, "FUNDEX", "Kenneth Johnson", null, null, null));
        gamesList.add(new BoardGame("194655", "VibNUMwsqr", "https://cf.geekdo-images.com/665Ply7Ho1WVf1v1iZlWeg__thumb/img/BKnSb6kAhpVFHV8cyUXrs3EF_dw=/fit-in/200x150/filters:strip_icc()/pic3283110.png", "https://cf.geekdo-images.com/665Ply7Ho1WVf1v1iZlWeg__original/img/4q6RCxd7st-yW2MyrJwVC_d3ajk=/0x0/filters:format(png)/pic3283110.png", "Santorini", "Santorini is a re-imagining of the purely abstract 2004 edition. Since its original inception over 30 years ago, Santorini has been continually developed, enhanced and refined by designer Gordon Hamilton.&#10;&#10;Santorini is an accessible strategy game, simple enough for an elementary school classroom while aiming to provide gameplay depth and content for hardcore gamers to explore, The rules are simple. Each turn consists of 2 steps:&#10;&#10;1. Move - move one of your builders into a neighboring space. You may move your Builder Pawn on the same level, step-up one level, or step down any number of levels.&#10;&#10;2. Build - Then construct a building level adjacent to the builder you moved.  When building on top of the third level, place a dome instead, removing that space from play.&#10;&#10;Winning the game - If either of your builders reaches the third level, you win.&#10;&#10;Variable player powers - Santorini features variable player powers layered over an otherwise abstract game, with 40 thematic god and hero powers that fundamentally change the way the game is played.&#10;&#10;", 2016, 2, 4, 20, 20, 20, 8, "Roxley", "Gord!", "https://roxley.com/products/santorini?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "http://files.roxley.com/Santorini-Rulebook-Web-2016.08.14.pdf", "778988704691"));
        gamesList.add(new BoardGame("2223", "Ad9NDUFxmt", "https://cf.geekdo-images.com/SU-OL7XWn7BOiSYevyTThw__thumb/img/5dxRomuxNxzw01ZYNnIK-f_Ai4o=/fit-in/200x150/filters:strip_icc()/pic981505.jpg", "https://cf.geekdo-images.com/SU-OL7XWn7BOiSYevyTThw__original/img/JeM6Gys8GmsTTRXcukfk92YtIcA=/0x0/filters:format(jpeg)/pic981505.jpg", "UNO", "Players race to empty their hands and catch opposing players with cards left in theirs, which score points. In turns, players attempt to play a card by matching its color, number, or word to the topmost card on the discard pile. If unable to play, players draw a card from the draw pile, and if still unable to play, they pass their turn. Wild and special cards spice things up a bit.&#10;&#10;UNO is a commercial version of Crazy Eights, a public domain card game played with a standard deck of playing cards.&#10;&#10;This entry includes all themed versions of UNO that do not include new cards.&#10;&#10;", 1971, 2, 10, 30, 30, 30, 6, "Mattel", "Merle Robbins", "https://www.mattelgames.com/en-us/cards/uno?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://service.mattel.com/instruction_sheets/42001pr.pdf", null));
        gamesList.add(new BoardGame("333481", "lqa7wCDrd7", "https://cf.geekdo-images.com/HDLq-Crvcr9ipuw99csZNg__thumb/img/_cg_Xke5LI-pyw7jdnV94zkJzgQ=/fit-in/200x150/filters:strip_icc()/pic6446489.png", "https://cf.geekdo-images.com/HDLq-Crvcr9ipuw99csZNg__original/img/zLDxZxQKZJaDc6N_yn_7Tm-cxwg=/0x0/filters:format(png)/pic6446489.png", "GigaWatt", "Europe, end of 20th century. Our economy runs entirely on fossil gas and coal-fired power plants. Climate change is a fact. Which region will have sustainable (non-fossil) electricity production first and wins the game?&#10;&#10;Each game round, the electricity demand (in GW) in your region increases due to the fate of the Dice. It's up to you to build power stations to produce the required GW demand. This provides you with income, with which you can eventually close the old fossil power stations and replace them with sustainable power stations that do not emit CO2. You can trade GW surpluses and GW shortages with the other players or External Markets to balance supply and demand.&#10;&#10;So are you going to increase sustainability quickly (with the risk of GW shortages), or are you more careful (at the risk of being late)? The region that is the first to produce more GW than the GW demand and has closed all its fossil power plants, wins. But beware, wind and solar energy sometimes only generate half of their GW power peak.&#10;&#10;&mdash;description from the designer&#10;&#10;", 2022, 1, 6, 120, 60, 120, 12, null, "Milo van Holsteijn", "https://www.kickstarter.com/projects/gigawatt/gigawatt-a-power-play-for-the-future?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", null, ""));
        gamesList.add(new BoardGame("811", "3Qx47A4K8M", "https://cf.geekdo-images.com/LeaLDlTTmeN639MfuflcMw__thumb/img/CSPEyoFAcA3WwtzDekp3Mfepcek=/fit-in/200x150/filters:strip_icc()/pic2286966.jpg", "https://cf.geekdo-images.com/LeaLDlTTmeN639MfuflcMw__original/img/fusGQFpm4srwqhM8CDlEDzh0NyQ=/0x0/filters:format(jpeg)/pic2286966.jpg", "Rummikub", "The game is based on the traditional middle-eastern game of Okey. First created in the 1930s and sold in hand-produced versions until the late 1970s.&#10;&#10;Similar to the Rummy that you play with cards - you try to get rid of all your tiles by forming numbers into runs of 3 tiles or more, or 3 to 4 of a kind. The colors of the numbers on the tiles are like card suits. This game may start rather uneventfully, but when the players start putting more and more tiles in play, the options for your upcoming turns can become more complex, challenging, and exciting (from areyougame.com).&#10;&#10;", 1977, 2, 4, 60, 60, 60, 8, "Pressman Toy Corp.", "Ephraim Hertzano", "http://www.rummikub.com?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://rummikub.com/wp-content/uploads/2019/12/2600-English-1.pdf", "021853004007"));
        gamesList.add(new BoardGame("30549", "6FmFeux5xH", "https://cf.geekdo-images.com/S3ybV1LAp-8SnHIXLLjVqA__thumb/img/oqViRj6nVxK3m36NluTxU1PZkrk=/fit-in/200x150/filters:strip_icc()/pic1534148.jpg", "https://cf.geekdo-images.com/S3ybV1LAp-8SnHIXLLjVqA__original/img/IsrvRLpUV1TEyZsO5rC-btXaPz0=/0x0/filters:format(jpeg)/pic1534148.jpg", "Pandemic", "In Pandemic, several virulent diseases have broken out simultaneously all over the world! The players are disease-fighting specialists whose mission is to treat disease hotspots while researching cures for each of four plagues before they get out of hand.&#10;&#10;The game board depicts several major population centers on Earth. On each turn, a player can use up to four actions to travel between cities, treat infected populaces, discover a cure, or build a research station. A deck of cards provides the players with these abilities, but sprinkled throughout this deck are Epidemic! cards that accelerate and intensify the diseases' activity. A second, separate deck of cards controls the &quot;normal&quot; spread of the infections.&#10;&#10;Taking a unique role within the team, players must plan their strategy to mesh with their specialists' strengths in order to conquer the diseases. For example, the Operations Expert can build research stations which are needed to find cures for the diseases and which allow for greater mobility between cities; the Scientist needs only four cards of a particular disease to cure it instead of the normal five&mdash;but the diseases are spreading quickly and time is running out. If one or more diseases spreads beyond recovery or if too much time elapses, the players all lose. If they cure the four diseases, they all win!&#10;&#10;The 2013 edition of Pandemic includes two new characters&mdash;the Contingency Planner and the Quarantine Specialist&mdash;not available in earlier editions of the game.&#10;&#10;Pandemic is the first game in the Pandemic series.&#10;&#10;", 2008, 2, 4, 45, 45, 45, 8, "Z-Man Games, Inc.", "Matt Leacock", "https://www.zmangames.com/en/products/pandemic?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://images-cdn.zmangames.com/us-east-1/filer_public/25/12/251252dd-1338-4f78-b90d-afe073c72363/zm7101_pandemic_rules.pdf", "841333106874"));
        gamesList.add(new BoardGame("178900", "GP7Y2xOUzj", "https://cf.geekdo-images.com/F_KDEu0GjdClml8N7c8Imw__thumb/img/yl8iXxSNwguMeg3KkmfFO9SMVVc=/fit-in/200x150/filters:strip_icc()/pic2582929.jpg", "https://cf.geekdo-images.com/F_KDEu0GjdClml8N7c8Imw__original/img/gcX_EfjsRpB5fI4Ug4XV73G4jGI=/0x0/filters:format(jpeg)/pic2582929.jpg", "Codenames", "Codenames is an easy party game to solve puzzles. &#10;The game is divided into red and blue, each side has a team leader, the team leader's goal is to lead their team to the final victory. &#10;At the beginning of the game, there will be 25 cards on the table with different words. Each card has a corresponding position, representing different colors. &#10;Only the team leader can see the color of the card. The team leader should prompt according to the words, let his team members find out the cards of their corresponding colors, and find out all the cards of their own colors to win.&#10;&#10;", 2015, 2, 8, 15, 15, 15, 14, "Czech Games Edition", "Vlaada Chvátil", "https://czechgames.com/en/codenames/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://czechgames.com/files/rules/codenames-rules-en.pdf", "8594156310462"));
        gamesList.add(new BoardGame("332290", "86z8YMWVTv", "https://cf.geekdo-images.com/X1oNtZV5H5BxtxlsTGIsHA__thumb/img/wvQPqF45KlfnRB-Z4ESK9VXzZMM=/fit-in/200x150/filters:strip_icc()/pic6037615.png", "https://cf.geekdo-images.com/X1oNtZV5H5BxtxlsTGIsHA__original/img/xkYA7A3vJLA2wqkaRorXcHgjQDk=/0x0/filters:format(png)/pic6037615.png", "Stardew Valley: The Board Game", "A cooperative board game of farming and friendship based on the Stardew Valley video game by Eric Barone. Work together with your fellow farmers to save the Valley from the nefarious JojaMart Corporation! To do this, you'll need to farm, fish, friend and find all kinds of different resources to fulfill your Grandpa's Goals and restore the Community Center. Collect all kinds of items, raise animals, and explore the Mine. Gain powerful upgrades and skills and as the seasons pass see if you're able to protect the magic of Stardew Valley!&#10;&#10;The goal of the game is to complete Grandpa's Goals and restore the Community Center, which requires you to gather different types of resources represented by tiles. You have a fixed amount of turns to accomplish this. This is driven by the Season Deck of 20 cards, one of which is drawn each turn to trigger certain events. Cooperatively the players decide each turn where they will focus their individual actions and place their pawn in that part of the Valley. Using their actions, they visit specific locations, trying to gather resources to complete their collective goals. Actions include things like: watering crops, trying to catch fish, rolling dice to explore the mines, and many more. When the Season Deck is exhausted, the game ends.&#10;&#10;", 2021, 1, 4, 200, 45, 200, 13, "ConcernedApe", "Eric Barone", "https://www.stardewvalley.net/stardew-valley-the-board-game-available-now/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.dropbox.com/s/n3x4p8nw2yr1a3j/Stardew%20Rules%20for%20Download.pdf?dl=0", null));
        gamesList.add(new BoardGame("74", "sOC4PMWhs0", "https://cf.geekdo-images.com/S5GzB_f2Re3kEDoSxqG5Ew__thumb/img/kJ1JQ_d9xEZ00sJ1dLvwyQYiQUA=/fit-in/200x150/filters:strip_icc()/pic213515.jpg", "https://cf.geekdo-images.com/S5GzB_f2Re3kEDoSxqG5Ew__original/img/K49MN0Ij_tZOgDK4znCUJGu6aU4=/0x0/filters:format(jpeg)/pic213515.jpg", "Apples to Apples", "The party game Apples to Apples consists of two decks of cards: Things and Descriptions. Each round, the active player draws a Description card (which features an adjective like &quot;Hairy&quot; or &quot;Smarmy&quot;) from the deck, then the other players each secretly choose the Thing card in hand that best matches that description and plays it face-down on the table. The active player then reveals these cards and chooses the Thing card that, in his opinion, best matches the Description card, which he awards to whoever played that Thing card. This player becomes the new active player for the next round.&#10;&#10;Once a player has won a pre-determined number of Description cards, that player wins.&#10;&#10;Note: &quot;Party Box&quot; editions include all cards from Apples to Apples: Expansion Set #1 and Apples to Apples: Expansion Set #2&#10;&#10;", 1999, 4, 10, 30, 30, 30, 12, "Mattel", "Matthew Kirby", "http://www.applestoapplesgame.com/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://service.mattel.com/instruction_sheets/N1384-0920.pdf", "746775321543"));
        gamesList.add(new BoardGame("3955", "AaRXSVtjLn", "https://cf.geekdo-images.com/_04xA-d-rfygm0c49HE4vA__thumb/img/P-6cf4TEk7ewzrfP9d-8c-TxAY4=/fit-in/200x150/filters:strip_icc()/pic1170986.jpg", "https://cf.geekdo-images.com/_04xA-d-rfygm0c49HE4vA__original/img/el-4Ilu9DeYunRT3r4TVP8Vy3uk=/0x0/filters:format(jpeg)/pic1170986.jpg", "BANG!", "&quot;The Outlaws hunt the Sheriff. The Sheriff hunts the Outlaws. The Renegade plots secretly, ready to take one side or the other. Bullets fly. Who among the gunmen is a Deputy, ready to sacrifice himself for the Sheriff? And who is a merciless Outlaw, willing to kill him? If you want to find out, just draw (your cards)!&quot; (From back of box)&#10;&#10;The card game BANG! recreates an old-fashioned spaghetti western shoot-out, with each player randomly receiving a Character card to determine special abilities, and a secret Role card to determine their goal.&#10;&#10;Four different Roles are available, each with a unique victory condition:&#10;&#10;     Sheriff - Kill all Outlaws and the Renegade&#10;     Deputy - Protect the Sheriff and kill any Outlaws&#10;     Outlaw - Kill the Sheriff&#10;     Renegade - Be the last person standing&#10;&#10;&#10;A player's Role is kept secret, except for the Sheriff. Character cards are placed face up on table, and also track strength (hand limit) in addition to special ability.&#10;&#10;There are 22 different types of cards in the draw deck. Most common are the BANG! cards, which let you shoot at another player, assuming the target is within &quot;range&quot; of your current gun.  The target player can play a &quot;MISSED!&quot; card to dodge the shot.  Other cards can provide temporary boosts while in play (for example, different guns to improve your firing range) and special one-time effects to help you or hinder your opponents (such as Beer to restore health, or Barrels to hide behind during a shootout). A horse is useful for keeping your distance from unruly neighbors, while the Winchester can hit a target at range 5. The Gatling is a deadly exception where range doesn't matter: it can only be used once, but targets all other players at the table!&#10;&#10;Information on the cards is displayed using language-independent symbols, and 7 summary/reference cards are included.&#10;&#10;", 2002, 4, 7, 40, 20, 40, 10, "dV Giochi", "Emiliano Sciarra", "https://www.dvgiochi.com/gioco.cfm?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.dvgiochi.com/giochi/bang/download/Bang_rules_ENG.pdf", null));
        gamesList.add(new BoardGame("266524", "ApSXoXA1Gs", "https://cf.geekdo-images.com/mF2cSNRk2O6HtE45Sl9TcA__thumb/img/K2AgL-KE_CTcvwahWd7zlt9YR0U=/fit-in/200x150/filters:strip_icc()/pic4852372.jpg", "https://cf.geekdo-images.com/mF2cSNRk2O6HtE45Sl9TcA__original/img/_KNgPoC_4l7iN0ntbFILexOJpfc=/0x0/filters:format(jpeg)/pic4852372.jpg", "PARKS", "PARKS is a celebration of the US National Parks featuring illustrious art from Fifty-Nine Parks.&#10;&#10;In PARKS, players will take on the role of two hikers as they trek through different trails across four seasons of the year. While on the trail, these hikers will take actions and collect memories of the places your hikers visit. These memories are represented by various resource tokens like mountains and forests. Collecting these memories in sets will allow players to trade them in to visit a National Park at the end of each hike.&#10;&#10;Each trail represents one season of the year, and each season, the trails will change and grow steadily longer. The trails, represented by tiles, get shuffled in between each season and laid out anew for the next round. Resources can be tough to come by especially when someone is at the place you&rsquo;re trying to reach! Campfires allow you to share a space and time with other hikers. Canteens and Gear can also be used to improve your access to resources through the game. It&rsquo;ll be tough to manage building up your engine versus spending resources on parks, but we bet you&rsquo;re up to the challenge. Welcome to PARKS!&#10;&#10;&mdash;description from the publisher&#10;&#10;", 2019, 1, 5, 60, 30, 60, 10, "Keymaster Games", "Henry Audubon", "http://keymastergames.com/parks?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://cdn.shopify.com/s/files/1/1898/8501/files/Rules-NEWLOGO-Small.pdf", "850003498027"));
        gamesList.add(new BoardGame("13", "OIXt3DmJU0", "https://cf.geekdo-images.com/W3Bsga_uLP9kO91gZ7H8yw__thumb/img/8a9HeqFydO7Uun_le9bXWPnidcA=/fit-in/200x150/filters:strip_icc()/pic2419375.jpg", "https://cf.geekdo-images.com/W3Bsga_uLP9kO91gZ7H8yw__original/img/xV7oisd3RQ8R-k18cdWAYthHXsA=/0x0/filters:format(jpeg)/pic2419375.jpg", "Catan", "In CATAN (formerly The Settlers of Catan), players try to be the dominant force on the island of Catan by building settlements, cities, and roads. On each turn dice are rolled to determine what resources the island produces. Players build by spending resources (sheep, wheat, wood, brick and ore) that are depicted by these resource cards; each land type, with the exception of the unproductive desert, produces a specific resource: hills produce brick, forests produce wood, mountains produce ore, fields produce wheat, and pastures produce sheep.&#10;&#10;Setup includes randomly placing large hexagonal tiles (each showing a resource or the desert) in a honeycomb shape and surrounding them with water tiles, some of which contain ports of exchange. Number disks, which will correspond to die rolls (two 6-sided dice are used), are placed on each resource tile. Each player is given two settlements (think: houses) and roads (sticks) which are, in turn, placed on intersections and borders of the resource tiles. Players collect a hand of resource cards based on which hex tiles their last-placed house is adjacent to. A robber pawn is placed on the desert tile.&#10;&#10;A turn consists of possibly playing a development card, rolling the dice, everyone (perhaps) collecting resource cards based on the roll and position of houses (or upgraded cities&mdash;think: hotels) unless a 7 is rolled, turning in resource cards (if possible and desired) for improvements, trading cards at a port, and trading resource cards with other players. If a 7 is rolled, the active player moves the robber to a new hex tile and steals resource cards from other players who have built structures adjacent to that tile.&#10;&#10;Points are accumulated by building settlements and cities, having the longest road and the largest army (from some of the development cards), and gathering certain development cards that simply award victory points. When a player has gathered 10 points (some of which may be held in secret), he announces his total and claims the win.&#10;&#10;CATAN has won multiple awards and is one of the most popular games in recent history due to its amazing ability to appeal to experienced gamers as well as those new to the hobby.&#10;&#10;Die Siedler von Catan was originally published by KOSMOS and has gone through multiple editions. It was licensed by Mayfair and has undergone four editions as The Settlers of Catan. In 2015, it was formally renamed CATAN to better represent itself as the core and base game of the CATAN series. It has been re-published in two travel editions, portable edition and compact edition, as a special gallery edition (replaced in 2009 with a family edition), as an anniversary wooden edition, as a deluxe 3D collector's edition, in the basic Simply Catan, as a beginner version, and with an entirely new theme in Japan and Asia as Settlers of Catan: Rockman Edition. Numerous spin-offs and expansions have also been made for the game.&#10;&#10;", 1995, 3, 4, 120, 60, 120, 10, "KOSMOS", "Klaus Teuber", "https://www.catan.com/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.catan.com/sites/prod/files/2021-06/catan_base_rules_2020_200707.pdf", "029877030712"));
        gamesList.add(new BoardGame("46213", "cyscZjjlse", "https://cf.geekdo-images.com/oc3u6OQpJkf9TvDw4iI9xA__thumb/img/Otc-6MFf-HC9ep-82ub2B2rID78=/fit-in/200x150/filters:strip_icc()/pic4991936.jpg", "https://cf.geekdo-images.com/oc3u6OQpJkf9TvDw4iI9xA__original/img/Hh2zbSnLEARMlNg0OzImf2-5FIw=/0x0/filters:format(jpeg)/pic4991936.jpg", "Telestrations", "From the publisher's press release:&#10;&#10;&quot;Each player begins by sketching a TELESTRATIONS word dictated by the roll of a die. The old fashioned sand timer may limit the amount of time they get to execute their sketch, but it certainly doesn't limit creativity! Time's up! All players, all at the same time, pass their sketch to the next player, who must guess what's been drawn. Players then simultaneously pass their guess -- which hopefully matches the original word (or does it??) -- to the next player who must try to draw the word they see -- and so on.&quot;&#10;&quot;Telestrations contains eight erasable sketchbooks and markers, a die, a 90 second sand-timer and 2,400 words to choose from.&quot;&#10;Inspired by the public domain game Eat Poop You Cat .&#10;Similar games are Mutabo and  Mini-Mutabo .&#10;&#10;", 2009, 4, 8, 30, 30, 30, 12, "USAopoly", null, "https://theop.games/products/game/telestrations-8-player-the-original/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://theop.games/wp-content/uploads/2019/02/Telestrations_8-Player_edition.pdf", "700304043542"));
        gamesList.add(new BoardGame("131357", "19C9ka2hEx", "https://cf.geekdo-images.com/MWhSY_GOe2-bmlQ2rntSVg__thumb/img/vuR_0PCX1w2EkjO_LbchOHZPOwU=/fit-in/200x150/filters:strip_icc()/pic2016054.jpg", "https://cf.geekdo-images.com/MWhSY_GOe2-bmlQ2rntSVg__original/img/ayAAWBK1rAEumARNmROsOtvqW-4=/0x0/filters:format(jpeg)/pic2016054.jpg", "Coup", "You are head of a family in an Italian city-state, a city run by a weak and corrupt court. You need to manipulate, bluff and bribe your way to power. Your object is to destroy the influence of all the other families, forcing them into exile. Only one family will survive...&#10;&#10;In Coup, you want to be the last player with influence in the game, with influence being represented by face-down character cards in your playing area.&#10;&#10;Each player starts the game with two coins and two influence &ndash; i.e., two face-down character cards; the fifteen card deck consists of three copies of five different characters, each with a unique set of powers:&#10;&#10;&#10;     Duke: Take three coins from the treasury. Block someone from taking foreign aid.&#10;     Assassin: Pay three coins and try to assassinate another player's character.&#10;     Contessa: Block an assassination attempt against yourself.&#10;     Captain: Take two coins from another player, or block someone from stealing coins from you.&#10;     Ambassador: Draw two character cards from the Court (the deck), choose which (if any) to exchange with your face-down characters, then return two. Block someone from stealing coins from you.&#10;&#10;&#10;On your turn, you can take any of the actions listed above, regardless of which characters you actually have in front of you, or you can take one of three other actions:&#10;&#10;&#10;     Income: Take one coin from the treasury.&#10;     Foreign aid: Take two coins from the treasury.&#10;     Coup: Pay seven coins and launch a coup against an opponent, forcing that player to lose an influence. (If you have ten coins or more, you must take this action.)&#10;&#10;&#10;When you take one of the character actions &ndash; whether actively on your turn, or defensively in response to someone else's action &ndash; that character's action automatically succeeds unless an opponent challenges you. In this case, if you can't (or don't) reveal the appropriate character, you lose an influence, turning one of your characters face-up. Face-up characters cannot be used, and if both of your characters are face-up, you're out of the game.&#10;&#10;If you do have the character in question and choose to reveal it, the opponent loses an influence, then you shuffle that character into the deck and draw a new one, perhaps getting the same character again and perhaps not.&#10;&#10;The last player to still have influence &ndash; that is, a face-down character &ndash; wins the game!&#10;&#10;A new &amp; optional character called the Inquisitor has been added (currently, the only English edition with the Inquisitor included is the Kickstarter Version from Indie Boards &amp; Cards. Copies in stores may not be the Kickstarter versions and may only be the base game). The Inquisitor character cards may be used to replace the Ambassador cards.&#10;&#10;&#10;     Inquisitor: Draw one character card from the Court deck and choose whether or not to exchange it with one of your face-down characters. OR Force an opponent to show you one of their character cards (their choice which). If you wish it, you may then force them to draw a new card from the Court deck. They then shuffle the old card into the Court deck. Block someone from stealing coins from you.&#10;&#10;&#10;", 2012, 2, 6, 15, 15, 15, 13, "Indie Boards & Cards", "Rikki Tahta", "http://www.indieboardsandcards.com/index.php/games/coup/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", null, null));
        gamesList.add(new BoardGame("155703", "AayCJcD6rT", "https://cf.geekdo-images.com/wo_EYno7S66psGPMl_JtiQ__thumb/img/_30uXRkyUuk9vMeeedlSqQ3oJwA=/fit-in/200x150/filters:strip_icc()/pic3543505.jpg", "https://cf.geekdo-images.com/wo_EYno7S66psGPMl_JtiQ__original/img/buKqUQr48Cb41noIhoaAJzWBLQA=/0x0/filters:format(jpeg)/pic3543505.jpg", "Evolution", "In Evolution, players adapt their species in a dynamic ecosystem where food is scarce and predators lurk.  Traits like Hard Shell and Horns will protect your species from Carnivores, while a Long Neck will help them get food that others cannot reach. With over 4,000 ways to evolve your species, every game becomes a different adventure.&#10;&#10;Evolution packs a surprising amount of variety for a game with simple rules.  The variety comes from the synergies between the trait cards and from the different personalities at the table.  Some players thrive on creating Carnivores to wreak havoc on their fellow players.  Others prefer to stay protected and mind their own business.  Evolution encourages both play styles by giving each of them multiple paths to victory.  And it is the mix of play styles at the table that ultimately determines the ecosystem in which the players are adapting.  So gather your friends and see who can best adapt to the changing world around them.&#10;&#10;&quot;The theme of evolution is not just tacked on: it drives play.&quot; - Nature (the world's most prestigious scientific journal)  &#10;Evolution was covered in the journal Nature.  The article was written by Stuart West, a Professor of Evolutionary Biology at the University of Oxford who has used Evolution in his undergraduate class.  &#10;&#10;     https://boardgamegeek.com/thread/1487263/evolution-and-other-evolution-themed-games-nature&#10;     https://boardgamegeek.com/thread/1580710/using-game-evolution-teach-evolution&#10;&#10;&#10;", 2014, 2, 6, 60, 60, 60, 12, "North Star Games", "Dmitry Knorre", "https://www.northstargames.com/collections/strategy-games/products/evolution?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://cdn.shopify.com/s/files/1/0283/4324/files/Evolution-Rules-Web_1.pdf?7044374637391402093", "892884000821"));
        gamesList.add(new BoardGame("181", "EL3YmDLY6W", "https://cf.geekdo-images.com/Oem1TTtSgxOghRFCoyWRPw__thumb/img/5cltSV60oVvjL3Ag_KTJbmTdU6w=/fit-in/200x150/filters:strip_icc()/pic4916782.jpg", "https://cf.geekdo-images.com/Oem1TTtSgxOghRFCoyWRPw__original/img/Nu3eXPyOkhtnR3hhpUrtgqRMAfs=/0x0/filters:format(jpeg)/pic4916782.jpg", "Risk", "Possibly the most popular, mass market war game.  The goal is conquest of the world.&#10;&#10;Each player's turn consists of:&#10;- gaining reinforcements through number of territories held, control of every territory on each continent, and turning sets of bonus cards.&#10;-  Attacking other players using a simple combat rule of comparing the highest dice rolled for each side.  Players may attack as often as desired.  If one enemy territory is successfully taken, the player is awarded with a  bonus card.&#10;-  Moving a group of armies to another adjacent territory.&#10;&#10;", 1959, 2, 6, 120, 120, 120, 10, "Hasbro", "Albert Lamorisse", null, null, "630509426027"));
        gamesList.add(new BoardGame("1294", "WVgc4bUL8N", "https://cf.geekdo-images.com/owlmDA10KegcwPaapY5auw__thumb/img/73FO0H7pORdum5ZexMXyrjLhpVU=/fit-in/200x150/filters:strip_icc()/pic5146918.png", "https://cf.geekdo-images.com/owlmDA10KegcwPaapY5auw__original/img/JJ5Pw1-6M7GUZ7OhFxEybSaryiM=/0x0/filters:format(png)/pic5146918.png", "Clue", "For versions of Clue that feature the new character Dr. Orchid, please use this Clue page.&#10;&#10;The classic detective game!  In Clue, players move from room to room in a mansion to solve the mystery of: who done it, with what, and where?  Players are dealt character, weapon, and location cards after the top card from each card type is secretly placed in the confidential file in the middle of the board.  Players must move to a room and then make an accusation against a character saying they did it in that room with a specific weapon.  The player to the left must show one of any cards accused to the accuser if in that player's hand.  Through deductive reasoning each player must figure out which character, weapon, and location are in the secret file.  To do this, each player must uncover what cards are in other players hands by making more and more accusations.  Once a player knows what cards the other players are holding, they will know what cards are in the secret file.  A great game for those who enjoy reasoning and thinking things out.&#10;&#10;Note: There are some early versions of Clue which are labeled as 2-6 players.&#10;&#10;", 1949, 2, 6, 45, 45, 45, 8, "Hasbro", "Anthony E. Pratt", null, null, "714043011373"));
        gamesList.add(new BoardGame("241724", "9CSmAJHQ2i", "https://cf.geekdo-images.com/rMMSvnXuBPZSihIaVtVT-A__thumb/img/IkNfLIRXNN3cwFLItdj56VnREm0=/fit-in/200x150/filters:strip_icc()/pic4996383.png", "https://cf.geekdo-images.com/rMMSvnXuBPZSihIaVtVT-A__original/img/PivWbEOFSDhHXl2NCHw8xSErORA=/0x0/filters:format(png)/pic4996383.png", "Villagers", "You are the founder of a new village during the middle ages, in the years after a great plague. The loss of so many people has created big problems for the survivors. Many of the people the villagers used to depend on for essential things like food, shelter and clothes are gone. Craftsmen find themselves without suppliers of raw materials, traders have lost their customers and many have lost their farms and workshops as they escaped the plague.&#10;&#10;The roads are full of refugees seeking a new beginning. They come to you, hoping to settle down on your land and make a living. Your grain farm is the ideal starting point for a village, reliably providing food for many people. You must choose wisely who you allow to settle with you, as your food and resources are limited.&#10;&#10;The people on the road have valuable and unique skills, but they all in turn rely on other people with very specific crafts to be able to work. Raw materials, tools and services must be provided by other people from the road.&#10;&#10;If you manage to find people that can work together to make a profit, while increasing your food surplus and capacity for building new houses, your village will be prosperous.&#10;&#10;The game comes with a solo mode where a lone village strives to prosper in spite of the dreaded Countess and her evil machinations.&#10;&#10;&mdash;description from the publisher&#10;&#10;", 2019, 1, 5, 60, 30, 60, 10, "Gigamic", "Haakon Gaarder", "https://www.kickstarter.com/projects/sinisterfish/villagers-card-drafting-and-village-building-for-1?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.dropbox.com/s/tkmu6xmznmv1n9m/rulebook_5_5.pdf?dl=0", null));
        gamesList.add(new BoardGame("167791", "fDn9rQjH9O", "https://cf.geekdo-images.com/wg9oOLcsKvDesSUdZQ4rxw__thumb/img/BTxqxgYay5tHJfVoJ2NF5g43_gA=/fit-in/200x150/filters:strip_icc()/pic3536616.jpg", "https://cf.geekdo-images.com/wg9oOLcsKvDesSUdZQ4rxw__original/img/thIqWDnH9utKuoKVEUqveDixprI=/0x0/filters:format(jpeg)/pic3536616.jpg", "Terraforming Mars", "In the 2400s, mankind begins to terraform the planet Mars. Giant corporations, sponsored by the World Government on Earth, initiate huge projects to raise the temperature, the oxygen level, and the ocean coverage until the environment is habitable. In Terraforming Mars, you play one of those corporations and work together in the terraforming process, but compete for getting victory points that are awarded not only for your contribution to the terraforming, but also for advancing human infrastructure throughout the solar system, and doing other commendable things.&#10;&#10;The players acquire unique project cards (from over two hundred different ones) by buying them to their hand. The projects (cards) can represent anything from introducing plant life or animals, hurling asteroids at the surface, building cities, to mining the moons of Jupiter and establishing greenhouse gas industries to heat up the atmosphere. The cards can give you immediate bonuses, as well as increasing your production of different resources. Many cards also have requirements and they become playable when the temperature, oxygen, or ocean coverage increases enough. Buying cards is costly, so there is a balance between buying cards (3 megacredits per card) and actually playing them (which can cost anything between 0 to 41 megacredits, depending on the project). Standard Projects are always available to complement your cards.&#10;&#10;Your basic income, as well as your basic score, is based on your Terraform Rating (starting at 20), which increases every time you raise one of the three global parameters. However, your income is complemented with your production, and you also get VPs from many other sources.&#10;&#10;Each player keeps track of their production and resources on their player boards, and the game uses six types of resources: MegaCredits, Steel, Titanium, Plants, Energy, and Heat. On the game board, you compete for the best places for your city tiles, ocean tiles, and greenery tiles. You also compete for different Milestones and Awards worth many VPs. Each round is called a generation (guess why) and consists of the following phases:&#10;&#10;1) Player order shifts clockwise.&#10;2) Research phase: All players buy cards from four privately drawn.&#10;3) Action phase: Players take turns doing 1-2 actions from these options: Playing a card, claiming a Milestone, funding an Award, using a Standard project, converting plant into greenery tiles (and raising oxygen), converting heat into a temperature raise, and using the action of a card in play. The turn continues around the table (sometimes several laps) until all players have passed.&#10;4) Production phase: Players get resources according to their terraform rating and production parameters.&#10;&#10;When the three global parameters (temperature, oxygen, ocean) have all reached their goal, the terraforming is complete, and the game ends after that generation. Count your Terraform Rating and other VPs to determine the winning corporation!&#10;&#10;", 2016, 1, 5, 120, 120, 120, 12, "FryxGames", "Jacob Fryxelius", "https://strongholdgames.com/store/board-games/terraforming-mars/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "http://www.fryxgames.se/TerraformingMars/TMRULESFINAL.pdf", "696859265808"));
        gamesList.add(new BoardGame("184267", "BgXeKFHwhz", "https://cf.geekdo-images.com/Nm0Iw8NoiM9V8IsifimGBw__thumb/img/4GFbhkEWyIublXmikE2Tep6wSIQ=/fit-in/200x150/filters:strip_icc()/pic4357658.jpg", "https://cf.geekdo-images.com/Nm0Iw8NoiM9V8IsifimGBw__original/img/GNjjsgxq7wYO9pSIteDJe6Sxt00=/0x0/filters:format(jpeg)/pic4357658.jpg", "On Mars", "Following the success of unmanned rover missions, the United Nations established the Department of Operations and Mars Exploration (D.O.M.E.). The first settlers arrived on Mars in the year 2037 and in the decades after establishment Mars Base Camp, private exploration companies began work on the creation of a self-sustaining colony. As chief astronaut for one of these enterprises, you want to be a pioneer in the development of the biggest, most advanced colony on Mars by achieving both D.O.M.E. mission goals as well as your company&rsquo;s private agenda.&#10;&#10;In the beginning, you will be dependent on supplies from Earth and will have to travel often between the Mars Space Station and the planet's surface. As the colony expands over time, you will shift your activities to construct mines, power generators, water extractors, greenhouses, oxygen factories, and shelters. Your goal is to develop a self-sustaining colony independent of any terrestrial organization. This will require understanding the importance of water, air, power, and food &mdash; the necessities for survival.&#10;&#10;Do you dare take part in humankind&rsquo;s biggest challenge?&#10;&#10;On Mars is played over several rounds, each consisting of two phases - the Colonization Phase &#226;&#128;&#139;and the Shuttle Phase&#226;&#128;&#139;.&#10;&#10;During the Colonization Phase, each player takes a turn during which they take actions. The available actions depend on the side of the board they are on. If you are in orbit, you can take blueprints, buy and develop technologies, and take supplies from the Warehouse. If you are on the surface of the planet, you can construct buildings with your bots, upgrade these buildings using blueprints, take scientists and new contracts, welcome new ships, and explore the planet&rsquo;s surface with your rover. In the Shuttle Phase, players may travel between the colony and the Space Station in orbit.&#10;&#10;All buildings on Mars have a dependency on each other and some are required for the colony to grow. Building shelters for Colonists to live in requires oxygen; generating oxygen requires plants; growing plants requires water; extracting water from ice requires power; generating power requires mining minerals; and mining minerals requires Colonists. Upgrading the colony&rsquo;s ability to provide each of these resources is vital. As the colony grows, more shelters are needed so that the Colonists can survive the inhospitable conditions on Mars.&#10;&#10;During the game, players are also trying to complete missions. Once a total of three missions have been completed, the game ends. To win the game, players must contribute to the development of the first colony on Mars. This is represented during the game by players gaining Opportunity Points (OP). The player with the most OP at the end of the game is declared the winner.&#10;&#10;", 2020, 1, 4, 150, 90, 150, 14, "Eagle-Gryphon Games", "Vital Lacerda", "https://www.kickstarter.com/projects/eaglegryphon/on-mars-by-vital-lacerda-with-artwork-by-ian-otool?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", null, null));
        gamesList.add(new BoardGame("10547", "3RVcHxhPEZ", "https://cf.geekdo-images.com/lqmt2Oti_qJS65XqHcB8AA__thumb/img/EDOmDbRhLy4za2PHkJ5IbhNxZmk=/fit-in/200x150/filters:strip_icc()/pic5146864.png", "https://cf.geekdo-images.com/lqmt2Oti_qJS65XqHcB8AA__original/img/7R6yYk8A2eUMmSDaxGjae5SceGI=/0x0/filters:format(png)/pic5146864.png", "Betrayal at House on the Hill", "From the press release:&#10;&#10;Betrayal at House on the Hill quickly builds suspense and excitement as players explore a haunted mansion of their own design, encountering spirits and frightening omens that foretell their fate. With an estimated one hour playing time, Betrayal at House on the Hill is ideal for parties, family gatherings or casual fun with friends.&#10;&#10;Betrayal at House on the Hill is a tile game that allows players to build their own haunted house room by room, tile by tile, creating a new thrilling game board every time. The game is designed for three to six people, each of whom plays one of six possible characters.&#10;&#10;Secretly, one of the characters betrays the rest of the party, and the innocent members of the party must defeat the traitor in their midst before it&rsquo;s too late! Betrayal at House on the Hill will appeal to any game player who enjoys a fun, suspenseful, and strategic game.&#10;&#10;Betrayal at House on the Hill includes detailed game pieces, including character cards, pre-painted plastic figures, and special tokens, all of which help create a spooky atmosphere and streamline game play.&#10;&#10;An updated reprint of Betrayal at House on the Hill was released on October 5, 2010.&#10;&#10;", 2004, 3, 6, 60, 60, 60, 12, "Avalon Hill Games, Inc.", "Bill McQuillan", "http://avalonhill.wizards.com/avalon-hill-betrayal-house-hill?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://media.wizards.com/2018/downloads/rules/betrayal_rules.pdf", null));
        gamesList.add(new BoardGame("144344", "L3bbRD1H4o", "https://cf.geekdo-images.com/Go8mEe3K1VjYFJxLXEUPqQ__thumb/img/cclTiR7tkCqMj-TuFAaYfySFmH4=/fit-in/200x150/filters:strip_icc()/pic1795272.jpg", "https://cf.geekdo-images.com/Go8mEe3K1VjYFJxLXEUPqQ__original/img/RcJ7xX5K_y0G87nK5ttdcWeb10U=/0x0/filters:format(jpeg)/pic1795272.jpg", "Rococo", "Welcome to the Rococo era during the reign of Louis XV when it&rsquo;s safe to say that holding lavish balls is quite trendy. Important personalities wrap up in noble coats and dresses, anxious to outshine one another. As the biggest event is coming up in just a few weeks, everyone is turning to you with their requests: an elegant coat here, a stunning dress there, or a donation to fund the fireworks at the event. Soon you realize that it&rsquo;s not just about your dressmaking business anymore &mdash; it&rsquo;s about managing the most prestigious ball of the era&hellip;and now it&rsquo;s time to ro(c)k!&#10;&#10;Rokoko is a Eurostyle board game with an interesting take on deck-building. Each turn you play one of your employee cards and let that employee perform a task: hire a new employee, buy resources, manufacture a coat or dress, or invest in the ball&rsquo;s decorations. But not every employee is up to every task, so you must choose and lead your employees wisely &mdash; especially since each employee grants a unique bonus and some of these bonuses generate prestige points.&#10;&#10;After seven rounds, the game ends with the big ball and a final scoring. Then you gain prestige points for certain employee bonuses and for coats and dresses that you rent out to guests at the ball as well as for decorations that you funded. The player who collected the most prestige points wins.&#10;&#10;", 2013, 2, 5, 120, 60, 120, 12, "Eagle-Gryphon Games", "Matthias Cramer", "http://www.eggrules.com/games/games-q-z/rococo/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", null, null));
        gamesList.add(new BoardGame("312786", "yqtKCtQD1i", "https://cf.geekdo-images.com/CDlBjXSPT-3Zyrj8ENBkvQ__thumb/img/0ON18UcLepuwPLhwkRtRUjhvAZ8=/fit-in/200x150/filters:strip_icc()/pic5702797.jpg", "https://cf.geekdo-images.com/CDlBjXSPT-3Zyrj8ENBkvQ__original/img/FgAyx5Q1pe7TyHsYskhbab_S_os=/0x0/filters:format(jpeg)/pic5702797.jpg", "Poetry for Neanderthals", "Poetry for Neanderthals is a competitive word-guessing game where you can only give clues by speaking in single syllables. So, instead of saying &quot;broccoli,&quot; you'd say something like &quot;green thing you eat for live long and have good health.&quot; If you mess up and use a big word, such as &quot;vegetable,&quot; you get bopped on the head with a NO !Stick and you lose points.&#10;&#10;The goal of the game is to score the most points by correctly interpreting words and phrases. The chosen Poet starts off the game with a Poetry Card and tries to get their teammates to say the listed word, using only words with one syllable within a 90-second time limit.&#10;&#10;The team with the most points is the winner.&#10;&#10;Contents:&#10;220 cards&#10;1 inflatable NO! Stick&#10;1 sand timer&#10;2 point slates&#10;&#10;", 2020, 2, 12, 15, 15, 15, 7, "Exploding Kittens", null, "https://www.explodingkittens.com/products/poetry-for-neanderthals?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.explodingkittens.com/pages/rules-poetry-for-neanderthals", "852131006303"));
        gamesList.add(new BoardGame("320", "UI0oxncplG", "https://cf.geekdo-images.com/mVmmntn2oQd0PfFrWBvwIQ__thumb/img/RUmuGCB40FQH0en0R2nrcsSO7DE=/fit-in/200x150/filters:strip_icc()/pic404651.jpg", "https://cf.geekdo-images.com/mVmmntn2oQd0PfFrWBvwIQ__original/img/11jrKPiOVTNl5NwX83KGtTZEq40=/0x0/filters:format(jpeg)/pic404651.jpg", "Scrabble", "In this classic word game, players use their seven drawn letter-tiles to form words on the gameboard.  Each word laid out earns points based on the commonality of the letters used, with certain board spaces giving bonuses.  But a word can only be played if it uses at least one already-played tile or adds to an already-played word.  This leads to slightly tactical play, as potential words are rejected because they would give an opponent too much access to the better bonus spaces.&#10;&#10;Skip-a-cross was licensed by Selchow &amp; Righter and manufactured by Cadaco. Both games have identical rules but Skip-a-cross has tiles and racks made of cardboard instead of wood. The game was also published because not enough Scrabble games were manufactured to meet the demand.&#10;&#10;", 1948, 2, 4, 90, 90, 90, 10, "Hasbro", "Alfred Mosher Butts", "http://scrabble.hasbro.com?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://scrabble.hasbro.com/en-us/rules", "630509445226"));
        gamesList.add(new BoardGame("205398", "Yd28NnaUA0", "https://cf.geekdo-images.com/sHd0jkZZLDgixHjAXtn7kA__thumb/img/HB0Z5D7uwBjf1lCCPjMck-5VP9A=/fit-in/200x150/filters:strip_icc()/pic3119514.jpg", "https://cf.geekdo-images.com/sHd0jkZZLDgixHjAXtn7kA__original/img/9n76iqzSN06hlbX4DuuPD6fSkHQ=/0x0/filters:format(jpeg)/pic3119514.jpg", "Citadels", "In Citadels, players take on new roles each round to represent characters they hire in order to help them acquire gold and erect buildings. The game ends at the close of a round in which a player erects their eighth building. Players then tally their points, and the player with the highest score wins.&#10;&#10;Players start the game with a number of building cards in their hand; buildings come in five colors, with the purple buildings typically having a special ability and the other colored buildings providing a benefit when you play particular characters. At the start of each round, the player who was king the previous round discards one of the eight character cards at random, chooses one, then passes the cards to the next player, etc. until each player has secretly chosen a character. Each character has a special ability, and the usefulness of any character depends upon your situation, and that of your opponents. The characters then carry out their actions in numerical order: the assassin eliminating another character for the round, the thief stealing all gold from another character, the wizard swapping building cards with another player, the warlord optionally destroys a building in play, and so on.&#10;&#10;On a turn, a player earns two or more gold (or draws two building cards then discards one), then optionally constructs one building (or up to three if playing the architect this round). Buildings cost gold equal to the number of symbols on them, and each building is worth a certain number of points. In addition to points from buildings, at the end of the game a player scores bonus points for having eight buildings or buildings of all five colors.&#10;&#10;The 2016 edition of Citadels includes twenty-seven characters &mdash; eight from the original Citadels, ten from the Dark City expansion, and nine new ones &mdash; along with thirty unique building districts, and the rulebook includes six preset lists of characters and districts beyond the starter list, each crafted to encourage a different style and intensity of gameplay.&#10;&#10;", 2016, 2, 8, 60, 30, 60, 10, "Z-Man Games, Inc.", "Bruno Faidutti", "https://www.zmangames.com/en/products/citadels/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://images.zmangames.com/filer_public/c4/8f/c48f0e33-2967-4cf8-a486-487ff4b46e29/zc01-rulebook_web.pdf", "841333102067"));
        gamesList.add(new BoardGame("822", "oGVgRSAKwX", "https://cf.geekdo-images.com/okM0dq_bEXnbyQTOvHfwRA__thumb/img/88274KiOg94wziybVHyW8AeOiXg=/fit-in/200x150/filters:strip_icc()/pic6544250.png", "https://cf.geekdo-images.com/okM0dq_bEXnbyQTOvHfwRA__original/img/aVZEXAI-cUtuunNfPhjeHlS4fwQ=/0x0/filters:format(png)/pic6544250.png", "Carcassonne", "Carcassonne is a tile-placement game in which the players draw and place a tile with a piece of southern French landscape on it. The tile might feature a city, a road, a cloister, grassland or some combination thereof, and it must be placed adjacent to tiles that have already been played, in such a way that cities are connected to cities, roads to roads, etcetera. Having placed a tile, the player can then decide to place one of their meeples on one of the areas on it: on the city as a knight, on the road as a robber, on a cloister as a monk, or on the grass as a farmer. When that area is complete, that meeple scores points for its owner.&#10;&#10;During a game of Carcassonne, players are faced with decisions like: &quot;Is it really worth putting my last meeple there?&quot; or &quot;Should I use this tile to expand my city, or should I place it near my opponent instead, giving him a hard time to complete their project and score points?&quot; Since players place only one tile and have the option to place one meeple on it, turns proceed quickly even if it is a game full of options and possibilities.&#10;&#10;First game in the Carcassonne series.&#10;&#10;", 2000, 2, 5, 45, 30, 45, 7, "Hans im Glück", "Klaus-Jürgen Wrede", "https://www.zmangames.com/en/products/carcassonne/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://images-cdn.zmangames.com/us-east-1/filer_public/d5/20/d5208d61-8583-478b-a06d-b49fc9cd7aaa/zm7810_carcassonne_rules.pdf", "681706781006"));
        gamesList.add(new BoardGame("9209", "AuBvbISHR6", "https://cf.geekdo-images.com/ZWJg0dCdrWHxVnc0eFXK8w__thumb/img/a9rsFV6KR0aun8GobhRU16aU8Kc=/fit-in/200x150/filters:strip_icc()/pic38668.jpg", "https://cf.geekdo-images.com/ZWJg0dCdrWHxVnc0eFXK8w__original/img/LgzEsQlF3xkSEQLoorc8ntiYiIY=/0x0/filters:format(jpeg)/pic38668.jpg", "Ticket to Ride", "With elegantly simple gameplay, Ticket to Ride can be learned in under 15 minutes. Players collect cards of various types of train cars they then use to claim railway routes in North America. The longer the routes, the more points they earn. Additional points come to those who fulfill Destination Tickets &ndash; goal cards that connect distant cities; and to the player who builds the longest continuous route.&#10;&#10;&quot;The rules are simple enough to write on a train ticket &ndash; each turn you either draw more cards, claim a route, or get additional Destination Tickets,&quot; says Ticket to Ride author, Alan R. Moon. &quot;The tension comes from being forced to balance greed &ndash; adding more cards to your hand, and fear &ndash; losing a critical route to a competitor.&quot;&#10;&#10;Ticket to Ride continues in the tradition of Days of Wonder's big format board games featuring high-quality illustrations and components including: an oversize board map of North America, 225 custom-molded train cars, 144 illustrated cards, and wooden scoring markers.&#10;&#10;Since its introduction and numerous subsequent awards, Ticket to Ride has become the BoardGameGeek epitome of a &quot;gateway game&quot; -- simple enough to be taught in a few minutes, and with enough action and tension to keep new players involved and in the game for the duration.&#10;&#10;Part of the Ticket to Ride series.&#10;&#10;", 2004, 2, 5, 60, 30, 60, 8, "Days of Wonder", "Alan R. Moon", "https://www.daysofwonder.com/tickettoride/en/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://ncdn0.daysofwonder.com/tickettoride/en/img/tt_rules_2015_en.pdf", "824968717912"));
        gamesList.add(new BoardGame("230802", "i5Oqu5VZgP", "https://cf.geekdo-images.com/tz19PfklMdAdjxV9WArraA__thumb/img/debo694HgBaIBeNGyxu1ELUbQGA=/fit-in/200x150/filters:strip_icc()/pic3718275.jpg", "https://cf.geekdo-images.com/tz19PfklMdAdjxV9WArraA__original/img/eFRt3o7W6YltnrkqpiSOKr2rKCw=/0x0/filters:format(jpeg)/pic3718275.jpg", "Azul", "Introduced by the Moors, azulejos (originally white and blue ceramic tiles) were fully embraced by the Portuguese when their king Manuel I, on a visit to the Alhambra palace in Southern Spain, was mesmerized by the stunning beauty of the Moorish decorative tiles. The king, awestruck by the interior beauty of the Alhambra, immediately ordered that his own palace in Portugal be decorated with similar wall tiles. As a tile-laying artist, you have been challenged to embellish the walls of the Royal Palace of Evora.&#10;&#10;In the game Azul, players take turns drafting colored tiles from suppliers to their player board. Later in the round, players score points based on how they've placed their tiles to decorate the palace. Extra points are scored for specific patterns and completing sets; wasted supplies harm the player's score. The player with the most points at the end of the game wins.&#10;&#10;", 2017, 2, 4, 45, 30, 45, 8, "Next Move Games", "Michael Kiesling", "https://www.nextmovegames.com/en/home/48-azul.html?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.nextmovegames.com/en/index.php?controller=attachment&id_attachment=9", null));
        gamesList.add(new BoardGame("266192", "5H5JS0KLzK", "https://cf.geekdo-images.com/yLZJCVLlIx4c7eJEWUNJ7w__thumb/img/VNToqgS2-pOGU6MuvIkMPKn_y-s=/fit-in/200x150/filters:strip_icc()/pic4458123.jpg", "https://cf.geekdo-images.com/yLZJCVLlIx4c7eJEWUNJ7w__original/img/cI782Zis9cT66j2MjSHKJGnFPNw=/0x0/filters:format(jpeg)/pic4458123.jpg", "Wingspan", "Wingspan is&nbsp;a competitive, medium-weight, card-driven, engine-building board game from Stonemaier Games. It's designed by Elizabeth Hargrave and features over 170 birds illustrated by Beth Sobel, Natalia Rojas, and Ana Maria Martinez.&#10;&#10;You are bird enthusiasts&mdash;researchers, bird watchers, ornithologists, and collectors&mdash;seeking to discover and attract the best birds to your network of wildlife preserves. Each bird extends a chain of powerful combinations in one of your habitats (actions). These habitats  focus on several key aspects of growth:&#10;&#10;&#10;     Gain food tokens via custom dice in a birdfeeder dice tower&#10;     Lay eggs using egg miniatures in a variety of colors&#10;     Draw from hundreds of unique bird cards and play them&#10;&#10;&#10;The winner is the player with the most points after 4 rounds.&#10;&#10;If you enjoy Terraforming Mars and Gizmos, we think this game will take flight at your table.&#10;&#10;&mdash;description from the publisher&#10;&#10;From the 7th printing on, the base game box includes Wingspan: Swift-Start Promo Pack.&#10;&#10;", 2019, 1, 5, 70, 40, 70, 10, "Stonemaier Games", "Elizabeth Hargrave", "https://stonemaiergames.com/games/wingspan/?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://stonemaiergames.com/games/wingspan/rules/", "644216627721"));
        gamesList.add(new BoardGame("256916", "xetNswpgzl", "https://cf.geekdo-images.com/36WIe0ZHkp5OvHOlB-8vog__thumb/img/C7u2bDi9kOoLkLoYHJ_jr0A928A=/fit-in/200x150/filters:strip_icc()/pic4356580.jpg", "https://cf.geekdo-images.com/36WIe0ZHkp5OvHOlB-8vog__original/img/4oIeldEZrg5Fi2YAlc5SzldywaA=/0x0/filters:format(jpeg)/pic4356580.jpg", "Concordia Venus", "Concordia Venus is a standalone reimplementation of Concordia with some added features.&#10;&#10;Concordia Venus is a peaceful strategy game of economic development in Roman times for 2-6 players aged 13 and up. Instead of looking to the luck of dice or cards, players must rely on their strategic abilities. Be sure to watch your rivals to determine which goals they are pursuing and where you can outpace them! In the game, colonists are sent out from Rome to settle down in cities that produce bricks, food, tools, wine, and cloth. Each player starts with an identical set of playing cards and acquires more cards during the game. These cards serve two purposes:&#10;&#10;They allow a player to choose actions during the game.&#10;They are worth victory points (VPs) at the end of the game.&#10;&#10;Concordia is a strategy game that requires advance planning and consideration of your opponent's moves. Every game is different, not only because of the sequence of new cards on sale but also due to the modular layout of cities on the 4 different maps included with the game. When all cards have been sold from the market or after the first player builds his 15th house, the game ends. The player with the most VPs from the gods (Jupiter, Saturnus, Mercurius, Minerva, Vesta, Venus, etc.) wins the game.&#10;&#10;&#10;     Teams of two players each may play against each other.&#10;     New personality cards with the goddess Venus allow for new strategies.&#10;     New maps (Cyprus, Hellas, Ionium) on which to play in addition to the classic Imperium map.&#10;&#10;&#10;The Italia map plus a new map will be sold as an expansion in 2019 for Venus owners.&#10;&#10;Note: The Concordia Venus expansion, which should not be confused with the Concordia Venus standalone game, can be found here. The expansion has Hellas/Ionium maps but not Imperium (which is already in the base game) or Cyprus (which will also be sold with a new map in an expansion in 2019 for original Concordia owners).&#10;&#10;", 2018, 2, 6, 120, 60, 120, 12, "PD-Verlag", "Mac Gerdts", "http://riograndegames.com/Game/1336-Concordia-with-Venus-expansion?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.riograndegames.com/wp-content/uploads/2018/11/Concordia-Venus-Rules-V11.pdf", "655132005616"));
        gamesList.add(new BoardGame("1406", "fG5Ax8PA7n", "https://cf.geekdo-images.com/9nGoBZ0MRbi6rdH47sj2Qg__thumb/img/ezXcyEsHhS9iRxmuGe8SmiLLXlM=/fit-in/200x150/filters:strip_icc()/pic5786795.jpg", "https://cf.geekdo-images.com/9nGoBZ0MRbi6rdH47sj2Qg__original/img/bA8irydTCNlE38QSzM9EhcUIuNU=/0x0/filters:format(jpeg)/pic5786795.jpg", "Monopoly", "Theme&#10;Players take the part of land owners, attempting to buy and then develop their land. Income is gained by other players visiting their properties and money is spent when they visit properties belonging to other players. When times get tough, players may have to mortgage their properties to raise cash for fines, taxes and other misfortunes.&#10;&#10;Gameplay&#10;On his turn, a player rolls two dice and moves that number of spaces around the board. If the player lands on an as-yet-unowned property, he has the opportunity to buy it and add it to his portfolio or allow the bank to auction it to the highest bidder. If a player owns all the spaces within a color group, he may then build houses and hotels on these spaces, generating even more income from opponents who land there. If he lands on a property owned by another player, he must pay that player rent according to the value of the land and any buildings on it. There are other places on the board which can not be bought, but instead require the player to draw a card and perform the action on the card, pay taxes, collect income, or even go to jail.&#10;&#10;Goal&#10;The goal of the game is to be the last player remaining with any money.&#10;&#10;Cultural impact on rules&#10;Monopoly is unusual in that the game has official, printed rules, but most players learn how to play from others, never actually learning the correct way to play. This has led to the canonization of a number of house rules that make the game more palatable to children (and sore losers) but harm the gameplay by preventing players from going bankrupt or slowing down the rate of property acquisition. One common house rule has players put any money paid to the bank in the center of the board, which jackpot a player may earn by landing on Free Parking. This prevents the game from removing money from play, and since players collect $200 each time they pass Go, this results in ever-increasing bankrolls and players surviving rents that should have bankrupted them. Another house rule allows players to take &quot;loans&quot; from the bank instead of going bankrupt, which means the game will never end. Some house rules arise out of ignorance rather than attempts to improve the game. For instance, many players don't know that properties landed on but left unbought go up for auction, and even some that know to auction don't know that the bidding starts at $1, meaning a player may pay well below the listed price for an auctioned property.&#10;&#10;Background&#10;In the USA in 1933, Charles Darrow devised Monopoly based on an earlier game by Elizabeth J. Magie. The patent was filed 31st August 1935 while the game was on sale in America. Based on an earlier game, The Landlord's Game, it was at first rejected by Parker Bros., as being too complicated to be a success. How wrong could they be! It came to the UK in 1936, made under licence by Waddingtons. Darrow died in 1967 having realised he had developed one of the most successful board games of all times. It was awarded as Game of the Century by the TRA (Toy Retailers Association).&#10;&#10;Monopoly was patented in 1935 by Charles Darrow and released by Parker Brothers. The game was actually one of a number of variants in existence at the time, all of which date back to an earlier, 1904 game by Elizabeth J. Magie called The Landlord's Game. Magie was a proponent of the Single Tax put forth by famous author Henry George.  The game was designed to show the evils of earning money from renting land (as it leads to the destitution of all but one player) and the virtues of the proposed Single Tax - players could choose to play under regular rules or alternate &quot;Single Tax&quot; rules.&#10;&#10;The game didn't really go anywhere and Magie lost interest in it. Variations of the game evolved, however, and homemade versions traveled up and down the Atlantic coast and even as far west as Michigan and Texas, being developed all along the way. Eventually the game was noticed by Charles Darrow, who introduced it to the world in its current form.&#10;&#10;Re-implements:&#10;&#10;    The Landlord's Game&#10;&#10;&#10;Expanded by:&#10;Official&#10;&#10;    Monopoly Stock Exchange Add-on&#10;    Monopoly Free Parking Mini Game&#10;    Monopoly Get Out of Jail Mini Game&#10;&#10;&#10;Unofficial&#10;&#10;    Super Add-Ons: Monopoly (fan expansion for Monopoly)&#10;    Entrepreneur's Accessory to Monopoly (fan expansion for Monopoly)&#10;    Game Spice: Monopoly Expansion (fan expansion for Monopoly)&#10;    Mafiopoly&#10;    Monopoly: Mob Rule Expansion Deck&#10;    Final Fantasy Monopoly&#10;&#10;&#10;", 1935, 2, 8, 180, 60, 180, 8, "Hasbro", "Charles Darrow", "https://shop.hasbro.com/en-us/product/monopoly-classic-game:7EABAF97-5056-9047-F577-8F4663C79E75?utm_source=boardgameatlas.com&utm_medium=search&utm_campaign=bga_ads", "https://www.hasbro.com/common/instruct/monins.pdf", "700304154415"));
    }

    private static void initGameNightCache() {
        final int NUM_GAME_NIGHTS = 3;
        final int MIN_EVENT_HOUR = 17;
        final int MAX_EVENT_HOUR = 23;
        final int MIN_INVITES = 1;
        final int MAX_INVITES = 5;
        final int MIN_USER_GAMES = 1;
        final int MAX_USER_GAMES = 3;
        final int MIN_GAME_NIGHT_GAMES = 1;
        final int MAX_GAME_NIGHT_GAMES = 5;

        // These two lists are the same length, but don't need to be
        List<String> genericNames = Arrays.asList(
                "11th Ward FHE",
                "Good Move Date",
                "Games w/Dave",
                "Weekly Game Night",
                "The Chess Two Years",
                "Birthday Party! 🎉",
                "Jenga-roo");
        List<String> genericLocations = Arrays.asList(
                "Apartment 110",
                "1 E Center St, Provo, UT 84606",
                "935 N University Ave #9",
                "876 N 300th W",
                "1380 W 1700th N",
                "522 E 3950th N",
                "Your Mom's House");

        assert(gameNightList == null);
        gameNightList = new ArrayList<>();
        List<User> userList = getUserList();

        for (int i = 0; i < NUM_GAME_NIGHTS; ++i) {
            // Randomly selects a host from all users
            User randomHost = userList.get(RANDOM.nextInt(userList.size()));

            /* Iterates through the genericNames list for names. If
             * NUM_GAME_NIGHTS > genericNames.size() it will just start at the beginning again.
             * The same will happen for genericLocation */
            String name = genericNames.get(i % genericNames.size());

            /* Creating a time:
             * The way that this works is that for each game night that needs to be generated
             * (up to NUM_GAME_NIGHTS), it starts by making a game night for tonight, the night
             * after that, etc. Each event is also given a random time on the hour or half-hour
             * sometime between 5 and 10PM */
            Calendar cal = Calendar.getInstance();
            int day = cal.get(Calendar.DAY_OF_MONTH);
            cal.set(Calendar.DAY_OF_MONTH, day + i);
            cal.set(Calendar.HOUR, RANDOM.nextInt((MAX_EVENT_HOUR - MIN_EVENT_HOUR) + 1) + MIN_EVENT_HOUR);
            cal.set(Calendar.MINUTE, (RANDOM.nextBoolean()) ? 0 : 30);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            LocalDateTime date = LocalDateTime.ofInstant(
                    cal.toInstant(),
                    cal.getTimeZone().toZoneId());


            /* These locations are literally just from https://www.bestrandoms.com/random-address
             * when you give it 84604, and I added in a couple just raw text as well, to
             * make sure the View handles it well. */
            String location = genericLocations.get(i % genericLocations.size());

            /* First, when creating the guest list, the main user is added, and randomly
             * selected as either coming or not responded.
             *
             * Secondly, the number of guests is randomly selected, and they are added with a
             * completely random RSVP. (I don't know what effect the ones other than YES and NO
             * should really have...). Check out randomEnum for how the enum is randomly chosen */
            Map<String, RSVP> guestList = new HashMap<>();
            guestList.put(
                    authenticatedUser.getPhoneNumber(),
                    (RANDOM.nextBoolean()) ? RSVP.YES : RSVP.NOT_YET_RESPONDED);
            int numInvites = RANDOM.nextInt((MAX_INVITES - MIN_INVITES) + 1) + MIN_INVITES;
            for (int j = 0; j < numInvites; ++j) {
                User randomInvite = userList.get(RANDOM.nextInt(userList.size()));
                guestList.put(
                        randomInvite.getPhoneNumber(),
                        randomEnum(RSVP.class));
            }

            /* Bringing assignments:
             * First, I flip a coin to see if the current user should bring any games. If this is
             * the first game being created, this will be guaranteed. The current user then has a
             * random number of board games assigned for them to bring from their own collection.
             * The reason for requiring the first BoardGame object (and the reason we're even giving
             * the user assignments in general) is so that it shows up in the MainActivity cards.
             *
             * Secondly, the program chooses a number of other board games being brought, from the
             * list of all board games in the database (because I don't want to think about who is
             * invited owning what game or whatever). This does not currently take the voting
             * system into account. Each board game has then a 3 / 4 chance of being assigned,
             * and is then randomly assigned to an id in the guestList. This _does_ mean that the
             * current user may be assigned additional games, but that's fine.
             *
             * Also, I make copies of the game lists and shuffle them each time because I don't
             * want repeats.
             */
            Map<String, String> bringingAssignments = new HashMap<>();
            if (i == 0 || RANDOM.nextBoolean()) {
                int numCurrUserGames = RANDOM.nextInt(
                        (MAX_USER_GAMES - MIN_USER_GAMES) + 1) + MIN_USER_GAMES;
                final List<BoardGame> currUserGames = getGamesForAuthenticatedUser();
                List<BoardGame> shuffledGames = new ArrayList<>(currUserGames);
                Collections.shuffle(shuffledGames);
                assert MAX_USER_GAMES < shuffledGames.size();
                for (int j = 0; j < numCurrUserGames; ++j) {
                    bringingAssignments.put(
                            shuffledGames.get(j).getBggId(),
                            authenticatedUser.getPhoneNumber());
                }
            }

            int numCurrUserGames = RANDOM.nextInt(
                    (MAX_GAME_NIGHT_GAMES - MIN_GAME_NIGHT_GAMES) + 1) + MIN_GAME_NIGHT_GAMES;
            final List<BoardGame> allGames = getGamesList();
            List<BoardGame> shuffledGames = new ArrayList<>(allGames);
            Collections.shuffle(shuffledGames);
            for (int j = 0; j < numCurrUserGames; ++j) {
                if (bringingAssignments.containsKey(shuffledGames.get(j).getBggId())) {
                    --j;
                    continue;
                }

                String assigned = null;
                if (xInYChance(3, 4)) {
                    assigned = (new ArrayList<>(guestList.keySet()))
                            .get(RANDOM.nextInt(guestList.size()));
                }
                bringingAssignments.put(shuffledGames.get(j).getBgaId(), assigned);
            }

            gameNightList.add(new GameNight(
                    name,
                    randomHost.getPhoneNumber(),
                    date,
                    location,
                    guestList,
                    bringingAssignments));
        }
    }

    /* Helper Functions */
    private static <T extends Enum<?>> T randomEnum(Class<T> clazz){
        int x = RANDOM.nextInt(Objects.requireNonNull(clazz.getEnumConstants()).length);
        return clazz.getEnumConstants()[x];
    }

    /* Returns the chance that x in y happens*/
    public static Boolean xInYChance(int x, int y) {
        return RANDOM.nextDouble() <= ((double) x / y);
    }
}
