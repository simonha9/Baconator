package ca.utoronto.utm.mcs.dao;

import static org.neo4j.driver.Values.parameters;

import org.neo4j.driver.Driver;
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
					parameters("name", movie.getPrimaryTitle(), "movieID", movie.getId())));
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
				movie.setPrimaryTitle(name);
				movie.setId(movieId);
				session.close();
				return movie;
			}
			return null;
		}
	}
}
