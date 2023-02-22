package Handler;

import Request.UserLoginRequest;
import Request.UserRegisterRequest;
import Response.Response;
import Services.FillService;
import Services.UserLoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class UserLoginHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        boolean success = false;
        Gson gson = new Gson();
        try {
            if (exchange.getRequestMethod().toLowerCase().equals("post")){
                InputStream reqBody = exchange.getRequestBody();
                String reqData = readString(reqBody);
                UserLoginRequest request = (UserLoginRequest)gson.fromJson(reqData, UserLoginRequest.class);


                UserLoginService service = new UserLoginService();
                Response result = service.userLogin(request);

                String respData = gson.toJson(result).toString();


                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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

    private void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }
}
