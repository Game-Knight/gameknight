package DataAccess.DAO;

import DataAccess.DAO.InMemory.InMemoryDAOFactory;
import DataAccess.DAO.Interfaces.IDAOFactory;

public class DAOFactorySingleton {

    private static IDAOFactory daoFactory;

    private DAOFactorySingleton() {}

    public static IDAOFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new InMemoryDAOFactory();
        }

        return daoFactory;
    }
}