package Handler;

import Response.ClearResponse;
import Services.ClearService;
import com.google.gson.Gson;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;

import static java.nio.file.Files.readString;

public class ClearHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                Headers reqHeaders = exchange.getRequestHeaders();
                /** if (reqHeaders.containsKey("Authorization")){
                    String authToken = reqHeaders.getFirst("Authorization");
                    if (authToken.equals("a real token"))
                }
                */

                ClearService service = new ClearService();
                ClearResponse result = service.clear();

                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                OutputStream resBody = exchange.getResponseBody();
                //TODO double check the correctness of next line
                resBody.write(gson.toJson(result).getBytes());
                resBody.close();

                success = true;

            }

            if (!success){
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                exchange.getResponseBody().close();
            }
        } catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }

    }
    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }

}
