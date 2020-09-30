package ca.utoronto.utm.mcs.services;

import java.util.List;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;

public interface ActorService {

	public static String kevinBacon = "Kevin Bacon";
	
	public String addActor(Actor actor) throws Exception;
	public Actor getActorByID(String actorId) throws Exception;
	public Actor getActorByName(String actorName) throws Exception;
	public Integer computeBaconNumber(Actor actor) throws Exception;
	public void computeBaconPath() throws Exception;
	public List<ActorMovieRelationship> computeBaconPath(Actor actor) throws Exception;
}
