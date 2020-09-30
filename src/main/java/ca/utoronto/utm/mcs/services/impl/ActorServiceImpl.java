package ca.utoronto.utm.mcs.services.impl;

import java.util.List;

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
		ActorDAO actorDAO = getActorDAO();
		Actor kevinB = actorDAO.getActorByName(kevinBacon);
		Actor otherActor = actorDAO.getActorByID(actor.getId());
		return actorDAO.computeShortestPath(otherActor);
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
	public Actor getActorByID(String actorId) throws Exception {
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

	@Override
	public Actor getActorByName(String actorName) throws Exception {
		ActorDAO actorDAO = getActorDAO();
		Actor actor = actorDAO.getActorByName(actorName);
		if (actor == null) return null;
		actor.getMovies().addAll(actorDAO.getMoviesByActorID(actor.getId()));
		return actor;
	}

	@Override
	public List<ActorMovieRelationship> computeBaconPath(Actor actor) throws Exception {
		ActorDAO actorDAO = getActorDAO();
		return actorDAO.computeBaconPath(actor);
	}
	
	
	
	
}
