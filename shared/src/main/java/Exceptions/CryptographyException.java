package Exceptions;

public class CryptographyException extends Exception {

    private String message;

    public CryptographyException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
