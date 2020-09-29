package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.MovieService;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;
import ca.utoronto.utm.mcs.services.impl.MovieServiceImpl;

public class App 
{
    static int PORT = 8080;
    public static void main(String[] args) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
        createContexts(server);
    }
    
    private static void createContexts(HttpServer server) {
    	ActorService actorService = new ActorServiceImpl();
    	server.createContext("/api/v1/addActor", actorService);
    	server.createContext("/api/v1/getActor", actorService);
    	
    	MovieService movieService = new MovieServiceImpl();
    	server.createContext("/api/v1/addMovie", movieService);
    	server.createContext("/api/v1/getMovie", movieService);
    }
}
