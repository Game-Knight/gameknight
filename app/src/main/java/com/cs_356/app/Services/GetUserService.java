package com.cs_356.app.Services;

import java.io.IOException;

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
