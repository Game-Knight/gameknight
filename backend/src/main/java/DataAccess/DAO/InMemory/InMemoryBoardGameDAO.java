package DataAccess.DAO.InMemory;

import java.util.ArrayList;
import java.util.List;

import DataAccess.DataGeneration.InMemoryDB;
import Exceptions.DataAccessException;
import DataAccess.DAO.Interfaces.IBoardGameDAO;
import Entities.BoardGame;

public class InMemoryBoardGameDAO implements IBoardGameDAO {
    @Override
    public BoardGame getBoardGameById(String id) throws DataAccessException {
        return InMemoryDB.getInstance().boardGameTable.get(id);
    }

    @Override
    public List<BoardGame> getBoardGamesByIds(List<String> ids, int offset) throws DataAccessException {
        List<BoardGame> boardGames = new ArrayList<>();
        for (String id : ids) {
            boardGames.add(InMemoryDB.getInstance().boardGameTable.get(id));
        }
        return boardGames;
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
