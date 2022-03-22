package DataAccess.DAO.Factories;

import DataAccess.DAO.InMemory.InMemoryBoardGameDAO;
import DataAccess.DAO.InMemory.InMemoryGameNightDAO;
import DataAccess.DAO.InMemory.InMemoryOwnershipDAO;
import DataAccess.DAO.InMemory.InMemoryUPCToBoardGameDAO;
import DataAccess.DAO.InMemory.InMemoryUserDAO;
import DataAccess.DAO.Interfaces.IBoardGameDAO;
import DataAccess.DAO.Interfaces.IGameNightDAO;
import DataAccess.DAO.Interfaces.IOwnershipDAO;
import DataAccess.DAO.Interfaces.IUPCToBoardGameDAO;
import DataAccess.DAO.Interfaces.IUserDAO;

public class InMemoryDAOFactory implements IDAOFactory {

    @Override
    public IBoardGameDAO makeBoardGameDAO() {
        return new InMemoryBoardGameDAO();
    }

    @Override
    public IGameNightDAO makeGameNightDAO() {
        return new InMemoryGameNightDAO();
    }

    @Override
    public IOwnershipDAO makeOwnershipDAO() {
        return new InMemoryOwnershipDAO();
    }

    @Override
    public IUPCToBoardGameDAO makeUPCToBoardGameDAO() {
        return new InMemoryUPCToBoardGameDAO();
    }

    @Override
    public IUserDAO makeUserDAO() {
        return new InMemoryUserDAO();
    }
}