package ca.utoronto.utm.mcs.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.Driver;

import ca.utoronto.utm.mcs.dao.ActorDAO;
import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;
import ca.utoronto.utm.mcs.exceptions.MissingInformationException;
import ca.utoronto.utm.mcs.exceptions.NoPathException;
import ca.utoronto.utm.mcs.exceptions.NodeAlreadyExistsException;
import ca.utoronto.utm.mcs.exceptions.NodeNotExistException;
import ca.utoronto.utm.mcs.services.ActorMovieRelationshipService;
import ca.utoronto.utm.mcs.services.ActorService;

public class ActorServiceImpl implements ActorService {

	ActorDAO actorDAO = null;

	public ActorServiceImpl(Driver driver) {
		actorDAO = new ActorDAO(driver);
	}

	@Override
	public Integer computeBaconNumber(String actorId) throws Exception {
		Integer baconNumber = null;
		if (actorId == null)
			throw new MissingInformationException("Required Information does not exist");
		Actor otherActor = actorDAO.findActorById(actorId);
		if (otherActor == null)
			throw new NodeNotExistException("That node does not exist");
		Actor kevinB = actorDAO.findActorById(kevinBaconId);
		if (kevinB == null) 
			throw new NodeNotExistException("That node does not exist");
		
		if (!otherActor.getId().equals(kevinB.getId())) {
			baconNumber = actorDAO.computeMinDegreeOfSeperation(otherActor.getId(), kevinB.getId());
			if (baconNumber == null)
				throw new NoPathException("There is no path");
		} else {
			return 0;
		}
		return baconNumber;
	}

	@Override
	public String addActor(Actor actor) throws Exception {
		if (actor.getName() == null || actor.getId() == null)
			throw new MissingInformationException("Required info is missing");
		Actor existingActor = actorDAO.findActorById(actor.getId());
		if (existingActor != null)
			throw new NodeAlreadyExistsException("That actor already exists");
		return actorDAO.saveActor(actor);
	}

	@Override
	public Actor findActorById(String actorId) throws Exception {
		if (actorId == null)
			throw new MissingInformationException("Required info is missing");
		Actor actor = actorDAO.findActorById(actorId);
		if (actor == null)
			throw new NodeNotExistException("That node does not exist");
		ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(actorDAO.getDriver());
		actor.getMovies().addAll(relationshipService.findMoviesByActorId(actorId));
		return actor;
	}

	@Override
	public List<ActorMovieRelationship> computeBaconPath(String actorId) throws Exception {
		List<ActorMovieRelationship> rels = new ArrayList<>();
		Actor kevinB = actorDAO.findActorById(kevinBaconId);
		if (kevinB == null) 
			throw new NodeNotExistException("That node does not exist");
		if (actorId == null)
			throw new MissingInformationException("Required Information does not exist");
		Actor otherActor = actorDAO.findActorById(actorId);
		if (otherActor == null)
			throw new NodeNotExistException("That node does not exist");
		if (!otherActor.getId().equals(kevinB.getId())) {
			rels = actorDAO.computeShortestPath(otherActor.getId(), kevinB.getId());
		} else {
			ActorMovieRelationshipService relationshipService = new ActorMovieRelationshipServiceImpl(actorDAO.getDriver());
			List<String> movies = relationshipService.findMoviesByActorId(kevinBaconId);
			if (!movies.isEmpty()) {
				ActorMovieRelationship rel = new ActorMovieRelationship();
				rel.setActorID(kevinBaconId);
				rel.setMovieID(movies.get(0));
				rel.setHasRelationship(true);
			}
		}
		if (rels == null) throw new NoPathException("There does not exist a path to Kevin Bacon");
		return rels;
	}

}
