package ca.utoronto.utm.mcs.services.impl;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.ActorDAO;
import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.services.ActorService;

public class ActorServiceImpl implements ActorService {

	Driver driver;
	
	public ActorServiceImpl(Driver driver) {
		this.driver = driver;
	}
	
	@Override
	public void computeBaconNumber() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void computeBaconPath() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String addActor(Actor actor) throws Exception {
		ActorDAO actorDAO = new ActorDAO(driver);
		Actor existingActor = actorDAO.getActor(actor.getId());
		if (existingActor != null) throw new NodeAlreadyExistsException("Node already exists");
		return actorDAO.insertActor(actor);
	}

	@Override
	public Actor getActor(String actorId) throws Exception {
		ActorDAO actorDAO = new ActorDAO(driver);
		return actorDAO.getActor(actorId);
	}

	
}
