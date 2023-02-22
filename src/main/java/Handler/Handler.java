package Handler;

import Services.ValidateAuthtokenService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;

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

    public boolean isAuthTokenValid(String authToken) {
        return new ValidateAuthtokenService().validateAuthToken(authToken);
    }


}
