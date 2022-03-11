package DataAccess.DAO.Interfaces;

import java.util.List;

import Exceptions.DataAccessException;
import Entities.GameNight;

public interface IGameNightDAO {
    GameNight getGameNightById(String id) throws DataAccessException;
    List<GameNight> getGameNightsByHostId(String hostId, int offset) throws DataAccessException;
    boolean addGameNight(GameNight gameNight) throws DataAccessException;
    boolean updateGameNight(String id, GameNight gameNight) throws DataAccessException;
    boolean deleteGameNight(String id) throws DataAccessException;
    void clearGameNightTable();
}
