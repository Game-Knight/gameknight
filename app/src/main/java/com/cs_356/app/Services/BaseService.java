package com.cs_356.app.Services;

import com.cs_356.app.Net.ServerFacade;

public abstract class BaseService {

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
