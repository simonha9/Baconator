package ca.utoronto.utm.mcs.services.db;

import static org.neo4j.driver.Values.parameters;

import java.util.HashMap;
import java.util.Map;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Session;

import ca.utoronto.utm.mcs.services.dao.ActorDAO;

public class Neo4jActorMovies {

	private Driver driver;
	private String uriDb;

	public Neo4jActorMovies() {
		uriDb = "bolt://localhost:7687";
		driver = GraphDatabase.driver(uriDb, AuthTokens.basic("neo4j", "1234"));
	}

	public void close() throws Exception {
		driver.close();
	}

	public void insertActor(ActorDAO actor) {
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MERGE (a:Actor {name: $name, id: $actorID})",
					parameters("name", actor.getName(), "actorID", actor.getActorID())));
			session.close();
		}
	}
}
