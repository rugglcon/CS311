import java.util.ArrayList;

/**
 * 
 * @author Connor Ruggles
 *
 */
public class HashTable {
	/**
	 * size of this table
	 */ 
	private int size;

	/**
	 * variable for storing the current
	 * max load of the table
	 */
	private int curMaxLoad;

	/**
	 * array of items in this table
	 */ 
	ArrayList<ArrayList<Tuple>> items;

	/**
	 * hash function for this table
	 */
	HashFunction func;

	public HashTable(int size) {
		int p = 0;
		Helper helper = new Helper();

		if(size == 1 || size == 2 || size == 3) {
			p = size;
		} else {
			p = helper.nextPrime(size);
		}

		items = new ArrayList<ArrayList<Tuple>>();
		for(int i = 0; i < p; i++) {
			items.add(new ArrayList<Tuple>());
		}
		func = new HashFunction(p);
		curMaxLoad = 0;
		this.size = p;
	}

	public int maxLoad() {
		return curMaxLoad;
	}

	public int averageLoad() {
		int tmpSize = 0;
		int numCells = 0;
		for(int i = 0; i < items.size(); i++) {
			if(items.get(i) != null) {
				tmpSize += items.get(i).size();
				numCells++;
			}
		}
		return tmpSize/numCells;
	}

	public int size() {
		return size;
	}

	public int numElements() {
		int num = 0;
		for(int i = 0; i < size; i++) {
			if(items.get(i) != null) {
				num += items.get(i).size();
				if(items.get(i).size() > curMaxLoad) {
					curMaxLoad = items.get(i).size();
				}
			}
		}
		return num;
	}

	public int loadFactor() {
		return numElements() / size();
	}

	public void add(Tuple t) {
		if(items.get(func.hash(t.getKey())) == null) {
			items.set(func.hash(t.getKey()), new ArrayList<Tuple>());
		}
		items.get(func.hash(t.getKey())).add(t);
		if(items.get(func.hash(t.getKey())).size() > curMaxLoad) {
			curMaxLoad = items.get(func.hash(t.getKey())).size();
		}

		if(loadFactor() > 0.7) {
			Helper helper = new Helper();
			int newSize = helper.nextPrime(size*2);

			ArrayList<ArrayList<Tuple>> tmp = new ArrayList<ArrayList<Tuple>>(newSize);
			func = new HashFunction(newSize);
			
			for(int i = 0; i < newSize; i++) {
				tmp.add(new ArrayList<Tuple>());
			}

			for(int i = 0; i < size; i++) {
				if(items.get(i) != null) {
					if(items.get(i).size() > 0) {
						tmp.set(func.hash(items.get(i).get(0).getKey()), items.get(i));
					}
				}
			}
			size = newSize;
			items = tmp;
		}
	}

	public ArrayList<Tuple> search(int k) {
		ArrayList<Tuple> list = items.get(func.hash(k));
		if(list == null) {
			list = new ArrayList<Tuple>();
		}

		return list;
	}

	public void remove(Tuple t) {
		items.get(func.hash(t.getKey())).remove(t);
	}
}
