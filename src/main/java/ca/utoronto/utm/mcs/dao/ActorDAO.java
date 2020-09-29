package ca.utoronto.utm.mcs.dao;

import static org.neo4j.driver.Values.parameters;

import java.util.ArrayList;
import java.util.List;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import ca.utoronto.utm.mcs.domain.Actor;

public class ActorDAO {
	
	private Driver driver;
	
	public ActorDAO(Driver driver) {
		super();
		this.driver = driver;
	}

	public String insertActor(Actor actor) {
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MERGE (a:actor {name: $name, id: $actorID})",
					parameters("name", actor.getName(), "actorID", actor.getId())));
			session.close();
			return actor.getId();
		}
	}

	public Actor getActor(String actorID) {
		Actor actor = null;
		try (Session session = driver.session()) {
			String name = session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					Result result = tx.run("MATCH (a: actor) " + "WHERE a.id = $actorID" + " RETURN a.name as name",
							parameters("actorID", actorID));

					if (result.hasNext()) {
						return result.single().get(0).asString();
					}
					return null;
				}
			});
			if (name != null) {
				actor = new Actor();
				actor.setName(name);
				actor.setId(actorID);
				session.close();
				return actor;
			}
			return null;
		}
	}
	
	public List<String> getMoviesByActorID(String actorID) {
		List<String> movies = new ArrayList<>();
		try (Session session = driver.session()) {
			List<Record> results = session.writeTransaction(new TransactionWork<List<Record>>() {
				@Override
				public List<Record> execute(Transaction tx) {
					Result result = tx.run("MATCH (a:actor {id: $actorID})-[r]-(m) RETURN m.id as movieId",
							parameters("actorID", actorID));

					return result.list();
				}
			});
			if (results != null) {
				for (Record rec : results) {
					movies.add(rec.get("movieId", ""));
				}
			}
			return movies;
		}
	}
	
}
