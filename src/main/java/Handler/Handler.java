package Handler;

import Response.Response;
import Response.ErrorResponse;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.HttpURLConnection;

public class Handler implements HttpHandler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {}

    public String readString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader sr = new InputStreamReader(is);
        char[] buf = new char[1024];
        int len;
        while ((len = sr.read(buf)) > 0) {
            sb.append(buf, 0, len);
        }
        return sb.toString();
    }

    public void writeString(String str, OutputStream os) throws IOException {
        OutputStreamWriter sw = new OutputStreamWriter(os);
        sw.write(str);
        sw.flush();
    }


    public int getHTTPResponseVal(Response result){
        int responseVal;
        if (result instanceof ErrorResponse){
            responseVal = HttpURLConnection.HTTP_BAD_REQUEST;

        } else {
            responseVal = HttpURLConnection.HTTP_OK;
        }
        return responseVal;
    }


}
