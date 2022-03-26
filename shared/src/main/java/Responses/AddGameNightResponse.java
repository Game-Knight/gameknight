package Responses;

import Responses.Abstract.BaseResponse;

public class AddGameNightResponse extends BaseResponse {

    public AddGameNightResponse() {}

    public AddGameNightResponse(boolean success) {
        super(success);
    }

    public AddGameNightResponse(boolean success, String message) {
        super(success, message);
    }

    public AddGameNightResponse(boolean success, int errorCode, String message) {
        super(success, errorCode, message);
    }
}