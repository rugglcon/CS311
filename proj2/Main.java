import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException, InterruptedException {
		GraphProcessor gp = new GraphProcessor("WikiCS.txt");
		System.out.println(gp.bfsPath("/wiki/Computer_science", "/wiki/Human").toString());
		System.out.println(gp.largestComponent());
		System.out.println(gp.outDegree("/wiki/Computer_science"));
		System.out.println(gp.numComponents());
		System.out.println(gp.sameComponent("/wiki/Computer_science", "/wiki/Probability"));
		System.out.println(gp.componentVertices("/wiki/Computer_science"));
	}
}