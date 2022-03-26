package Handlers;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import Requests.GetUserRequest;
import Responses.GetUserResponse;
import Services.GetUserService;
import Utils.HttpUtils;

public class AddBoardGameHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            if (exchange.getRequestMethod().equalsIgnoreCase(HttpUtils.POST_METHOD)) {
                GetUserRequest request = extractRequest(exchange, GetUserRequest.class);

                GetUserService service = new GetUserService();
                GetUserResponse response = service.getUser(request);

                sendResponse(exchange, response);
            }
        }
        catch (Exception ex) {
            handleException(exchange, ex);
        }
    }
}