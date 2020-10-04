package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;
import java.io.OutputStream;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NoPathException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorService;
import ca.utoronto.utm.mcs.services.impl.ActorServiceImpl;

public class BaconNumberRestHandler extends BaseHandler {

	ActorService actorService = null;

	public BaconNumberRestHandler(Driver driver) {
		super(driver);
		actorService = new ActorServiceImpl(driver);
	}

	private Actor getActor(HttpExchange r) throws IOException, JSONException {
		JSONObject deserialized = convertRequestToJSON(r);
		Actor actor = new Actor();
		if (deserialized.has("actorId"))
			actor.setId(deserialized.getString("actorId"));
		return actor;
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

	@Override
	public void handleGet(HttpExchange r) throws Exception {
		Actor actor = getActor(r);
		Integer baconNumber = 0;
		baconNumber = actorService.computeBaconNumber(actor.getId());
		String response = buildResponse(baconNumber);
		r.getResponseHeaders().set("Content-Type", "appication/json");
		r.sendResponseHeaders(200, response.length());
		OutputStream os = r.getResponseBody();
		os.write(response.getBytes());
		os.close();
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
