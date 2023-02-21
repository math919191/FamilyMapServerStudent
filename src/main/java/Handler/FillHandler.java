package Handler;

import Model.Data.LoadData;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

public class FillHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        LoadData loadData = new LoadData();

        loadData.getLocationData();
    }
}
