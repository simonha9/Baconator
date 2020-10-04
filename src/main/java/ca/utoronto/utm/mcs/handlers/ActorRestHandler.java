package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;

public class ActorRestHandler extends BaseHandler {

	ActorService actorService = null;

	public ActorRestHandler(Driver driver) {
		super(driver);
		actorService = new ActorServiceImpl(driver);
	}

	private Actor getActorFromRequestBody(HttpExchange r) throws IOException, JSONException {
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
		Actor actor = getActorFromRequestBody(r);
		actor = actorService.findActorById(actor.getId());
		String response = buildResponse(actor);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	@Override
	public void handlePut(HttpExchange r) throws Exception {
		Actor actor = getActorFromRequestBody(r);
		actorService.addActor(actor);
		r.sendResponseHeaders(200, -1);
	}

	private String buildResponse(Actor actor) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("movies", actor.getMovies());
		obj.accumulate("name", actor.getName());
		obj.accumulate("actorId", actor.getId());
		return obj.toString();
	}
	
}
