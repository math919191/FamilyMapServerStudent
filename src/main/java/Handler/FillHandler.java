package Handler;

import Response.Response;
import Services.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")){

                String urlPath = exchange.getRequestURI().toString();
                String username = getUserNameFromUrl(urlPath);
                int generations = getGenerationsFromURL(urlPath);

                FillService service = new FillService();
                Response result = service.fill(username, generations);

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

        } catch (Exception e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
            exchange.getResponseBody().close();
            e.printStackTrace();
        }


    }

    private String getUserNameFromUrl(String url){
        url = url.substring(6);
        if (!url.contains("/")){
            return url;
        } else {
            return  url.substring(0, url.indexOf("/"));
        }
    }

    private int getGenerationsFromURL(String url){
        String last = url.substring(url.lastIndexOf('/')+1);
        if (isNumeric(last)){
            return Integer.valueOf(last);
        } else {
            return 4;
        }
    }

    //Bless you stackoverflow
    private static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }
}
