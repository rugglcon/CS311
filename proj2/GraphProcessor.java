import java.io.*;
import java.util.*;
/**
 * @author Connor Ruggles
 */
public class GraphProcessor {
	private WebGraph graph;
	private Vertex[] finishes;
	private ArrayList<ArrayList<String>> sccs;

	public GraphProcessor(String graphData)
			throws FileNotFoundException {
		graph = new WebGraph();
		File edges = new File(graphData);
		Scanner scan = new Scanner(edges);
		// size = Integer.parseInt(scan.nextLine());
		scan.nextLine();
		while(scan.hasNextLine()) {
			String[] line = scan.nextLine().split(" ");
			if(line.length == 2) {
				graph.addEdge(line[0], line[1]);
			} else {
				graph.addVertex(line[0]);
			}
		}
		scan.close();
		finishes = new Vertex[graph.size() + 1];
		sccs = new ArrayList<ArrayList<String>>();
		sccAlgo();
	}

	public int outDegree(String v) {
		if(!graph.hasVertex(v)) {
			return -1;
		}
		return graph.getVertex(v).getEdges().size();
	}

	public boolean sameComponent(String u, String v) {
		if(graph.hasVertex(u) && graph.hasVertex(v)) {
			for(int i = 0; i < sccs.size(); i++) {
				if(sccs.get(i).contains(u) && sccs.get(i).contains(v)) {
					return true;
				}
			}
		}

		return false;
	}

	public ArrayList<String> componentVertices(String v) {
		ArrayList<String> ret = new ArrayList<String>();
		if(graph.hasVertex(v)) {
			for(int i = 0; i < sccs.size(); i++) {
				if(sccs.get(i).contains(v)) {
					return sccs.get(i);
				}
			}
		}
		return ret;
	}

	public int largestComponent() {
		int largest = 0;
		for(ArrayList<String> a : sccs) {
			if(a.size() > largest) {
				largest = a.size();
			}
		}
		return largest;
	}

	public int numComponents() {
		return sccs.size();
	}

	public ArrayList<String> bfsPath(String u, String v) {
		ArrayList<String> path = new ArrayList<String>();
		Queue<String> q = new LinkedList<String>();
		HashSet<String> vis = new HashSet<String>();
		HashMap<String, String> prev = new HashMap<String, String>();
		HashMap<String, Vertex> verts = graph.getVertices();
		String cur = u;
		if(verts.get(cur) == null) {
			path.clear();
			return path;
		}
		q.add(cur);
		vis.add(cur);
		while(!q.isEmpty()) {
			cur = q.remove();
			HashMap<String, Edge> curEdges = verts.get(cur).getEdges();
			if(cur.equals(v)) {
				break;
			} else {
				for(String s : curEdges.keySet()) {
					if(!vis.contains(s)) {
						q.add(s);
						vis.add(s);
						prev.put(s, cur);
					}
				}
			}
		}
		if(!cur.equals(v)) {
			path.clear();
			return path;
		}
		ArrayList<String> tmp = new ArrayList<String>();
		for(String s = v; s != null; s = prev.get(s)) {
			tmp.add(s);
		}
		for(int i = tmp.size(); i > 0; i--) {
			path.add(tmp.remove(i - 1));
		}
		if(u.equals(v)) {
			path.add(v);
		}
		return path;
	}

	private void dfs(WebGraph wg, Vertex u) {
		HashMap<Vertex, Boolean> marks = new HashMap<Vertex, Boolean>();
		HashMap<String, Vertex> verts = wg.getVertices();
		HashMap<Vertex, Integer> counts = new HashMap<Vertex, Integer>();
		for(Vertex v : verts.values()) {
			marks.put(v, false);
			counts.put(v, -1);
		}
		int counter = 0;
		for(Vertex v : marks.keySet()) {
			if(!marks.get(v)) {
				recDFS(wg, v, marks, counter, counts);
			}
		}

	}

	private void recDFS(WebGraph wg, Vertex v, HashMap<Vertex, Boolean> marks, int counter, HashMap<Vertex, Integer> counts) {
		HashMap<String, Vertex> verts = wg.getVertices();
		marks.put(v, true);
		counter++;
		counts.put(v, counter);
		for(String u : v.getEdges().keySet()) {
			if(!marks.get(verts.get(u))) {
				recDFS(wg, verts.get(u), marks, counter, counts);
			}
		}
	}

	private int orderNum = 0;
	private HashMap<Vertex, Boolean> marks = new HashMap<Vertex, Boolean>();
	private void computeOrder() {
		marks = new HashMap<Vertex, Boolean>();
		WebGraph gr = new WebGraph();
		for(Vertex v : graph.getVertices().values()) {
			marks.put(v, false);
			for(Edge e : v.getEdges().values()) {
				gr.addEdge(e.getTo().getURL(), e.getFrom().getURL());
			}
		}
		for(Vertex v : marks.keySet()) {
			if(!marks.get(v)) {
				finishDFS(gr, v);
			}
		}
	}

	private void finishDFS(WebGraph gr, Vertex v) {
		marks.put(v, true);
		for(String e : v.getEdges().keySet()) {
			if(!marks.get(graph.getVertex(e))) {
				finishDFS(gr, graph.getVertex(e));
			}
		}
		orderNum++;
		finishes[orderNum] = v;
	}

	private void sccAlgo() {
		computeOrder();
		marks = new HashMap<Vertex, Boolean>();
		for(Vertex v : graph.getVertices().values()) {
			marks.put(v, false);
		}
		for(int i = finishes.length - 1; i > 0; i--) {
			if(!marks.get(finishes[i])) {
				ArrayList<String> set = new ArrayList<String>();
				sccDFS(finishes[i], set);
				sccs.add(set);
			}
		}
	}

