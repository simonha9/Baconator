package ca.utoronto.utm.mcs.domain;

import java.util.ArrayList;
import java.util.List;

public class Movie extends BaseDataEntity {

	private static final long serialVersionUID = 2180961231573206812L;
	public String name;
	public List<String> actors = new ArrayList<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getActors() {
		return actors;
	}
	public void setActors(List<String> actors) {
		this.actors = actors;
	}
	
	
}
