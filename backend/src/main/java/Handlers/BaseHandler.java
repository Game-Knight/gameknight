package Handlers;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

import Responses.Abstract.BaseResponse;

public abstract class BaseHandler implements HttpHandler {

    protected <T> T extractRequest(HttpExchange exchange, Class<T> requestClass) throws IOException {
        InputStream requestBody = exchange.getRequestBody();
        String requestData = readString(requestBody);
        Gson jsonRequest = new Gson();

        return jsonRequest.fromJson(requestData, requestClass);
    }

    protected <T extends BaseResponse> void sendResponse(HttpExchange exchange, T response) throws IOException {
        Gson jsonResponse = new Gson();
        String resultStr = jsonResponse.toJson(response);

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
    }

    protected void handleException(HttpExchange exchange, Exception ex) throws IOException {
        exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
        exchange.getResponseBody().close();

        ex.printStackTrace();
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

    private String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }
}