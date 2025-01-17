package Handler;

import Request.UserRegisterRequest;
import Response.UserRegisterResponse;
import Response.Response;
import Services.UserRegisterService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import passoffrequest.LoginRequest;
import passoffrequest.RegisterRequest;
import passoffresult.RegisterResult;

import java.io.*;
import java.net.HttpURLConnection;

public class UserRegisterHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        boolean success = false;
        Gson gson = new Gson();

        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")) {
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                UserRegisterRequest request = (UserRegisterRequest)gson.fromJson(reqData, UserRegisterRequest.class);

                UserRegisterService service = new UserRegisterService();
                Response result = service.userRegister(request);

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

}
