package ca.utoronto.utm.mcs.services.db;

import static org.neo4j.driver.Values.parameters;

import org.neo4j.driver.AuthTokens;
import org.neo4j.driver.Driver;
import org.neo4j.driver.GraphDatabase;
import org.neo4j.driver.Result;
import org.neo4j.driver.Session;
import org.neo4j.driver.Transaction;
import org.neo4j.driver.TransactionWork;

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

	public Boolean insertActor(ActorDAO actor) {
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MERGE (a:Actor {name: $name, id: $actorID})",
					parameters("name", actor.getName(), "actorID", actor.getActorID())));
			session.close();
			return !session.isOpen();
		}
	}

	public ActorDAO getActor(String actorID) {
		ActorDAO actor = null;
		try (Session session = driver.session()) {
			
			String name = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    Result result = tx.run("MATCH (a: Actor) " + "WHERE a.id = $actorID" + " RETURN a.name as name",
							parameters("actorID", actorID));
                    
                    if (result.hasNext()) {
                    	return result.single().get("name", "");
                    }
                    return null;
                }
            } );
			if (name != null) {
				actor = new ActorDAO();
				actor.setName(name);
				actor.setActorID(actorID);
				session.close();
				return actor;
			}
			return null;
		}
	}
}
