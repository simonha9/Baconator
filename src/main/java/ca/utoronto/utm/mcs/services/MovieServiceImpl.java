package ca.utoronto.utm.mcs.services;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.services.dao.ActorDAO;
import ca.utoronto.utm.mcs.services.dao.ActorDAOService;
import ca.utoronto.utm.mcs.services.dao.ActorDAOServiceImpl;

public class MovieServiceImpl implements ActorService {

	ActorDAOService actorDAOService = new ActorDAOServiceImpl();

	@Override
	public void handle(HttpExchange r) throws IOException {
		try {
			if (r.getRequestMethod().equals("GET")) {
				getActor(r);
			} else if (r.getRequestMethod().equals("POST")) {
				addActor(r);
			}
		} catch (MissingInformationException | JSONException e) {
			r.sendResponseHeaders(400, -1);
		} catch (Exception e) {
			e.printStackTrace();
			r.sendResponseHeaders(500, -1);
		}
	}

	@Override
	public void addActor(HttpExchange r) throws Exception {
		Actor actor = mapBodyToActor(r);
		//Check if exists
		if (actor.getPrimaryName() != null && actor.getId() != null) {
			actorDAOService.addActor(actor.getPrimaryName(), actor.getId());
		} else {
			throw new MissingInformationException("Information required is missing");
		}
		r.sendResponseHeaders(200, -1);
	}

	@Override
	public void getActor(HttpExchange r) throws Exception {
		//TODO: add relationships to return list of movies
		
		Actor actor = mapBodyToActor(r);
		if (actor.getId() != null) {
			ActorDAO actorDAO = actorDAOService.getActor(actor.getId());
			System.out.println("Actor found: " + actorDAO.getName());
			
			r.sendResponseHeaders(200, -1);
			// Write to out
		}

	}

	@Override
	public void computeBaconNumber(HttpExchange r) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void computeBaconPath(HttpExchange r) throws Exception {
		// TODO Auto-generated method stub

	}
	
	private Actor mapBodyToActor(HttpExchange r) throws IOException, JSONException {
		String body = Utils.convert(r.getRequestBody());
		JSONObject deserialized = new JSONObject(body);
		Actor actor = new Actor();

		if (deserialized.has("name")) 
			actor.setPrimaryName(deserialized.getString("name"));
		if (deserialized.has("actorId"))
			actor.setId(deserialized.getString("actorId"));
		
		return actor;
	}

}
