package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import ca.utoronto.utm.mcs.dao.Neo4jConnector;
import ca.utoronto.utm.mcs.handlers.ActorRestHandler;
import ca.utoronto.utm.mcs.handlers.BaseHandler;

public class App 
{
    static int PORT = 8080;
    public static void main(String[] args) throws IOException
    {
        HttpServer server = HttpServer.create(new InetSocketAddress("0.0.0.0", PORT), 0);
        server.start();
        System.out.printf("Server started on port %d...\n", PORT);
        Neo4jConnector connector = Neo4jConnector.getInstance();
        createContexts(server, connector);
    }
    
    private static void createContexts(HttpServer server, Neo4jConnector connector) {
    	BaseHandler actorHandler = new ActorRestHandler(connector.getDriver());
    	server.createContext("/api/v1/addActor", (HttpHandler) actorHandler);
//    	server.createContext("/api/v1/getActor", actorHandler);
    	
//    	MovieService movieService = new MovieServiceImpl();
//    	server.createContext("/api/v1/addMovie", movieService);
//    	server.createContext("/api/v1/getMovie", movieService);
//    	
//    	ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl();
//    	server.createContext("/api/v1/addRelationship", relationshipService);
//    	server.createContext("/api/v1/getRelationship", relationshipService);
    }
}
