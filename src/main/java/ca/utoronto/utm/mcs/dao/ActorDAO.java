package ca.utoronto.utm.mcs.dao;

import static org.neo4j.driver.Values.parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

import ca.utoronto.utm.mcs.domain.Actor;
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;

public class ActorDAO {

	private Driver driver;

	public ActorDAO(Driver driver) {
		super();
		this.driver = driver;
	}

	public String saveActor(Actor actor) {
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MERGE (a:actor {name: $name, id: $actorID})",
					parameters("name", actor.getName(), "actorID", actor.getId())));
			return actor.getId();
		}
	}

	public Actor findActorById(String actorId) {
		Actor actor = null;
		try (Session session = getDriver().session()) {
			Query query = new Query("MATCH (a:actor) WHERE a.id = $actorId RETURN a ");
			Result result = session.run(query.withParameters(parameters("actorId", actorId)));
			if (result.hasNext()) {
				Record record = result.single();
				Map<String, Object> fieldMap = record.get("a").asMap();
				actor = new Actor();
				actor.setId((String) fieldMap.get("id"));
				actor.setName((String) fieldMap.get("name"));
			}
			return actor;
		}
	}

	public Integer computeMinDegreeOfSeperation(String fromId, String toId) {
		Integer baconNumber = null;
		try (Session session = getDriver().session()) {
			Query query = new Query("MATCH (a:actor { id: $fromId }), (b:actor { id: $toId }), "
					+ "p = shortestPath((a)-[r:ACTED_IN*]-(b)) " + "RETURN size([m in nodes(p) WHERE m:movie]) as baconNumber");
			Result result = session.run(query.withParameters(parameters("fromId", fromId, "toId", toId)));
			if (result.hasNext()) {
				baconNumber = result.single().get(0).asInt();
			}
			return baconNumber;
		}
	}

	public List<ActorMovieRelationship> computeShortestPath(String fromId, String toId) {
		List<ActorMovieRelationship> rels = null;
		List<Record> recs = null;
		try (Session session = getDriver().session()) {
			Query query = new Query("MATCH (a:actor { id: $fromId }), (b:actor { id: $toId }), "
					+ "p = shortestPath((a)-[r:ACTED_IN*]-(b)) " + "with (nodes(p)) as path " + "unwind path as flatPath "
					+ "RETURN flatPath");
			Result result = session.run(query.withParameters(parameters("fromId", fromId, "toId", toId)));
			if (result.hasNext()) {
				recs = result.list();
			}
			if (recs != null) {
				rels = new ArrayList<>();
				for (int i = 0; i < recs.size() - 1; i++) {
					Record rec1 = recs.get(i);
					Record rec2 = recs.get(i + 1);
					ActorMovieRelationship relationship = new ActorMovieRelationship();
					if (i % 2 == 0) {
						relationship.setActorID(rec1.get("flatPath").get("id", ""));
						relationship.setMovieID(rec2.get("flatPath").get("id", ""));
					} else {
						relationship.setMovieID(rec1.get("flatPath").get("id", ""));
						relationship.setActorID(rec2.get("flatPath").get("id", ""));
					}
					rels.add(relationship);
				}
			}
			return rels;
		}
	}

	public Driver getDriver() {
		return driver;
	}

}
