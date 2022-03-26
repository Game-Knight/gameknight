package com.cs_356.app.Presenters;

import com.cs_356.app.Services.GetUserService;

import Requests.GetUserRequest;
import Responses.GetUserResponse;

public class GetUserPresenter {

    public GetUserResponse getUser(GetUserRequest request) {
        return new GetUserService().getUser(request);
    }
}
