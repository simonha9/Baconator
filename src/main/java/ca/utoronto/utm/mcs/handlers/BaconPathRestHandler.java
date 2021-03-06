package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NoPathException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;

public class BaconPathRestHandler extends BaseHandler {

	ActorService actorService = null;

	public BaconPathRestHandler(Driver driver) {
		super(driver);
		actorService = new ActorServiceImpl(driver);
	}
	
	@Override
	public void handle(HttpExchange r) throws IOException {
		try {
			if (r.getRequestMethod().equals("GET")) {
				handleGet(r);
			} 
		} catch (MissingInformationException | JSONException  | NodeAlreadyExistsException | NodeNotExistException e) {
			r.sendResponseHeaders(400, -1);
		} catch (NoPathException e) {
			r.sendResponseHeaders(404, -1);
		} catch (Exception e) {
			r.sendResponseHeaders(500, -1);
		}
	}

	private Actor getActor(HttpExchange r) throws IOException, JSONException {
		JSONObject deserialized = convertRequestToJSON(r);
		Actor actor = new Actor();
		if (deserialized.has("actorId"))
			actor.setId(deserialized.getString("actorId"));
		return actor;
	}
	
	@Override
	public void handleGet(HttpExchange r) throws Exception {
		Actor actor = getActor(r);
		List<ActorMovieRelationship> rels = new ArrayList<>();
		Integer baconNumber = actorService.computeBaconNumber(actor.getId());
		rels = actorService.computeBaconPath(actor.getId());
		String response = buildResponse(rels, baconNumber);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	private String buildResponse(List<ActorMovieRelationship> rels, Integer baconNumber) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("baconNumber", baconNumber.toString());
		JSONArray arr = new JSONArray();
		for (ActorMovieRelationship rel : rels) {
			JSONObject nested = new JSONObject();
			nested.accumulate("actorId", rel.getActorID());
			nested.accumulate("movieId", rel.getMovieID());
			arr.put(nested);
		}
		obj.put("baconPath", arr);
		return obj.toString();
	}

	@Override
	public void handlePut(HttpExchange r) throws Exception {

	}

	private String buildResponse(Integer baconNumber) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("baconNumber", baconNumber);
		return obj.toString();
	}

}
