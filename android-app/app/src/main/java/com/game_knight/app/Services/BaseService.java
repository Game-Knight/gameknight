package com.game_knight.app.Services;

import com.game_knight.app.Net.ServerFacade;

public abstract class BaseService {

    ServerFacade getServerFacade() {
        return new ServerFacade();
    }
}
