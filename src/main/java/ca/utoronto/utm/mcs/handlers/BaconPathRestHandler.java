package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

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
	}
	
	@Override
	public void handle(HttpExchange r) throws IOException {
		try {
			if (r.getRequestMethod().equals("GET")) {
				handleGet(r);
			} else if (r.getRequestMethod().equals("POST")) {
				handlePost(r);
			}
		} catch (MissingInformationException | JSONException  | NodeAlreadyExistsException | NodeNotExistException e) {
			e.printStackTrace();
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
		ActorService actorService = getActorService();
		Actor actor = getActor(r);
		List<ActorMovieRelationship> rels = new ArrayList<>();
		if (actor.getId() == null)
			throw new MissingInformationException("Required Information does not exist");
		actor = actorService.findActorById(actor.getId());
		if (actor == null)
			throw new NodeNotExistException("That node does not exist");
		Actor kevinB = actorService.findActorById(kevinBaconId);
		if (kevinB == null) 
			throw new NodeNotExistException("That node does not exist");
		
		Integer baconNumber = actorService.computeBaconNumber(actor);
		
		if (!actor.getName().equals("Kevin Bacon")) {
			rels = actorService.computeBaconPath(actor);
//			for (ActorMovieRelationship rel : rels) {
//				System.out.println("Actorid: " + rel.getActorID());
//				System.out.println("Movieid: " + rel.getMovieID());
//			}
		}
		if (rels == null) throw new NoPathException("There does not exist a path to Kevin Bacon");
		String response = buildResponse(rels, baconNumber);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
	}

	private String buildResponse(List<ActorMovieRelationship> rels, Integer baconNumber) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("baconNumber", baconNumber);
		for (ActorMovieRelationship rel : rels) {
			JSONObject nested = new JSONObject();
			nested.accumulate("actorId", rel.getActorID());
			nested.accumulate("movieId", rel.getMovieID());
			obj.append("baconPath", nested);
		}
		return obj.toString();
	}

	@Override
	public void handlePost(HttpExchange r) throws Exception {

	}

	private ActorService getActorService() {
		if (actorService == null)
			actorService = new ActorServiceImpl(driver);
		return actorService;
	}

	private String buildResponse(Integer baconNumber) throws JSONException {
		JSONObject obj = new JSONObject();
		obj.accumulate("baconNumber", baconNumber);
		return obj.toString();
	}

}
