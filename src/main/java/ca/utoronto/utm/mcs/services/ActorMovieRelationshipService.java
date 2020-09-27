package ca.utoronto.utm.mcs.services;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public interface ActorMovieRelationshipService extends HttpHandler{

	public void addRelationship(HttpExchange r) throws Exception;
	public void getRelationship(HttpExchange r) throws Exception;
}
