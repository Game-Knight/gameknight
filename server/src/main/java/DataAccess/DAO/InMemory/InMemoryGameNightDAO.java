package DataAccess.DAO.InMemory;

import java.util.List;

import Exceptions.DataAccessException;
import DataAccess.DAO.Interfaces.IGameNightDAO;
import Entities.GameNight;

public class InMemoryGameNightDAO implements IGameNightDAO {
    @Override
    public GameNight getGameNightById(String id) throws DataAccessException {
        return null;
    }

    @Override
    public List<GameNight> getGameNightsByHostId(String hostId, int offset) throws DataAccessException {
        return null;
    }

    @Override
    public boolean addGameNight(GameNight gameNight) throws DataAccessException {
        return false;
    }

    @Override
    public boolean updateGameNight(String id, GameNight gameNight) throws DataAccessException {
        return false;
    }

    @Override
    public boolean deleteGameNight(String id) throws DataAccessException {
        return false;
    }
}
