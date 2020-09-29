package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.impl.ActorMovieRelationshipServiceImpl;

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
		if (deserialized.has("actorId"))
			relationship.setMovieID(deserialized.getString("movieId"));
		
		return relationship;
	}

	@Override
	public void handleGet(HttpExchange r) throws Exception {
		ActorMovieRelationshipService relationshipService = getRelationshipService();
		ActorMovieRelationship relationship = getRelationship(r);
//		relationshipService
		
	}

	@Override
	public void handlePost(HttpExchange r) throws Exception {
		ActorMovieRelationshipService relationshipService = getRelationshipService();
		ActorMovieRelationship relationship = getRelationship(r);
		
		if (relationship.getActorID() == null || relationship.getMovieID() == null) 
			throw new MissingInformationException("Required info is missing");
		relationshipService.addRelationship(relationship);
		r.sendResponseHeaders(200, -1);
	}
	
	private ActorMovieRelationshipService getRelationshipService() {
		if (relationshipService == null) relationshipService = new ActorMovieRelationshipServiceImpl(driver);
		return relationshipService;
	}
	
	

}
