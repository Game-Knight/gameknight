package com.game_knight.app.Services;

import Requests.GetUserRequest;
import Responses.GetUserResponse;
import Services.IGetUserService;

public class GetUserService extends BaseService implements IGetUserService {
    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        try {
            return getServerFacade().getUser(request);
        }
        catch (Exception ex) {
            return new GetUserResponse(false, ex.getMessage());
        }
    }
}
