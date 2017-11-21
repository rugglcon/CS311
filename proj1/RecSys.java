import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 
 * @author Connor Ruggles
 *
 */
public class RecSys {
	/**
	 * NearestPoints instance for this class
	 */
	private NearestPoints np;

	/**
	 * HashMap to map the points to a 
	 * User class; see documentation for
	 * User in User.java
	 */
	private HashMap<Float, User> users;

	/**
	 * array to store user mappings
	 */
	private float[] upoints;
	
	public RecSys(String mrMatrix) {
		File mrFile = new File(mrMatrix);
		users = new HashMap<Float, User>();
		try {
			Scanner scanner = new Scanner(mrFile);
			if(scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] mu = line.split(" ");
				int numUsers = Integer.parseInt(mu[0]);
				int numMovies = Integer.parseInt(mu[1]);
				upoints = new float[numUsers];
				ArrayList<Float> points = new ArrayList<Float>();
				for(int i = 0; i < numUsers; i++) {
					line = scanner.nextLine();
					mu = line.split(" ");
					upoints[i] = Float.parseFloat(mu[0]);
					points.add(Float.parseFloat(mu[0]));
					User u = new User(i + 1, numMovies);
					for(int j = 1; j <= numMovies; j++) {
						u.addRating(j, Integer.parseInt(mu[j]));
					}
					users.put(Float.parseFloat(mu[0]), u);
				}
				np = new NearestPoints(points);
			}
			scanner.close();
		} catch(FileNotFoundException f) {
			System.out.println("can't create rating matrix. file not found.");
			f.printStackTrace();
			return;
		}
	}

	public float ratingOf(int u, int m) {
		float rating = users.get(upoints[u - 1]).getRating(m);
		if(rating == 0.0) {
			ArrayList<Float> al = np.npHashNearestPoints(upoints[u - 1]);
			float ave = 0;
			for(int i = 0; i < al.size(); i++) {
				ave += users.get(al.get(i)).getRating(m);
			}
			rating = ave / al.size();
		}
		return rating;
	}
}
