package ca.utoronto.utm.mcs.services;

import java.util.List;

import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;

public interface ActorMovieRelationshipService {

	public ActorMovieRelationship addRelationship(ActorMovieRelationship relationship) throws Exception;
	public ActorMovieRelationship getRelationship(ActorMovieRelationship relationship) throws Exception;
	public List<String> findActorsByMovieId(String movieId) throws Exception;
	public List<String> findMoviesByActorId(String actorId) throws Exception;
}
