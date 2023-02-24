package Handler;

import Response.ClearResponse;
import Response.Response;

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

public class ClearHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();

        if (exchange.getRequestMethod().toLowerCase().equals("post")) {

            ClearService service = new ClearService();
            Response result = service.clear();

            String respData = gson.toJson(result).toString();


            int responseVal = getHTTPResponseVal(result);
            exchange.sendResponseHeaders(responseVal, 0);

            OutputStream respBody = exchange.getResponseBody();

            writeString(respData, respBody);
            respBody.close();

            success = true;

        }

        if (!success){
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            exchange.getResponseBody().close();
        }


    }

}
