package ca.utoronto.utm.mcs.domain;

public class Movie extends BaseDataEntity {

	private static final long serialVersionUID = 2180961231573206812L;
	public String titleType;
	public String primaryTitle;
	public String originalTitle;
	public Boolean isAdult;
	public String startYear;
	public String endYear;
	
	
	public String getPrimaryTitle() {
		return primaryTitle;
	}
	public void setPrimaryTitle(String primaryTitle) {
		this.primaryTitle = primaryTitle;
	}
	public String getOriginalTitle() {
		return originalTitle;
	}
	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}
	public Boolean getIsAdult() {
		return isAdult;
	}
	public void setIsAdult(Boolean isAdult) {
		this.isAdult = isAdult;
	}
	public String getStartYear() {
		return startYear;
	}
	public void setStartYear(String startYear) {
		this.startYear = startYear;
	}
	public String getEndYear() {
		return endYear;
	}
	public void setEndYear(String endYear) {
		this.endYear = endYear;
	}
}
