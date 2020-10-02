package ca.utoronto.utm.mcs.domain;

import java.util.ArrayList;
import java.util.List;

public class Actor extends BaseDataEntity {

	private String name;
	private List<String> movies = new ArrayList<>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMovies() {
		return movies;
	}

	public void setMovies(List<String> movies) {
		this.movies = movies;
	}

}
