import java.util.Random;

/**
 * 
 * @author Connor Ruggles
 *
 */
public class HashFunction {
	/**
	 * integers for this hash function
	 */
	private int a, b, p;

	/**
	 * Sets 'p' to the next positive prime integer
	 * that is greater than or equal to 'range', then
	 * sets 'a' and 'b' to a random number between 0 and p - 1, 
	 * @param range - int
	 */
	public HashFunction(int range) {
		if(range == 1 || range == 2 || range == 3) {
			p = range;
		} else {
			Helper helper = new Helper();
			p = helper.nextPrime(range);
		}

		Random rand = new Random();
		a = rand.nextInt(p);
		b = rand.nextInt(p);
	}

	public int hash(int x) {
		long result = ((a * x) + b) % p;
//		if(result < 0) {
//			result += p;
//		}

		return (int) result;
	}

	public int getA() {
		return a;
	}

	public int getB() {
		return b;
	}

	public int getP() {
		return p;
	}

	public void setA(int x) {
		a = x % p;
	}

	public void setB(int y) {
		b = y % p;
	}

	public void setP(int x) {
		int num = x;
		
		Random rand = new Random();
		if(num == 1) {
			p = 2;
			a = rand.nextInt(p);
			b = rand.nextInt(p);
		}
		if(num == 2 || num == 3) {
			p = num;
			a = rand.nextInt(p);
			b = rand.nextInt(p);
			return;
		}
		Helper helper = new Helper();
		
		p = helper.nextPrime(num);
		a = rand.nextInt(p);
		b = rand.nextInt(p);
	}
	
	
}
