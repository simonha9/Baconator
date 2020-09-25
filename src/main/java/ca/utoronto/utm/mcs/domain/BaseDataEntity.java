package ca.utoronto.utm.mcs.domain;

import java.io.Serializable;

public class BaseDataEntity implements Serializable {

	private static final long serialVersionUID = -3511212793126715779L;

	public int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
}
