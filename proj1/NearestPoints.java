import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * 
 * @author Connor Ruggles
 *
 */
public class NearestPoints {
	private ArrayList<Float> data;
	private HashTable table;
	private static final String NRESULT_FILE = "NaiveSolution.txt";
	private static final String HRESULT_FILE = "HashSolution.txt";

	public NearestPoints(String dataFile) {
		data = new ArrayList<Float>();
		File tmpData;
		Scanner scanner;
		try {
			tmpData = new File(dataFile);
			scanner = new Scanner(tmpData);
		} catch(FileNotFoundException f) {
			System.out.println("file not found");
			return;
		}
		while(scanner.hasNextLine()) {
			data.add(Float.parseFloat(scanner.nextLine()));
		}

		buildDataStructure();
	}

	public NearestPoints(ArrayList<Float> pointSet) {
		data = pointSet;
		buildDataStructure();
	}

	public ArrayList<Float> naiveNearestPoints(float p) {
		ArrayList<Float> points = new ArrayList<Float>();
		for(int i = 0; i < data.size(); i++) {
			if(Math.abs(p - data.get(i)) <= 1) {
				points.add(data.get(i));
			}
		}
		return points;
	}

	public void buildDataStructure() {
		table = new HashTable((int) 1.5 * data.size());
		for(int i = 0; i < data.size(); i++) {
			table.add(new Tuple((int) Math.floor(data.get(i)), data.get(i)));
		}
	}

	public ArrayList<Float> npHashNearestPoints(float p) {
		ArrayList<Float> points = new ArrayList<Float>();
		ArrayList<Tuple> listAt = table.search((int) Math.floor(p));
		ArrayList<Tuple> listMinus = table.search((int) Math.floor(p - 1));
		ArrayList<Tuple> listPlus = table.search((int) Math.floor(p + 1));

		for(int i = 0; i < listAt.size(); i++) {
			if(Math.abs(p - listAt.get(i).getValue()) <= 1) {
				points.add(listAt.get(i).getValue());
			}
		}
		for(int i = 0; i < listMinus.size(); i++) {
			if(Math.abs(p - listMinus.get(i).getValue()) <= 1) {
				points.add(listMinus.get(i).getValue());
			}
		}
		for(int i = 0; i < listPlus.size(); i++) {
			if(Math.abs(p - listPlus.get(i).getValue()) <= 1) {
				points.add(listPlus.get(i).getValue());
			}
		}
		return points;
	}

	public void allNearestPointsNaive() throws IOException {
		File nresult_f = new File(NRESULT_FILE);
		nresult_f.createNewFile();
		FileWriter fw;
		try {
			fw = new FileWriter(nresult_f);

			for(int i = 0; i < data.size(); i++) {
				float p = data.get(i);
				String line = "";
				line += p;
				for(int j = 0; j < data.size(); j++) {
					if(Math.abs(p - data.get(j)) <= 1) {
						line += " " + data.get(j);
					}
				}
				fw.write(line + "\n");
			}

			fw.flush();
			fw.close();
		} catch(IOException e) {
			System.out.println("error: " + e.getMessage());
		}
	}

	public void allNearestPointsHash() throws IOException {
		File hresult_f = new File(HRESULT_FILE);
		hresult_f.createNewFile();
		FileWriter fw;
		try {
			fw = new FileWriter(hresult_f);
		
			for(int j = 0; j < data.size(); j++) {
				float p = data.get(j);
				String line = "";
				line += p;
				ArrayList<Tuple> listAt = table.search((int) Math.floor(p));
				ArrayList<Tuple> listMinus = table.search((int) Math.floor(p - 1));
				ArrayList<Tuple> listPlus = table.search((int) Math.floor(p + 1));

				for(int i = 0; i < listAt.size(); i++) {
					if(Math.abs(p - listAt.get(i).getValue()) <= 1) {
						line += " " + listAt.get(i).getValue();
					}
				}
				for(int i = 0; i < listMinus.size(); i++) {
					if(Math.abs(p - listMinus.get(i).getValue()) <= 1) {
						line += " " + listMinus.get(i).getValue();
					}
				}
				for(int i = 0; i < listPlus.size(); i++) {
					if(Math.abs(p - listPlus.get(i).getValue()) <= 1) {
						line += " " + listPlus.get(i).getValue();
					}
				}

				fw.write(line + "\n");
			}
			fw.flush();
			fw.close();
		} catch(IOException e) {
			System.out.println("error: " + e.getMessage());
		}
	}
}
