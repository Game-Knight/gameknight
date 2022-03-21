package DataAccess.DAO.InMemory;

import java.util.List;

import Exceptions.DataAccessException;
import DataAccess.DAO.Interfaces.IUserDAO;
import Entities.User;

public class InMemoryUserDAO implements IUserDAO {

    @Override
    public User getUserById(String id) throws DataAccessException {
        return null;
    }

    @Override
    public List<User> getUsersByIds(List<String> ids, int offset) throws DataAccessException {
        return null;
    }

    @Override
    public User getUserByCredentials(String phoneNumber, String password) throws DataAccessException {
        return null;
    }

    @Override
    public List<User> getUsersMatchingCriteria(String searchCriteria, int offset) throws DataAccessException {
        return null;
    }

    @Override
    public boolean addUser(User user) throws DataAccessException {
        return false;
    }

    @Override
    public boolean updateUser(String id, User user) throws DataAccessException {
        return false;
    }

    @Override
    public boolean deleteUser(String id) throws DataAccessException {
        return false;
    }

    @Override
    public void clearUsersTable() {

    }
}
