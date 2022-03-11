package DataAccess.DAO.InMemory;

import DataAccess.DAO.Interfaces.IDAOFactory;
import DataAccess.DAO.Interfaces.IUserDAO;

public class InMemoryDAOFactory implements IDAOFactory {

    @Override
    public IUserDAO makeUserDAO() {
        return new InMemoryUserDAO();
    }
}