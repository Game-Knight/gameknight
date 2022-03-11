package DataAccess.DAO.InMemory;

import java.util.List;

import Exceptions.DataAccessException;
import DataAccess.DAO.Interfaces.IOwnershipDAO;

public class InMemoryOwnershipDAO implements IOwnershipDAO {
    @Override
    public List<String> getBoardGameIdsByOwner(String ownerId, int offset) throws DataAccessException {
        return null;
    }
}