	private void sccDFS(Vertex v, ArrayList<String> set) {
		marks.put(v, true);
		set.add(v.getURL());
		for(Edge e : graph.getVertex(v.getURL()).getEdges().values()) {
			if(!marks.get(e.getTo())) {
				sccDFS(e.getTo(), set);
			}
		}
	}

	/**
 	 * @author Connor Ruggles
	 *
	 * This class holds a Map of vertices that are mapped
 	 * by a key of type String.
 	 */
	private class WebGraph {
		private HashMap<String, Vertex> vertices;

		/**
	 	 * Constructor. Sets the map of vertices
	 	 * to a new empty map.
	 	 */
		public WebGraph() {
			vertices = new HashMap<String, Vertex>();
		}

		/**
	 	 * This method checks if vertices are already
	 	 * mapped to the keys in the parameters, and
	 	 * adds a new edge if they are. Otherwise, adds
	 	 * a new Vertex to the map and then adds a new
	 	 * Edge.
	 	 * @param from - String - 'from' Vertex
	 	 * @param to - String - 'to' Vertex
	 	 */
		public void addEdge(String from, String to) {
			if(vertices.containsKey(from)) {
				if(!vertices.containsKey(to)) {
					vertices.put(to, new Vertex(to));
				}
				vertices.get(from).addEdge(vertices.get(to));
			} else {
				Vertex v = new Vertex(from);
				if(!vertices.containsKey(to)) {
					vertices.put(to, new Vertex(to));
				}
				v.addEdge(vertices.get(to));
				vertices.put(from, v);
			}
		}

		public void addVertex(String v) {
			if(!vertices.containsKey(v)) {
				vertices.put(v, new Vertex(v));
			}
		}

		/**
		 * This method returns a HashMap<String, Vertex>
		 * of all vertices in this WebGraph.
		 */
		public HashMap<String, Vertex> getVertices() {
			return vertices;
		}

		public boolean hasVertex(String v) {
			return vertices.containsKey(v);
		}

		public Vertex getVertex(String v) {
			return vertices.get(v);
		}

		public int size() {
			return vertices.size();
		}

		/**
		 * This method is a different interpretation
		 * of the toString() method, but does not
		 * override it.
		 */
		public String toString() {
			String ret = "";
			ret += vertices.size() + "\n";
			for(Vertex v : vertices.values()) {
				ret += v.toString();
			}
			return ret;
		}
	}

	/**
	 * @author Connor Ruggles
	 *
	 * This class represents an edge in the WebGraph.
	 */
	private class Edge {
		private Vertex from;
		private Vertex to;

		/**
		 * Constructor. Sets the from and to Vertex
		 * to the given vertices.
		 * @param from - Vertex - 'from' Vertex
		 * @param to - Vertex - 'to' Vertex
		 */
		public Edge(Vertex from, Vertex to) {
			this.from = from;
			this.to = to;
		}

		/**
		 * This method returns the Vertex in the
		 * 'from' spot.
		 * @return Vertex - Vertex in the 'from' spot
		 */
		public Vertex getFrom() {
			return from;
		}

		/**
		 * This method returns the Vertex in the
		 * 'to' spot.
		 * @return Vertex - Vertex in the 'to' spot
		 */
		public Vertex getTo() {
			return to;
		}

		/**
		 * This method is a different interpretation
		 * of the toString() method, but does not
		 * override it.
		 */
		public String toString() {
			return from.getURL() + " " + to.getURL() + "\n";
		}
	}
	/**
	 * @author Connor Ruggles
	 *
	 * This class hold a String that represents
	 * a Vertex in a WebGraph, and a map of edges
	 * that this Vertex is a part of.
	 */
	public class Vertex {
		private String url;
		private HashMap<String, Edge> edges;

		/**
		 * Constructor. Sets the url of the Vertex
		 * to the given String, then sets the map
		 * of edges to a new empty map.
		 * @param url - String - url to be represented
		 */
		public Vertex(String url) {
			this.url = url;
			edges = new HashMap<String, Edge>();
		}

		/**
		 * This method adds a new Edge with the given
		 * Vertex.
		 * @param to - Vertex - Vertex that the new Edge
		 * will point to
		 */
		public void addEdge(Vertex to) {
			if(!edges.containsKey(to.getURL())) {
				edges.put(to.getURL(), new Edge(this, to));
			}
		}

		/**
		 * This method returns the url that is
		 * represented by this Vertex.
		 * @return String - url that is
		 * represented by this Vertex.
		 */
		public String getURL() {
			return url;
		}

		/**
		 * This returns true if the given Vertex's
		 * url matches this one, false otherwise.
		 * @param v - Vertex - Vertex to be tested against
		 * @return boolean - true if this equals v, false
		 * otherwise
		 */
		public boolean equals(Vertex v) {
			return url.equals(v.getURL());
		}

		/**
		 * Returns a HashMap<String, Edge> of the
		 * edges of this Vertex.
		 * @return HashMap<String, Edge> - map of this
		 * vertex's edges
		 */
		public HashMap<String, Edge> getEdges() {
			return edges;
		}

		/**
		 * This method is a different interpretation
		 * of the toString() method, but does not
		 * override it.
		 */
		public String toString() {
			String ret = "";
			for(Edge e : edges.values()) {
				ret += e.toString();
			}
			return ret;
		}
	}
}
