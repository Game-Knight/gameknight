package Services;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import DataAccess.DAO.Factories.DAOFactorySingleton;
import DataAccess.DAO.Interfaces.IBoardGameDAO;
import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;
import Utils.SharedConstants;

public class AddBoardGameService implements IAddBoardGameService {
    @Override
    public AddBoardGameResponse addBoardGame(AddBoardGameRequest request) {
        try {
            String upc = request.getUpc();
            URL url = new URL("https://api.upcdatabase.org/product/" + upc);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Authorization: Basic", SharedConstants.UPC_API_KEY);
            InputStream responseStream = connection.getInputStream();

            ObjectMapper mapper = new ObjectMapper();
            // APOD apod = mapper.readValue(responseStream, APOD.class);
        }
        catch (Exception ex) {
            return new AddBoardGameResponse(false, ex.getMessage());
        }

        return new AddBoardGameResponse(true);
    }

    public IBoardGameDAO getBoardGameDAO() {
        return DAOFactorySingleton.getInstance().makeBoardGameDAO();
    }
}
