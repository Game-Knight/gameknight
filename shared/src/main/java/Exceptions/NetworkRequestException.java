package Exceptions;

import java.util.List;

public class NetworkRequestException extends Exception {

    private String errorMessage;
    private String errorType;
    private List<String> errorStackTrace;

    public NetworkRequestException(String errorMessage, String errorType, List<String> errorStackTrace) {
        this.errorMessage = errorMessage;
        this.errorType = errorType;
        this.errorStackTrace = errorStackTrace;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public List<String> getErrorStackTrace() {
        return errorStackTrace;
    }

    public void setErrorStackTrace(List<String> errorStackTrace) {
        this.errorStackTrace = errorStackTrace;
    }
}
