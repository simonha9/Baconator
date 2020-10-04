package ca.utoronto.utm.mcs.handlers;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.neo4j.driver.Driver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.Utils;

public abstract class BaseHandler implements HttpHandler {

	Driver driver;
	
	public static final String kevinBaconId = "nm0000102";
	
	public BaseHandler(Driver driver) {
		this.driver = driver;
	}
	
	@Override
	public void handle(HttpExchange r) throws IOException {
		try {
			if (r.getRequestMethod().equals("GET")) {
				handleGet(r);
			} else if (r.getRequestMethod().equals("PUT")) {
				handlePut(r);
			}
		} catch (MissingInformationException | JSONException  | NodeAlreadyExistsException e) {
			r.sendResponseHeaders(400, -1);
		} catch (NodeNotExistException e) {
			r.sendResponseHeaders(404, -1);
		} catch (Exception e) {
			r.sendResponseHeaders(500, -1);
		}
	}

	public abstract void handleGet(HttpExchange r) throws Exception;
	
	public abstract void handlePut(HttpExchange r) throws Exception;
	
	public JSONObject convertRequestToJSON(HttpExchange r) throws IOException, JSONException {
		String body = Utils.convert(r.getRequestBody());
		JSONObject deserialized = new JSONObject(body);
		return deserialized;
	}

	

}
