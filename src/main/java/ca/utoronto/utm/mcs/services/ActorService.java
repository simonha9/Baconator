package ca.utoronto.utm.mcs.services;

import java.util.List;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;

public interface ActorService {

	public String kevinBaconId = "nm0000102";
	
	public String addActor(Actor actor) throws Exception;
	public Actor findActorById(String actorId) throws Exception;
	public Integer computeBaconNumber(String actorId) throws Exception;
	public List<ActorMovieRelationship> computeBaconPath(String actorId) throws Exception;
}
