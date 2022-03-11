package DataAccess.DAO.Interfaces;

import java.util.List;

import DataAccess.DAO.DataAccessException;
import Entities.User;

public interface IUserDAO {
    User getUserById(String id) throws DataAccessException;
    List<User> getUsersByIds(List<String> ids, int offset) throws DataAccessException;
    User getUserByCredentials(String phoneNumber, String password) throws DataAccessException;
    List<User> getUsersMatchingCriteria(String searchCriteria, int offset) throws DataAccessException;
    boolean addUser(User user) throws DataAccessException;
    boolean updateUser(String id, User user) throws DataAccessException;
    boolean deleteUser(String id) throws DataAccessException;
    void clearUsersTable();
}