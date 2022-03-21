package Startup;

import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;
import Services.AddBoardGameService;

public class BackendStartup {
    public static void main( String... args ) {
        System.out.println("Running Game Night Backend...");
        AddBoardGameService service = new AddBoardGameService();
        AddBoardGameResponse response = service.addBoardGame(new AddBoardGameRequest("681706711003"));
        System.out.println(response.isSuccess());
    }
}