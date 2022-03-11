package DataAccess.DAO;

public class DataAccessException extends Exception {

    private int errorCode;
    private String message;

    public DataAccessException(String message) {
        setMessage(message);
    }

    public DataAccessException(int errorCode, String message) {
        setErrorCode(errorCode);
        setMessage(message);
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getErrorCodeAndMessage() {
        return errorCode + ": " + message;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}