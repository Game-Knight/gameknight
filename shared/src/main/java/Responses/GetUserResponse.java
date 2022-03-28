package Responses;

import Entities.User;
import Responses.Abstract.BaseResponse;

public class GetUserResponse extends BaseResponse {

    private User user;

    public GetUserResponse() {}

    public GetUserResponse(boolean success, User user) {
        super(success);
        this.user = user;
    }

    public GetUserResponse(boolean success, String message) {
        super(success, message);
    }

    public GetUserResponse(boolean success, int errorCode, String message) {
        super(success, errorCode, message);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
