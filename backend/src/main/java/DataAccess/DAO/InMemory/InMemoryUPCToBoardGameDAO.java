package DataAccess.DAO.InMemory;

import DataAccess.DAO.Interfaces.IUPCToBoardGameDAO;
import DataAccess.DataGeneration.InMemoryDB;
import Exceptions.DataAccessException;

public class InMemoryUPCToBoardGameDAO implements IUPCToBoardGameDAO {
    @Override
    public String getBoardGameIdByUPC(String upc) throws DataAccessException {
        return InMemoryDB.getInstance().upcToBoardGameTable.get(upc);
    }
}
