package ca.utoronto.utm.mcs.domain;

public class Actor extends BaseDataEntity {

	private static final long serialVersionUID = 58059863990569983L;
	
	public String primaryName;
	public String birthYear;
	public String deathYear;
	public String pimaryProfession;
	public String knownForTitle;
	public String getPrimaryName() {
		return primaryName;
	}
	public void setPrimaryName(String primaryName) {
		this.primaryName = primaryName;
	}
	public String getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	public String getDeathYear() {
		return deathYear;
	}
	public void setDeathYear(String deathYear) {
		this.deathYear = deathYear;
	}
	public String getPimaryProfession() {
		return pimaryProfession;
	}
	public void setPimaryProfession(String pimaryProfession) {
		this.pimaryProfession = pimaryProfession;
	}
	public String getKnownForTitle() {
		return knownForTitle;
	}
	public void setKnownForTitle(String knownForTitle) {
		this.knownForTitle = knownForTitle;
	}
	
	

}
