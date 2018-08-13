package object;

public class Movie {

	String name;
	String genres;
	String ratings;
	String showTime;
	String theater;
	String theaterAddress;
	String description;

	public Movie(String name, String genres, String ratings, String showTime,
			String theater, String theaterAddress, String description) {
		this.name = name;
		this.genres = genres;
		this.ratings = ratings;
		this.showTime = showTime;
		this.theater = theater;
		this.description = description;
		this.theaterAddress = theaterAddress;
	}

	public Movie() {
		this.name = "";
		this.genres = "";
		this.ratings = "";
		this.showTime = "";
		this.theater = "";
		this.description = "";
		this.theaterAddress = "";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGenres() {
		return genres;
	}

	public void setGenres(String genres) {
		this.genres = genres;
	}

	public String getRatings() {
		return ratings;
	}

	public void setRatings(String ratings) {
		this.ratings = ratings;
	}

	public String getShowTime() {
		return showTime;
	}

	public void setShowTime(String showTime) {
		this.showTime = showTime;
	}

	public String getTheater() {
		return theater;
	}

	public void setTheater(String theater) {
		this.theater = theater;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTheaterAddress() {
		return theaterAddress;
	}

	public void setTheaterAddress(String theaterAddress) {
		this.theaterAddress = theaterAddress;
	}

}
