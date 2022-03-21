package DataAccess.DAO.Factories;

import DataAccess.DAO.Factories.InMemoryDAOFactory;
import DataAccess.DAO.Factories.IDAOFactory;

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