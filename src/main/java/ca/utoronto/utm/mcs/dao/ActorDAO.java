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
import ca.utoronto.utm.mcs.domain.ActorMovieRelationship;

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

	public Actor getActorByID(String actorID) {
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
	
	public Actor getActorByName(String name) {
		Actor actor = null;
		try (Session session = driver.session()) {
			String id = session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					Result result = tx.run("MATCH (a: actor) " + "WHERE a.name = $name" + " RETURN a.id as id",
							parameters("name", name));

					if (result.hasNext()) {
						return result.single().get(0).asString();
					}
					return null;
				}
			});
			if (id != null) {
				actor = new Actor();
				actor.setName(name);
				actor.setId(id);
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

	public Integer computeShortestPath(Actor actor) {
		try (Session session = driver.session()) {
			Integer baconNum = session.writeTransaction(new TransactionWork<Integer>() {
				@Override
				public Integer execute(Transaction tx) {
					Result result = tx.run(
							"MATCH (a:actor { name: 'Kevin Bacon' }), (b:actor { id: $actorID }), "
									+ "p = shortestPath((a)-[*]-(b)) "
									+ "RETURN size([m in nodes(p) WHERE m:movie]) as baconNumber",
							parameters("actorID", actor.getId()));
					if (result.hasNext()) {
						return result.single().get(0).asInt();
					}
					return null;
				}
			});
			if (baconNum != null) {
				return baconNum;
			}
			return null;
		}
	}
	
	public List<ActorMovieRelationship> computeBaconPath(Actor actor) {
		List<ActorMovieRelationship> rels = null;
		try (Session session = driver.session()) {
			List<Record> recs = session.writeTransaction(new TransactionWork<List<Record>>() {
				@Override
				public List<Record> execute(Transaction tx) {
					Result result = tx.run(
							"MATCH (a:actor { name: 'Kevin Bacon' }), (b:actor { id: $actorID }), "
									+ "p = shortestPath((a)-[*]-(b)) "
									+ "with reverse(nodes(p)) as path "
									+ "unwind path as flatPath "
									+ "RETURN flatPath",
							parameters("actorID", actor.getId()));
					if (result.hasNext()) {
						return result.list();
					}
					return null;
				}
			});
			if (recs != null) {
				rels = new ArrayList<>();
				for (int i = 0; i < recs.size() - 1; i++) {
					Record rec1 = recs.get(i);
					Record rec2 = recs.get(i+1);
					ActorMovieRelationship relationship = new ActorMovieRelationship();
					if (i%2 == 0) {
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
	

}
