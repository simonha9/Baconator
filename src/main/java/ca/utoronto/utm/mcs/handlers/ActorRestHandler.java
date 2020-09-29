package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;

public class ActorRestHandler extends BaseHandler {
	
	public ActorRestHandler(Driver driver) {
		super(driver);
	}
	
	private Actor getActor(HttpExchange r) throws IOException, JSONException {
		JSONObject deserialized = convertRequestToJSON(r);
		Actor actor = new Actor();
		if (deserialized.has("name")) 
			actor.setPrimaryName(deserialized.getString("name"));
		if (deserialized.has("actorId"))
			actor.setId(deserialized.getString("actorId"));
		
		return actor;
	}

	@Override
	public void handleGet(HttpExchange r) throws Exception {
		ActorService actorService = new ActorServiceImpl(driver);
		Actor actor = getActor(r);
		actorService.getActor(actor.getId());
	}

	@Override
	public void handlePost(HttpExchange r) throws Exception {
		ActorService actorService = new ActorServiceImpl(driver);
		Actor actor = getActor(r);
		if (actor.getPrimaryName() == null || actor.getId() == null) 
			throw new MissingInformationException("Required info is missing");
		actorService.addActor(actor);
		r.sendResponseHeaders(200, -1);
	}
	
	
	

}
