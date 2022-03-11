package DataAccess.DAO.Interfaces;

public interface IDAOFactory {
    IUserDAO makeUserDAO();
}