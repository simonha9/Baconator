package ca.utoronto.utm.mcs.dao;

import static org.neo4j.driver.Values.parameters;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;

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

}
