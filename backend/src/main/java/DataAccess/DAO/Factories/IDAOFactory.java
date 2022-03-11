package DataAccess.DAO.Factories;

import DataAccess.DAO.Interfaces.IBoardGameDAO;
import DataAccess.DAO.Interfaces.IFriendshipDAO;
import DataAccess.DAO.Interfaces.IGameNightDAO;
import DataAccess.DAO.Interfaces.IOwnershipDAO;
import DataAccess.DAO.Interfaces.IUserDAO;

public interface IDAOFactory {
    IBoardGameDAO makeBoardGameDAO();
    IFriendshipDAO makeFriendshipDAO();
    IGameNightDAO makeGameNightDAO();
    IOwnershipDAO makeOwnershipDAO();
    IUserDAO makeUserDAO();
}