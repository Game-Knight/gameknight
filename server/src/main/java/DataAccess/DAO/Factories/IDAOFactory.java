package DataAccess.DAO.Factories;

import DataAccess.DAO.Interfaces.IBoardGameDAO;
import DataAccess.DAO.Interfaces.IGameNightDAO;
import DataAccess.DAO.Interfaces.IOwnershipDAO;
import DataAccess.DAO.Interfaces.IUPCToBoardGameDAO;
import DataAccess.DAO.Interfaces.IUserDAO;

public interface IDAOFactory {
    IBoardGameDAO makeBoardGameDAO();
    IGameNightDAO makeGameNightDAO();
    IOwnershipDAO makeOwnershipDAO();
    IUPCToBoardGameDAO makeUPCToBoardGameDAO();
    IUserDAO makeUserDAO();
}