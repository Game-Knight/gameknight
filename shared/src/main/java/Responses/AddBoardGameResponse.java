package Responses;

import Responses.Abstract.BaseResponse;

public class AddBoardGameResponse extends BaseResponse {

    public AddBoardGameResponse() {}

    public AddBoardGameResponse(boolean success) {
        super(success);
    }

    public AddBoardGameResponse(boolean success, String message) {
        super(success, message);
    }

    public AddBoardGameResponse(boolean success, int errorCode, String message) {
        super(success, errorCode, message);
    }
}