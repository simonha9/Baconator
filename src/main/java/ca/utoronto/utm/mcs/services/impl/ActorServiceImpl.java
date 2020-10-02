package ca.utoronto.utm.mcs.services.impl;

import java.util.List;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.ActorDAO;
import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;
import ca.utoronto.utm.mcs.services.ActorService;

public class ActorServiceImpl implements ActorService {

	Driver driver;
	ActorDAO actorDAO = null;

	public ActorServiceImpl(Driver driver) {
		this.driver = driver;
		actorDAO = new ActorDAO(driver);
	}

	@Override
	public Integer computeBaconNumber(Actor actor) throws Exception {
		ActorDAO actorDAO = getActorDAO();
		Actor kevinB = actorDAO.findActorById(kevinBaconId);
		Actor otherActor = actorDAO.findActorById(actor.getId());
		return actorDAO.computeShortestPath(kevinB, otherActor);
	}

	@Override
	public String insertActor(Actor actor) throws Exception {
		ActorDAO actorDAO = getActorDAO();
		if (actor.getName() == null || actor.getId() == null)
			throw new MissingInformationException("Required info is missing");
		Actor existingActor = actorDAO.findActorById(actor.getId());
		if (existingActor != null)
			throw new NodeAlreadyExistsException("That actor already exists");
		return actorDAO.saveActor(actor);
	}

	@Override
	public Actor findActorById(String actorId) throws Exception {
		ActorDAO actorDAO = getActorDAO();
		if (actorId == null)
			throw new MissingInformationException("Required info is missing");
		Actor actor = actorDAO.findActorById(actorId);
		if (actor == null)
			throw new NodeNotExistException("That node does not exist");
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(driver);
		actor.getMovies().addAll(relationshipService.findMoviesByActorId(actorId));
		return actor;
	}

	private ActorDAO getActorDAO() {
		return actorDAO;
	}

	@Override
	public List<ActorMovieRelationship> computeBaconPath(Actor actor) throws Exception {
		ActorDAO actorDAO = getActorDAO();
		Actor kevinB = actorDAO.findActorById(kevinBaconId);
		return actorDAO.computeBaconPath(kevinB, actor);
	}

}
