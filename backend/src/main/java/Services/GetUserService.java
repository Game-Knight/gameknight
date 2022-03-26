package Services;

import DataAccess.DAO.Factories.DAOFactorySingleton;
import DataAccess.DAO.Interfaces.IUserDAO;
import Entities.User;
import Exceptions.DataAccessException;
import Requests.GetUserRequest;
import Responses.GetUserResponse;

public class GetUserService implements IGetUserService {
    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        try {
            User user = getUserDAO().getUserById(request.getPhoneNumber());
            return new GetUserResponse(true, user);
        }
        catch (DataAccessException ex) {
            return new GetUserResponse(false, ex.getErrorCode(), ex.getMessage());
        }
    }

    public IUserDAO getUserDAO() {
        return DAOFactorySingleton.getInstance().makeUserDAO();
    }
}
