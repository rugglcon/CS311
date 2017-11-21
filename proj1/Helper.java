/**
 * Class for utility functions
 * 
 * @author Connor Ruggles
 */
public class Helper {
	/**
	 * determines if 'n' is prime
	 * @param n - int
	 * @return true if n is prime, false otherwise
	 */
	public boolean isPrime(int n) {
		if(n % 2 == 0 || n % 3 == 0) {
			return false;
		}

		int root = (int) Math.sqrt(n);
		for(int i = 6; i <= root; i += 6) {
			if(n % (i - 1) == 0 || n % (i + 1) == 0) {
				return false;
			}
		}

		return true;
	}

	/**
	 * gives the next prime number, 'n' inclusive
	 * @param n - int
	 * @return - returns the next prime number that is
	 * 						greater than or equal to 'n'
	 */
	public int nextPrime(int n) {
		int next = n;
		while(!isPrime(next)) {
			if(next % 2 == 0) {
				next++;
			} else {
				next += 2;
			}
		}

		return next;
	}
}
