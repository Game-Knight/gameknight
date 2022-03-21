package Services;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import DataAccess.DAO.Factories.DAOFactorySingleton;
import DataAccess.DAO.Interfaces.IBoardGameDAO;
import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;
import Utils.SharedConstants;

public class AddBoardGameService implements IAddBoardGameService {
    @Override
    public AddBoardGameResponse addBoardGame(AddBoardGameRequest request) {
        try {
            URL upcURL = getUPCDatabaseURL(request.getUpc());
            HttpURLConnection upcConnection = (HttpURLConnection) upcURL.openConnection();
            InputStream upcResponseStream = upcConnection.getInputStream();

            JSONParser upcJsonParser = new JSONParser();
            JSONObject upcJsonObject = (JSONObject) upcJsonParser.parse(
                    new InputStreamReader(upcResponseStream, StandardCharsets.UTF_8)
            );

            String title = (String) upcJsonObject.get("title");
            String brand = (String) upcJsonObject.get("brand");

            URL atlasURL = getBoardGameAtlasURL(brand);
            HttpURLConnection atlasConnection = (HttpURLConnection) atlasURL.openConnection();
            InputStream atlasResponseStream = atlasConnection.getInputStream();

            JSONParser atlasJsonParser = new JSONParser();
            JSONObject atlasJsonObject = (JSONObject) atlasJsonParser.parse(
                    new InputStreamReader(atlasResponseStream, StandardCharsets.UTF_8)
            );

            System.out.println(atlasJsonObject.toJSONString());
        }
        catch (Exception ex) {
            return new AddBoardGameResponse(false, ex.getMessage());
        }

        return new AddBoardGameResponse(true);
    }

    public IBoardGameDAO getBoardGameDAO() {
        return DAOFactorySingleton.getInstance().makeBoardGameDAO();
    }

    private URL getUPCDatabaseURL(String upc) throws MalformedURLException {
        String baseURL = "https://api.upcdatabase.org/product/";
        String apiKey = "?apikey=" + SharedConstants.UPC_API_KEY;
        return new URL(baseURL + upc + apiKey);
    }

    private URL getBoardGameAtlasURL(String searchTerm) throws MalformedURLException {
        String baseURL = "https://api.boardgameatlas.com/api/search";
        String search = "?name=" + searchTerm;
        String clientId = "&pretty=true&client_id=" + SharedConstants.ATLAS_CLIENT_ID;
        return new URL(baseURL + search + clientId);
    }
}
