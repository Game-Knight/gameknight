package DataAccess.DAO.Interfaces;

import java.util.List;

import Exceptions.DataAccessException;
import Entities.Friendship;

public interface IFriendshipDAO {
    boolean friendshipExists(Friendship friendship) throws DataAccessException;
    List<String> getFriendIdsOfUser(String userId, int offset) throws DataAccessException;
    boolean addFriendship(Friendship friendship) throws DataAccessException;
    boolean deleteFriendship(Friendship friendship) throws DataAccessException;
    void clearFriendshipsTable();
}