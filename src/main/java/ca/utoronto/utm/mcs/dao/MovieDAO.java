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
import ca.utoronto.utm.mcs.domain.Movie;

public class MovieDAO {

	private Driver driver;
	
	public MovieDAO(Driver driver) {
		super();
		this.driver = driver;
	}

	public String insertMovie(Movie movie) {
		try (Session session = driver.session()) {
			session.writeTransaction(tx -> tx.run("MERGE (m:movie {name: $name, id: $movieID})",
					parameters("name", movie.getName(), "movieID", movie.getId())));
			session.close();
			return movie.getId();
		}
	}
	
	public Movie getMovie(String movieId) {
		Movie movie = null;
		try (Session session = driver.session()) {
			String name = session.writeTransaction(new TransactionWork<String>() {
				@Override
				public String execute(Transaction tx) {
					Result result = tx.run("MATCH (m: movie) " + "WHERE m.id = $movieID" + " RETURN m.name as name",
							parameters("movieID", movieId));
					if (result.hasNext()) {
						return result.single().get("name", "");
					}
					return null;
				}
			});
			if (name != null) {
				movie = new Movie();
				movie.setName(name);
				movie.setId(movieId);
				session.close();
				return movie;
			}
			return null;
		}
	}
	
	public List<String> getActorsByMovieId(String movieId) {
		List<String> actors = new ArrayList<>();
		try (Session session = driver.session()) {
			List<Record> results = session.writeTransaction(new TransactionWork<List<Record>>() {
				@Override
				public List<Record> execute(Transaction tx) {
					Result result = tx.run("MATCH (m:movie {id: $movieId})-[r]-(a) RETURN a.id as actorId",
							parameters("movieId", movieId));
					return result.list();
				}
			});
			if (results != null) {
				for (Record rec : results) {
					actors.add(rec.get("actorId", ""));
				}
			}
			return actors;
		}
	}
}
