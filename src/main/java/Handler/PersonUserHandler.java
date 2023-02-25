package Handler;

import Response.Response;
import Services.PersonUserService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class PersonUserHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();

        if (exchange.getRequestMethod().toLowerCase().equals("get")) {

            Headers reqHeaders = exchange.getRequestHeaders();
            if (reqHeaders.containsKey("Authorization")) {
                String authToken = reqHeaders.getFirst("Authorization");

                PersonUserService service = new PersonUserService();
                Response result = service.personUser(authToken);

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
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
        }

    }
}
