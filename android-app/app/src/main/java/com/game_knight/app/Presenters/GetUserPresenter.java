package com.game_knight.app.Presenters;

import com.game_knight.app.Services.GetUserService;

import Requests.GetUserRequest;
import Responses.GetUserResponse;

public class GetUserPresenter {

    public GetUserResponse getUser(GetUserRequest request) {
        return new GetUserService().getUser(request);
    }
}
