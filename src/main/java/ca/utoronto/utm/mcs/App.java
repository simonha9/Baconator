package ca.utoronto.utm.mcs;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import ca.utoronto.utm.mcs.dao.Neo4jConnector;
import ca.utoronto.utm.mcs.handlers.ActorMovieRelationshipRestHandler;
import ca.utoronto.utm.mcs.handlers.ActorRestHandler;
import ca.utoronto.utm.mcs.handlers.BaconNumberRestHandler;
import ca.utoronto.utm.mcs.handlers.BaseHandler;
import ca.utoronto.utm.mcs.handlers.MovieRestHandler;

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
    	server.createContext("/api/v1/getActor", actorHandler);
    	
    	BaseHandler movieHandler = new MovieRestHandler(connector.getDriver());
    	server.createContext("/api/v1/addMovie", movieHandler);
    	server.createContext("/api/v1/getMovie", movieHandler);
//    	
    	BaseHandler relationshipHandler = new ActorMovieRelationshipRestHandler(connector.getDriver());
    	server.createContext("/api/v1/addRelationship", relationshipHandler);
    	server.createContext("/api/v1/hasRelationship", relationshipHandler);
    	
    	BaseHandler baconNumberHandler = new BaconNumberRestHandler(connector.getDriver());
    	server.createContext("/api/v1/computeBaconNumber", baconNumberHandler);
    }
}
