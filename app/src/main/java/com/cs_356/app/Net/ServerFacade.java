package com.cs_356.app.Net;

import java.io.IOException;

import Exceptions.NetworkRequestException;
import Requests.GetUserRequest;
import Responses.GetUserResponse;
import Utils.HttpUtils;

public class ServerFacade {

    public GetUserResponse getUser(GetUserRequest request) throws IOException, NetworkRequestException {
        return new ClientCommunicator().doPost(HttpUtils.GET_USER_EXT, request, null, GetUserResponse.class);
    }
}
