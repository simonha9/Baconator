package ca.utoronto.utm.mcs.dao;

import static org.neo4j.driver.Values.parameters;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Session;

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
	
}
