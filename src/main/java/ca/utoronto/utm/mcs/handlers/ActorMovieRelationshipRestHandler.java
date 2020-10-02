package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.MovieService;
import ca.utoronto.utm.mcs.services.impl.ActorMovieRelationshipServiceImpl;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;
import ca.utoronto.utm.mcs.services.impl.MovieServiceImpl;

public class ActorMovieRelationshipRestHandler extends BaseHandler {
	
	ActorMovieRelationshipService relationshipService = null;
	
	public ActorMovieRelationshipRestHandler(Driver driver) {
		super(driver);
	}
	
	private ActorMovieRelationship getRelationship(HttpExchange r) throws IOException, JSONException {
		JSONObject deserialized = convertRequestToJSON(r);
		ActorMovieRelationship relationship = new ActorMovieRelationship();
		if (deserialized.has("actorId")) 
			relationship.setActorID(deserialized.getString("actorId"));
		if (deserialized.has("movieId"))
			relationship.setMovieID(deserialized.getString("movieId"));
		
		return relationship;
	}

	@Override
	public void handleGet(HttpExchange r) throws Exception {
		ActorMovieRelationshipService relationshipService = getRelationshipService();
		ActorMovieRelationship relationship = getRelationship(r);
		relationship = relationshipService.getRelationship(relationship);
		relationship.setHasRelationship(relationship == null ? null : relationship.getActorID() != null && relationship.getMovieID() != null);
		String response = buildResponse(relationship);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	@Override
	public void handlePost(HttpExchange r) throws Exception {
		ActorMovieRelationshipService relationshipService = getRelationshipService();
		ActorMovieRelationship relationship = getRelationship(r);
		relationshipService.addRelationship(relationship);
		r.sendResponseHeaders(200, -1);
	}
	
	private ActorMovieRelationshipService getRelationshipService() {
		if (relationshipService == null) relationshipService = new ActorMovieRelationshipServiceImpl(driver);
		return relationshipService;
	}
	
	
	private String buildResponse(ActorMovieRelationship relationship) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("actorId", relationship.getActorID());
		obj.accumulate("movieId", relationship.getMovieID());
		obj.accumulate("hasRelationship", relationship.getHasRelationship());
		return obj.toString();
	}
	
	
	

}
