
public class StringAlign {
	
	/**
	 * Returns the alignment cost of the two Strings.
	 * @param x - First String to compute alignment cost
	 * @param y - Second  String to compute alignment cost
	 * @return int - Cost of alignment for the two strings passed in, -1 if the Strings are not the same length
	 */
	public static int AlignCost(String x, String y) {
		if(x.length() != y.length()) {return -1;}
		int total = 0;
		for(int i = 0 ; i < x.length() ; i++) {
			total = total + AlignCost(x.charAt(i),y.charAt(i));
		}
		return total;
	}
	
	/**
	 * Computes the alignment cost of the two chars passed in
	 * @param x - First Char to compute
	 * @param y - Second char to compute
	 * @return int - Alignment cost, 4 if either are '$', 0 if they are the same char, else returns 2
	 */
	public static int AlignCost(char x, char y) {
		if(x == '$' || y == '$') {return 4;}
		if(x == y) {return 0;}
		return 2;
	}
	
	/**
	 * Retrieves the String value of the lowest Alignment Cost from y only adding '$'
	 * @param x - Longer String to have y modify to
	 * @param y - Shorter String to have '$' added to
	 * @return String - y with '$' added to it to be the same length as x with the lowest alignment cost
	 */
	public static String getMinCost(String x, String y) {
		int xl = x.length();
		int yl = y.length();
		String z = getDollarSignStr(xl);
		for(int i = 0 ; i < xl - yl ; i++) {
			y = y + " ";
		}
		
		int next_open_index = 0;
		for(int i = 0 ; i < yl ; i++) {
			int next_index = getZindexOf(i, x, y); //getting the z index of y[i] character
			if(next_index < next_open_index) {
				next_index = next_open_index;
			}
			z = putChar(z, y.substring(i, i+1), next_index);	
			next_open_index = next_index + 1;
		}
		return z;
		
	}
	
	public static String putChar(String z, String newChar, int next_index) {
		if(newChar.length() != 1) {
			return null;
		}
		z = z.substring(0,next_index) + newChar + z.substring(next_index + 1);
		return z;
	}
	
	/**
	 * Returns a String of size number of '$' characters
	 * @param size - Length of string to be returned
	 * @return String - String of only '$'
	 */
	public static String getDollarSignStr(int size) {
		String ret = "";
		for(int i = 0 ; i < size ; i++) {
			ret = ret + "$";
		}
		return ret;
	}

	/**
	 * Returns the index that y[i] character should be at in the min cost alignment string to x
	 * @param i - index of y char to calculate index for
	 * @param x - longer string to align to
	 * @param y - shorter string to align to x
	 * @return int - index of y[i] in min alignment cost string 
	 */
	private static int getZindexOf(int i, String x, String y) {
		//The max range of any given index of y (y[i] can be put in z[i] through z[i + reach])
		int reach = x.length() - y.trim().length() + 1;
		
		boolean[][] matchTable = getMatchArray(x.substring(i, i + reach), y.substring(i, i+reach));
		for (int x_index = 0 ; x_index < reach ; x_index++) {
			for(int y_index = x_index ; y_index >= 0; y_index--) {
				if(matchTable[x_index][y_index]) {
					//Match occurred
					if(y_index == 0) {
						//match is first index
						return i + x_index; // y[0] goes to z[i + x_index]
					}
					else {
						//match is not first index
						return i; //y[0] goes to z[i]
					}
				}
			}
		}
		return i;
	}

	/**
	 * Gets a truth table of the two strings with true where col and row are matched characters, and false otherwise
	 * 		[a]	[b]	[ ]
	 * [a]	 T	 F	 F
	 * [b]	 F 	 T	 F
	 * [c]	 F	 F	 F
	 * @param x - String 1, longer string stored as rows
	 * @param y - String 2, can be shorter stored as columns
	 * @return boolean[][] - truth table, null if x is shorter than y
	 */
	public static boolean[][] getMatchArray(String x, String y) {
		if(y.length() > x.length()) {return null;}
		boolean[][] arr = new boolean[x.length()][y.length()];
		for(int i = 0 ; i < x.length() ; i++) {
			for(int j = 0 ; j < i + 1 ; j++) {
				if(isMatch(x,i,y,j)) {
					arr[i][j] = true;
				}
				else {
					arr[i][j] = false;
				}
			}
		}
		return arr;
	}
	
	/**
	 * Checks if characters are matching
	 * @param x - String 1
	 * @param xi - index of char to compare in x
	 * @param y - String 2
	 * @param yi - index of char to compare in y
	 * @return boolean - Whether or not the described chars are the same character
	 */
	public static boolean isMatch(String x, int xi, String y, int yi) {
		return x.substring(xi,xi+1).equals(y.substring(yi,yi+1));
	}

}
