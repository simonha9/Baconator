package ca.utoronto.utm.mcs.services.impl;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.ActorDAO;
import ca.utoronto.utm.mcs.dao.MovieDAO;
import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.domain.Movie;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorService;

public class ActorServiceImpl implements ActorService {

	Driver driver;
	
	ActorDAO actorDAO = null;
	
	public ActorServiceImpl(Driver driver) {
		this.driver = driver;
	}
	
	@Override
	public Integer computeBaconNumber(Actor actor) throws Exception {
		checkActorExistsByName(kevinBacon);
		checkActorExistsByID(actor.getId());
		return actorDAO.computeBaconNumber(actor);
	}

	@Override
	public void computeBaconPath() throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String addActor(Actor actor) throws Exception {
		ActorDAO actorDAO = getActorDAO();
		return actorDAO.insertActor(actor);
	}

	@Override
	public Actor getActor(String actorId) throws Exception {
		ActorDAO actorDAO = getActorDAO();
		Actor actor = actorDAO.getActorByID(actorId);
		if (actor == null) return null;
		actor.getMovies().addAll(actorDAO.getMoviesByActorID(actorId));
		return actor;
	}
	
	private ActorDAO getActorDAO() {
		if (actorDAO == null) actorDAO = new ActorDAO(driver);
		return actorDAO;
	}
	
	private Boolean checkActorExistsByID(String actorId) throws NodeNotExistException {
		ActorDAO actorDAO = new ActorDAO(driver);
		Actor actor = actorDAO.getActorByID(actorId);
		if (actor == null) throw new NodeNotExistException("That node does not exist");
		return true;
	}
	
	private Boolean checkActorExistsByName(String actorName) throws NodeNotExistException {
		ActorDAO actorDAO = new ActorDAO(driver);
		Actor actor = actorDAO.getActorByName(actorName);
		if (actor == null) throw new NodeNotExistException("That node does not exist");
		return true;
	}
	
	
	
	
}
