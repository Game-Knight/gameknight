package DataAccess.DAO.Interfaces;

import java.util.List;

import Exceptions.DataAccessException;
import Entities.BoardGame;

public interface IBoardGameDAO {
    BoardGame getBoardGameById(String id) throws DataAccessException;
    List<BoardGame> getBoardGamesByIds(List<String> ids, int offset) throws DataAccessException;
}