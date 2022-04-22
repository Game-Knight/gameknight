package Services;

import DataAccess.DAO.Factories.DAOFactorySingleton;
import DataAccess.DAO.Interfaces.IOwnershipDAO;
import DataAccess.DAO.Interfaces.IUPCToBoardGameDAO;
import Entities.Ownership;
import Exceptions.DataAccessException;
import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;

public class AddBoardGameService implements IAddBoardGameService {
    @Override
    public AddBoardGameResponse addBoardGame(AddBoardGameRequest request) {
        try {
            String boardGameId = getUPCToBoardGameDAO().getBoardGameIdByUPC(request.getUpc());
            getOwnershipDAO().addOwnership(request.getOwnerId(), boardGameId);

            return new AddBoardGameResponse(true);
        }
        catch (DataAccessException ex) {
            return new AddBoardGameResponse(false, ex.getErrorCode(), ex.getMessage());
        }
    }

    public IOwnershipDAO getOwnershipDAO() {
        return DAOFactorySingleton.getInstance().makeOwnershipDAO();
    }

    public IUPCToBoardGameDAO getUPCToBoardGameDAO() {
        return DAOFactorySingleton.getInstance().makeUPCToBoardGameDAO();
    }
}
