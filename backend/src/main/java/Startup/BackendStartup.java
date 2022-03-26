package Startup;

import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;

import DataAccess.DataGeneration.InMemoryDB;
import Handlers.AddBoardGameHandler;
import Handlers.GetUserHandler;
import Utils.HttpUtils;

public class BackendStartup {
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private void run() {
        System.out.println("Running Game Night Backend...");

        try {
            System.out.println("Initializing InMemoryDB...");
            InMemoryDB.getInstance();
            System.out.println("InMemoryDB initialized!");
        }
        catch (Exception ex) {
            System.err.println("There was a fatal error initializing the InMemoryDB! " + ex.getMessage());
            return;
        }

        HttpServer server;
        try {
            System.out.println("Initializing HTTP Server...");
            server = HttpServer.create(
                    new InetSocketAddress(HttpUtils.PORT_NUMBER),
                    MAX_WAITING_CONNECTIONS
            );
            System.out.println("HTTP Server initialized!");
        }
        catch (Exception ex) {
            System.err.println("There was a fatal error initializing the HTTP Server! " + ex.getMessage());
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating request handler contexts...");

        server.createContext(HttpUtils.ADD_BOARD_GAME_EXT, new AddBoardGameHandler());
        server.createContext(HttpUtils.GET_USER_EXT, new GetUserHandler());
        // TODO: Add more request handlers!

        System.out.println("Starting server...");
        server.start();
        System.out.println("Server started!");
    }

    public static void main( String... args ) {
        new BackendStartup().run();
    }
}