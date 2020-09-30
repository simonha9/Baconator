package ca.utoronto.utm.mcs.dao;

import static org.neo4j.driver.Values.parameters;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;

public class ActorMovieRelationshipDAO {

	private Driver driver;

	public ActorMovieRelationshipDAO(Driver driver) {
		super();
		this.driver = driver;
	}

	public ActorMovieRelationship insertRelationship(ActorMovieRelationship relationship) {
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run(
					"MATCH (a:actor),(m:movie)" + " WHERE a.id = $actorID AND m.id = $movieID"
							+ " CREATE (a)-[r:ACTED_IN]->(m)",
					parameters("actorID", relationship.getActorID(), "movieID", relationship.getMovieID())));
			return relationship;
		}
	}

	public ActorMovieRelationship hasRelationship(String actorId, String movieId) {
		ActorMovieRelationship relationship = null;
		try (Session session = driver.session()) {
			String rel = session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					Result result = tx.run("MATCH (a:actor {id: $actorID})-[r]-(m:movie {id: $movieId}) RETURN type(r)",
							parameters("actorID", actorId, "movieId", movieId));

					if (result.hasNext()) {
						return result.single().get(0).asString();
					}
					return null;
				}
			});
			if (rel != null) {
				relationship = new ActorMovieRelationship();
				relationship.setActorID(actorId);
				relationship.setMovieID(movieId);
				return relationship;
			}
		}
		return null;
	}

}
