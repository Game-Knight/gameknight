package DataAccess.DAO.APIs;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import DataAccess.DAO.Interfaces.IBoardGameDAO;
import DataAccess.DataGeneration.XML.BGAKeys;
import DataAccess.DataGeneration.XML.BGGKeys;
import Entities.BoardGame;
import Exceptions.DataAccessException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class APIBoardGameDAO implements IBoardGameDAO {
    private static final String BGG_API_URL="https://boardgamegeek.com/xmlapi2/";
    private static final String BGG_QUERY_BY_ID="thing?id=";
    private static final String BGG_SEARCH_BY_NAME="search?type=boardgame&query=";
    private static final String BGA_API_URL="https://api.boardgameatlas.com/api/";
    private static final String BGA_CLIENT_ID="3OGB9fbtWY";
    private static final String BGA_QUERY_BY_NAME="search?client_id=" + BGA_CLIENT_ID + "&name=";

    @Override
    public BoardGame getBoardGameById(String id) throws DataAccessException {
        BoardGame game = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
//            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(BGG_API_URL + BGG_QUERY_BY_ID + id);
            doc.getDocumentElement().normalize();

            Node node = doc.getElementsByTagName("item").item(0);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                game = bggItemToBoardGame(node);
            }
        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            e.printStackTrace();
        }
        return game;
    }

    @Override
    public List<BoardGame> getBoardGamesByIds(List<String> ids, int offset) throws DataAccessException {
        List<BoardGame> gamesList = new ArrayList<>();
        ids = ids.subList(offset, ids.size());
        String idsString = String.join(",", ids);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
//            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(BGG_API_URL + BGG_QUERY_BY_ID + idsString);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("item");

            for (int i = 0; i < list.getLength(); i++) {
                Node node = list.item(i);
                gamesList.add(bggItemToBoardGame(node));
            }

        } catch (ParserConfigurationException | SAXException | IOException | ParseException e) {
            e.printStackTrace();
        }

        return gamesList;
    }

    public List<BoardGame> getBoardGamesByName(String name, int count) throws DataAccessException {
        List<BoardGame> gamesList = new ArrayList<>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
//            dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(BGG_API_URL + BGG_SEARCH_BY_NAME + name);
            doc.getDocumentElement().normalize();

            NodeList list = doc.getElementsByTagName("item");
            if (count > list.getLength()) {
                count = list.getLength();
            }
            for (int i = 0; i < count; i++) {
                Node node = list.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String bggId = element.getAttribute(BGGKeys.ID);
                    gamesList.add(getBoardGameById(bggId));
                }
            }

        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }

        return gamesList;
    }

    private static BoardGame bggItemToBoardGame(Node node) throws IOException, ParseException {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element element = (Element) node;

            String bggId = element.getAttribute(BGGKeys.ID);
            String thumbnailUrl = null;
            if (element.getElementsByTagName(BGGKeys.THUMBNAIL).item(0) != null) {
                thumbnailUrl = element.getElementsByTagName(BGGKeys.THUMBNAIL).item(0).getTextContent();
            }
            String imageUrl = null;
            if (element.getElementsByTagName(BGGKeys.IMAGE).item(0) != null) {
                imageUrl = element.getElementsByTagName(BGGKeys.IMAGE).item(0).getTextContent();
            }
            String name = element.getElementsByTagName(BGGKeys.NAME).item(0).getAttributes().getNamedItem("value").getNodeValue();
            String bggDescription = element.getElementsByTagName(BGGKeys.DESCRIPTION).item(0).getTextContent();
            int yearPublished = Integer.parseInt(element.getElementsByTagName(BGGKeys.YEAR_PUBLISHED).item(0).getAttributes().getNamedItem("value").getNodeValue());
            int minPlayers = Integer.parseInt(element.getElementsByTagName(BGGKeys.MIN_PLAYERS).item(0).getAttributes().getNamedItem("value").getNodeValue());
            int maxPlayers = Integer.parseInt(element.getElementsByTagName(BGGKeys.MAX_PLAYERS).item(0).getAttributes().getNamedItem("value").getNodeValue());
            int playingTime = Integer.parseInt(element.getElementsByTagName(BGGKeys.PLAYING_TIME).item(0).getAttributes().getNamedItem("value").getNodeValue());
            int minPlayTime = Integer.parseInt(element.getElementsByTagName(BGGKeys.MIN_PLAY_TIME).item(0).getAttributes().getNamedItem("value").getNodeValue());
            int maxPlayTime = Integer.parseInt(element.getElementsByTagName(BGGKeys.MAX_PLAY_TIME).item(0).getAttributes().getNamedItem("value").getNodeValue());
            int minAge = Integer.parseInt(element.getElementsByTagName(BGGKeys.MIN_AGE).item(0).getAttributes().getNamedItem("value").getNodeValue());

//      HttpResponse<String> response = Unirest.get(BGA_API_URL + BGA_QUERY_BY_NAME + name).asString();
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request request = new Request.Builder()
                    .url(BGA_API_URL + BGA_QUERY_BY_NAME + name)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();

            JSONParser parser = new JSONParser();
            JSONObject bgaJSON = (JSONObject) parser.parse(response.body().string());
            JSONArray games =  ((JSONArray) bgaJSON.get("games"));

            String bgaId = null;
            String bgaDescription = null;
            String primaryPublisher = null;
            String primaryDesigner = null;
            String officialURL = null;
            String rulesURL = null;
            String upc = null;

            if (bgaJSON.get("games") == null || ((JSONArray) bgaJSON.get("games")).size() > 0) {
                JSONObject bgaGame = (JSONObject) ((JSONArray) bgaJSON.get("games")).get(0);

                bgaId = (String) bgaGame.get(BGAKeys.ID);
                bgaDescription = (String) bgaGame.get(BGGKeys.DESCRIPTION);


                if (bgaGame.get(BGAKeys.PRIMARY_PUBLISHER) != null) {
                    primaryPublisher = (String) ((JSONObject) bgaGame.get(BGAKeys.PRIMARY_PUBLISHER)).get(BGAKeys.NAME);
                }


                if (bgaGame.get(BGAKeys.PRIMARY_DESIGNER) != null) {
                    primaryDesigner = (String) ((JSONObject) bgaGame.get(BGAKeys.PRIMARY_DESIGNER)).get(BGAKeys.NAME);
                }

                officialURL = (String) bgaGame.get(BGAKeys.OFFICIAL_URL);
                rulesURL = (String) bgaGame.get(BGAKeys.RULES_URL);
                upc = (String) bgaGame.get(BGAKeys.UPC);
            }

            return new BoardGame(bggId, bgaId, thumbnailUrl, imageUrl, name,
                    bggDescription, yearPublished, minPlayers, maxPlayers, playingTime,
                    minPlayTime, maxPlayTime, minAge, primaryPublisher, primaryDesigner, officialURL, rulesURL, upc);
        }
        else return null;
    }
}
