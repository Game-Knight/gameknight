package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;
import Services.AddBoardGameService;
import Utils.HttpUtils;

public class GetUserHandler  extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase(HttpUtils.POST_METHOD)) {
                AddBoardGameRequest request = extractRequest(exchange, AddBoardGameRequest.class);

                AddBoardGameService service = new AddBoardGameService();
                AddBoardGameResponse response = service.addBoardGame(request);

                sendResponse(exchange, response);
            }
        }
        catch (Exception ex) {
            handleException(exchange, ex);
        }
    }
}
