package Server;

import java.io.*;
import java.net.*;

import Handler.*;
import Model.Data.LoadData;
import com.sun.net.httpserver.*;

public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;

    private HttpServer server;

    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        }
        catch (IOException e) {
            e.printStackTrace();
            return;
        }
        server.setExecutor(null);

        System.out.println("Loading Data");


        System.out.println("Creating contexts");
        server.createContext("/user/register", new UserRegisterHandler());
        server.createContext("/user/login", new UserLoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill/", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person/", new PersonIDHandler());
        server.createContext("/person", new PersonUserHandler());
        server.createContext("/event/", new EventIDHandler());
        server.createContext("/event", new EventAllHandler());

        server.createContext("/", new FileHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
