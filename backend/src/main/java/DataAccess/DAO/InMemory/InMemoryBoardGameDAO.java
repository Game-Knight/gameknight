package DataAccess.DAO.InMemory;

import java.util.List;

import Exceptions.DataAccessException;
import DataAccess.DAO.Interfaces.IBoardGameDAO;
import Entities.BoardGame;

public class InMemoryBoardGameDAO implements IBoardGameDAO {
    @Override
    public BoardGame getBoardGameById(String id) throws DataAccessException {
        return null;
    }

    @Override
    public List<BoardGame> getBoardGamesByIds(List<String> ids, int offset) throws DataAccessException {
        return null;
    }

    @Override
    public List<BoardGame> getBoardGamesMatchingCriteria(String ownerId, String searchCriteria, int offset) throws DataAccessException {
        return null;
    }

    @Override
    public boolean addBoardGame(BoardGame boardGame) throws DataAccessException {
        return false;
    }

    @Override
    public boolean updateBoardGame(String id, BoardGame boardGame) throws DataAccessException {
        return false;
    }

    @Override
    public boolean deleteBoardGame(String id) throws DataAccessException {
        return false;
    }

    @Override
    public void clearBoardGamesTable() {

    }
}
