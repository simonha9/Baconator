package ca.utoronto.utm.mcs.dao;

import static org.neo4j.driver.Values.parameters;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;

import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;

public class ActorMovieRelationshipDAO {

	private Driver driver;

	public ActorMovieRelationshipDAO(Driver driver) {
		super();
		this.driver = driver;
	}

	public ActorMovieRelationship saveRelationship(ActorMovieRelationship relationship) {
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run(
					"MATCH (a:actor),(m:movie)" + " WHERE a.id = $actorID AND m.id = $movieID"
							+ " CREATE (a)-[r:ACTED_IN]->(m)",
					parameters("actorID", relationship.getActorID(), "movieID", relationship.getMovieID())));
			return relationship;
		}
	}

	public ActorMovieRelationship getRelationship(String actorId, String movieId) {
		ActorMovieRelationship relationship = null;
		try (Session session = getDriver().session()) {
			Query query = new Query("MATCH (a:actor {id: $actorID})-[r]-(m:movie {id: $movieId}) RETURN type(r)");
			Result result = session.run(query.withParameters(parameters("actorID", actorId, "movieId", movieId)));
			if (result.hasNext()) {
				relationship = new ActorMovieRelationship();
				relationship.setActorID(actorId);
				relationship.setMovieID(movieId);
				// add more properties extraction here.
			}
			return relationship;
		}
	}
	
	public List<String> findMoviesByActorId(String actorId) {
		List<String> movies = new ArrayList<>();
		try (Session session = getDriver().session()) {
			Query query = new Query("MATCH (a:actor {id: $actorID})-[r:ACTED_IN]-(m) RETURN m.id as movieId");
			Result result = session.run(query.withParameters(parameters("actorID", actorId)));
			if (result.hasNext()) {
				movies.add(result.next().get("movieId", ""));
			}
			return movies;
		}
	}
	
	public List<String> findActorsByMovieId(String movieId) {
		List<String> actors = new ArrayList<>();
		try (Session session = getDriver().session()) {
			Query query = new Query("MATCH (m:movie {id: $movieId})-[r:ACTED_IN]-(a) RETURN a.id as actorId");
			Result result = session.run(query.withParameters(parameters("movieId", movieId)));
			if (result.hasNext()) {
				actors.add(result.next().get("actorId", ""));
			}
			return actors;
		}
	}
	
	private Driver getDriver() {
		return driver;
	}
	
}
