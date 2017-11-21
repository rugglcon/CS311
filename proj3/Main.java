public class Main {
	public static void main(String[] args) {
		// int[][] M = {{4, 1, 6, 9, 4, 3, 6, 5}, {3, 6, 9, 4, 2, 6, 4, 8}, {3, 2, 1, 6, 4, 3, 5, 8}, {43, 7, 23, 78, 46, 8, 4, 6}};
		// System.out.println(DynamicProgramming.minCostVC(M).toString());
		// ImageProcessor ip = new ImageProcessor("/home/connor/current.classes/cs311/projects/proj3/Original.jpg");
		ImageProcessor ip = new ImageProcessor("/home/connor/Pictures/blackx250.jpg");
		Picture pic = ip.reduceWidth(.735);
		pic.show();
	}
}