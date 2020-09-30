package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;

public class ActorRestHandler extends BaseHandler {

	ActorService actorService = null;

	public ActorRestHandler(Driver driver) {
		super(driver);
	}

	private Actor getActor(HttpExchange r) throws IOException, JSONException {
		JSONObject deserialized = convertRequestToJSON(r);
		Actor actor = new Actor();
		if (deserialized.has("name"))
			actor.setName(deserialized.getString("name"));
		if (deserialized.has("actorId"))
			actor.setId(deserialized.getString("actorId"));

		return actor;
	}

	@Override
	public void handleGet(HttpExchange r) throws Exception {
		ActorService actorService = getActorService();
		Actor actor = getActor(r);
		if (actor.getId() == null) throw new MissingInformationException("Required info is missing");
		actor = actorService.getActorByID(actor.getId());
		if (actor == null) throw new NodeNotExistException("That node does not exist");
		String response = buildResponse(actor);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	@Override
	public void handlePost(HttpExchange r) throws Exception {
		ActorService actorService = getActorService();
		Actor actor = getActor(r);
		if (actor.getName() == null || actor.getId() == null)
			throw new MissingInformationException("Required info is missing");
		Actor existingActor = actorService.getActorByID(actor.getId());
		if (existingActor != null) throw new NodeAlreadyExistsException("That actor already exists");
		actorService.addActor(actor);
		r.sendResponseHeaders(200, -1);
	}

	private ActorService getActorService() {
		if (actorService == null)
			actorService = new ActorServiceImpl(driver);
		return actorService;
	}

	private String buildResponse(Actor actor) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("movies", actor.getMovies());
		obj.accumulate("name", actor.getName());
		obj.accumulate("actorId", actor.getId());
		return obj.toString();
	}
	
}
