import java.io.IOException;
import java.util.ArrayList;
public class Main {
	public static void main(String[] args) {
		NearestPoints np = new NearestPoints("points.txt");
		
		long now = System.currentTimeMillis();
		try {
			np.allNearestPointsNaive();
		} catch(IOException i) {
			i.printStackTrace();
		}
		long after = System.currentTimeMillis();
		System.out.println(after - now);

		now = System.currentTimeMillis();
		try {
			np.allNearestPointsHash();
		} catch(IOException e) {
			e.printStackTrace();
		}
		after = System.currentTimeMillis();
		System.out.println(after - now);
	}
}
