package ca.utoronto.utm.mcs.services;

import java.io.IOException;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpExchange;

import ca.utoronto.utm.mcs.services.dao.ActorDAO;
import ca.utoronto.utm.mcs.services.dao.ActorDAOService;
import ca.utoronto.utm.mcs.services.dao.ActorDAOServiceImpl;

public class ActorServiceImpl implements ActorService {

	ActorDAOService actorDAOService = new ActorDAOServiceImpl();
	
	@Override
	public void handle(HttpExchange r) throws IOException {
		try {
			if (r.getRequestMethod().equals("GET")) {
				getActor(r);
			} else if (r.getRequestMethod().equals("POST")) {
				System.out.println("POST sent:");
				addActor(r);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addActor(HttpExchange r) throws Exception {
		String body = Utils.convert(r.getRequestBody());
        JSONObject deserialized = new JSONObject(body);

        String name = null;
        String actorID = null;
        ActorDAO actorDAO = null;

        if (deserialized.has("name")) {
        	name = deserialized.getString("name");
//        	System.out.println("name: " + name);
        }

        if (deserialized.has("actorId"))
        	actorID = deserialized.getString("actorId");
        
        if (name != null && actorID != null) {
        	actorDAO = new ActorDAO(name, actorID);
        	actorDAOService.addActor(actorDAO);
        }
		
		
	}

	@Override
	public void getActor(HttpExchange r) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void computeBaconNumber(HttpExchange r) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void computeBaconPath(HttpExchange r) throws Exception {
		// TODO Auto-generated method stub

	}

}
