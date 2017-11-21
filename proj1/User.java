/**
 * User stores the user id and an array 
 * of all the movie ratings
 * 
 * @author Connor Ruggles
 *
 */
public class User {
	/**
	 * array of movie ratings for this user
	 */
	private int[] ratings;

	private int userID;

	public User(int id, int numMovies) {
		ratings = new int[numMovies];
		userID = id;
	}

	public void addRating(int index, int rating) {
		ratings[index - 1] = rating;
	}

	public int getRating(int movie) {
		return ratings[movie - 1];
	}

	public int getID() {
		return userID;
	}
}
