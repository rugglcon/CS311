import java.util.*;

/**
 * @author Connor Ruggles and Josh Grittner
 */
public class DynamicProgramming {
	public static ArrayList<Integer> minCostVC(int[][] M) {
		ArrayList<Integer> coords = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> cuts = new ArrayList<ArrayList<Integer>>();
		int minIndex = 0;
		int x = 0;
		int minCost = Integer.MAX_VALUE;
		for(int y = 0; y < M[0].length; y++) {
			int cost = 0;
			x = 0;
			coords = new ArrayList<>();
			int start = M[x][y];
			cost += start;
			int tmp = y;
			coords.add(x);
			coords.add(y);
			int tmpIndex = tmp;
			for(x = 1; x < M.length; x++) {
				start = M[x][tmp];
				coords.add(x);
				if(tmp > 0 && M[x][tmp - 1] < start) {
					start = M[x][tmp - 1];
					tmpIndex = tmp - 1;
				}
				if(tmp < M[0].length - 1 && M[x][tmp + 1] < start) {
					start = M[x][tmp + 1];
					tmpIndex = tmp + 1;
				}
				coords.add(tmpIndex);
				cost += start;
				tmp = tmpIndex;
			}
			if(cost < minCost) {
				minCost = cost;
				minIndex = y;
			}

			cuts.add(coords);
		}
		return cuts.get(minIndex);
	}

	public static String stringAlignment(String x, String y) {
		return StringAlign.getMinCost(x, y);
	}
}
