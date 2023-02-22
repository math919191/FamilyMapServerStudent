package Handler;

import Response.Response;
import Services.FillService;
import Services.PersonIDService;
import Services.ValidateAuthtokenService;
import com.google.gson.Gson;
import com.sun.net.httpserver.*;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonIDHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("get")){

                Headers reqHeaders = exchange.getRequestHeaders();
                if (reqHeaders.containsKey("Authorization")){
                    String authToken = reqHeaders.getFirst("Authorization");

                    if (!isAuthTokenValid(authToken)){
                        throw new Exception("invalid authtoken");
                    }
                    String urlPath = exchange.getRequestURI().toString();
                    String personID = urlPath.substring(urlPath.lastIndexOf("/"));

                    PersonIDService service = new PersonIDService();
                    Response result = service.getPersonFromPersonID(personID, authToken);

                    String respData = gson.toJson(result).toString();
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

        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }

    private boolean isAuthTokenValid(String authToken) {
        return new ValidateAuthtokenService().validateAuthToken(authToken);
    }

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}