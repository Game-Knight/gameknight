package DataAccess.DAO.Interfaces;

import java.util.List;

import Exceptions.DataAccessException;

public interface IOwnershipDAO {
    List<String> getBoardGameIdsByOwner(String ownerId, int offset) throws DataAccessException;
}
