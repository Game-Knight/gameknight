package Services;

import DataAccess.DAO.Factories.DAOFactorySingleton;
import DataAccess.DAO.Interfaces.IUPCToBoardGameDAO;
import Exceptions.DataAccessException;
import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;

public class AddBoardGameService implements IAddBoardGameService {
    @Override
    public AddBoardGameResponse addBoardGame(AddBoardGameRequest request) {
        try {
            String boardGameId = getUPCToBoardGameDAO().getBoardGameIdByUPC(request.getUpc());
            // TODO: Decide if this is the right spot to add ownership!

            return new AddBoardGameResponse(true);
        }
        catch (DataAccessException ex) {
            return new AddBoardGameResponse(false, ex.getErrorCode(), ex.getMessage());
        }
    }

    public IUPCToBoardGameDAO getUPCToBoardGameDAO() {
        return DAOFactorySingleton.getInstance().makeUPCToBoardGameDAO();
    }
}
