package ca.utoronto.utm.mcs.dao;

import static org.neo4j.driver.Values.parameters;

import java.util.Map;

import org.neo4j.driver.Driver;
import org.neo4j.driver.Query;
import org.neo4j.driver.Record;
import org.neo4j.driver.Result;
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
					parameters("name", movie.getName(), "movieID", movie.getId())));
			session.close();
			return movie.getId();
		}
	}
	
	public Movie findMovieById(String movieId) {
		Movie movie = null;
		try (Session session = getDriver().session()) {
			Query query = new Query("MATCH (m:movie) WHERE m.id = $movieId RETURN m ");
			Result result = session.run(query.withParameters(parameters("movieId", movieId)));
			if (result.hasNext()) {
				Record record = result.single();
				Map<String, Object> fieldMap = record.get("m").asMap();
				movie = new Movie();
				movie.setId((String)fieldMap.get("id"));
				movie.setName((String)fieldMap.get("name"));
			}
			return movie;
		}
	}
	public Driver getDriver() {
		return driver;
	}
}
