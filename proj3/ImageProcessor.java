import java.awt.Color;
import java.util.ArrayList;

/**
 * @author Connor Ruggles and Josh Grittner
 */
public class ImageProcessor {
	private Picture pic;
	private int W;
	private int H;
	public ImageProcessor(String imageFile) {
		pic = new Picture(imageFile);
		W = pic.width();
		H = pic.height();
	}

	public Picture reduceWidth(double x) {
		Picture newPic = new Picture(pic);
		int w = W;
		for(int k = 0; k < W - ((int) Math.ceil(W * x)); k++) {
			w = w - 1;
			int I[][] = new int[H][w];
			for(int i = 0; i < H; i++) {
				for(int j = 0; j < w; j++) {
					int yImp = 0;
					int xImp = 0;

					// get y importance
					if(i == 0) {
						yImp = calcDist(newPic.get(j, H - 1), newPic.get(j, i + 1));
					} else if(i == H - 1) {
						yImp = calcDist(newPic.get(j, i - 1), newPic.get(j, 0));
					} else {
						yImp = calcDist(newPic.get(j, i - 1), newPic.get(j, i + 1));
					}

					// get x importance
					if(j == 0) {
						xImp = calcDist(newPic.get(w - 1, i), newPic.get(j + 1, i));
					} else if(j == w - 1) {
						xImp = calcDist(newPic.get(0, i), newPic.get(j - 1, i));
					} else {
						xImp = calcDist(newPic.get(j - 1, i), newPic.get(j + 1, i));
					}

					// now fill that spot in the importance array
					I[i][j] = xImp + yImp;
				}
			}

			ArrayList<Integer> minCut = DynamicProgramming.minCostVC(I);
			Picture tmpPicture = new Picture(w, H);
			// make a new picture object with the ceiling of
			// the old width * x, and the same height
			for(int m = 0; m < H; m++) {
				// index for the new picture
				int tn = 0;
				for(int n = 0; n < w; n++) {
					boolean remove = false;
					for(int i = 0; i < minCut.size() - 1; i += 2) {
						if(minCut.get(i) == m) {
							if(minCut.get(i + 1) == n) {
								// loop through the cut to get whether this index
								// shouldn't be added
								remove = true;
								break;
							}
						}
					}
					if(!remove) {
						// set pixel at correct index for new image
						// if it should be added
						tmpPicture.set(tn, m, newPic.get(n, m));
						tn++;
					}
				}
			}

			// set picture to the newly resized image
			newPic = new Picture(tmpPicture);
		}

		return newPic;
	}

	private int calcDist(Color p, Color q) {
		return (int) (Math.pow(p.getRed() - q.getRed(), 2.0) +
		Math.pow(p.getGreen() - q.getGreen(), 2.0) +
		Math.pow(p.getBlue() - q.getBlue(), 2.0));
	}
}