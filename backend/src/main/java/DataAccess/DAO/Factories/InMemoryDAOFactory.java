package DataAccess.DAO.Factories;

import DataAccess.DAO.InMemory.InMemoryBoardGameDAO;
import DataAccess.DAO.InMemory.InMemoryFriendshipDAO;
import DataAccess.DAO.InMemory.InMemoryGameNightDAO;
import DataAccess.DAO.InMemory.InMemoryOwnershipDAO;
import DataAccess.DAO.InMemory.InMemoryUserDAO;
import DataAccess.DAO.Interfaces.IBoardGameDAO;
import DataAccess.DAO.Interfaces.IFriendshipDAO;
import DataAccess.DAO.Interfaces.IGameNightDAO;
import DataAccess.DAO.Interfaces.IOwnershipDAO;
import DataAccess.DAO.Interfaces.IUserDAO;

public class InMemoryDAOFactory implements IDAOFactory {

    @Override
    public IBoardGameDAO makeBoardGameDAO() {
        return new InMemoryBoardGameDAO();
    }

    @Override
    public IFriendshipDAO makeFriendshipDAO() {
        return new InMemoryFriendshipDAO();
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
    public IUserDAO makeUserDAO() {
        return new InMemoryUserDAO();
    }
}