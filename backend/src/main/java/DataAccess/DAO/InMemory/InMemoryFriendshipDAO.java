package DataAccess.DAO.InMemory;

import java.util.List;

import Exceptions.DataAccessException;
import DataAccess.DAO.Interfaces.IFriendshipDAO;
import Entities.Friendship;

public class InMemoryFriendshipDAO implements IFriendshipDAO {
    @Override
    public boolean friendshipExists(Friendship friendship) throws DataAccessException {
        return false;
    }

    @Override
    public List<String> getFriendIdsOfUser(String userId, int offset) throws DataAccessException {
        return null;
    }

    @Override
    public boolean addFriendship(Friendship friendship) throws DataAccessException {
        return false;
    }

    @Override
    public boolean deleteFriendship(Friendship friendship) throws DataAccessException {
        return false;
    }

    @Override
    public void clearFriendshipsTable() {

    }
}
