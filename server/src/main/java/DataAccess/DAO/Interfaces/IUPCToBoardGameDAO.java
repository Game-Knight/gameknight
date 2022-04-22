package DataAccess.DAO.Interfaces;

import Exceptions.DataAccessException;

public interface IUPCToBoardGameDAO {
    String getBoardGameIdByUPC(String upc) throws DataAccessException;
}
