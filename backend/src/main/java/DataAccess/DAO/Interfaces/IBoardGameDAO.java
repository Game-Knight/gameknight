package DataAccess.DAO.Interfaces;

import java.util.List;

import Exceptions.DataAccessException;
import Entities.BoardGame;

public interface IBoardGameDAO {
    BoardGame getBoardGameById(String id) throws DataAccessException;
    List<BoardGame> getBoardGamesByIds(List<String> ids, int offset) throws DataAccessException;
    List<BoardGame> getBoardGamesMatchingCriteria(String ownerId, String searchCriteria, int offset) throws DataAccessException;
    boolean addBoardGame(BoardGame boardGame) throws DataAccessException;
    boolean updateBoardGame(String id, BoardGame boardGame) throws DataAccessException;
    boolean deleteBoardGame(String id) throws DataAccessException;
    void clearBoardGamesTable();
}