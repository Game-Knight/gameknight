package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import Requests.AddBoardGameRequest;
import Responses.AddBoardGameResponse;
import Services.AddBoardGameService;

public class AddBoardGameHandler extends BaseHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;

        try {
            // Because clear is a POST method, make sure post is in the exchange request
            if (exchange.getRequestMethod().equalsIgnoreCase("post")) {

                // Get the request
                InputStream requestBody = exchange.getRequestBody();
                String requestData = readString(requestBody);
                Gson jsonRequest = new Gson();

                AddBoardGameRequest request = jsonRequest.fromJson(requestData, AddBoardGameRequest.class);
                AddBoardGameService service = new AddBoardGameService();
                AddBoardGameResponse response = service.addBoardGame(request);

                Gson jsonResponse = new Gson();
                String resultStr = jsonResponse.toJson(response);

                // Start sending the HTTP response to the client
                if (response.isSuccess()) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }

                // Set the response
                OutputStream responseBody = exchange.getResponseBody();
                responseBody.write(resultStr.getBytes());
                responseBody.close();

                success = true;
            }

            if (!success) {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        }
        catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            exchange.getResponseBody().close();

            e.printStackTrace();
        }
    }
}