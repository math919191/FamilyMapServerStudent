package Handler;

import Model.Event;
import Response.Response;
import Services.EventAllService;
import Services.PersonUserService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class EventAllHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")) {

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")) {
                    String authToken = reqHeaders.getFirst("Authorization");

//                    if (!isAuthTokenValid(authToken)){
//                        throw new Exception("invalid authtoken");
//                    }

                    EventAllService service = new EventAllService();
                    Response result = service.eventAll(authToken);

                    String respData = gson.toJson(result).toString();

                    int responseVal = getHTTPResponseVal(result);
                    exchange.sendResponseHeaders(responseVal, 0);

                    OutputStream respBody = exchange.getResponseBody();

                    writeString(respData, respBody);
                    respBody.close();

                    success = true;
                }
            }

            if (!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
                exchange.getResponseBody().close();
            }
        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }
    }

}

