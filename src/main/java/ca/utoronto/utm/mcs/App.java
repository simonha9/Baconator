package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.ActorServiceImpl;

public class App 
{
    static int PORT = 8080;
    public static void main(String[] args) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
        ActorService actorService = new ActorServiceImpl();
        server.createContext("/api/v1/addActor", actorService);
    }
}
