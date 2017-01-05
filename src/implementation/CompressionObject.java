package implementation;

import imageMain.StaticMethods;
import imageMain.StaticMethods.AxisTuple;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import genetic.GeneticObject;

public class CompressionObject extends GeneticObject {
	public int[][] matrix;
	public double fitness;
	CompressionEnvironment e;
	public static double mutationMagnitude = 1;

	// public

	public CompressionObject(int[][] matrix, CompressionEnvironment e) {
		this.matrix = matrix;
		this.e = e;
		calculateFitness();

	}

	public CompressionObject(int scalar, CompressionEnvironment e) {
		this.e = e;
		int[] hist = e.t.histogram;

		int[] histCP = new int[hist.length];

		for (int i = 0; i < hist.length; i++) {
			histCP[i] = hist[i];
		}
		int pos = scalar;
		matrix = new int[e.t.xAxis.length][e.t.yAxis.length];
		for (int x = 0; x < e.t.xAxis.length; ++x) {
			for (int y = 0; y < e.t.yAxis.length; ++y) {
				// while (histCP[pos] == 0) {
				// pos++;
				// pos %= hist.length;
				// }
				// matrix[x][y] = pos;
				// histCP[pos]--;
				matrix[x][y] = scalar;
			}
		}
		calculateFitness();
	}

	private void calculateFitness() {
		AxisTuple res = StaticMethods.calculateSumAndHist(matrix);
		int[] xAxis = res.xAxis;
		int[] yAxis = res.yAxis;
		int[] diagAxis = res.diagAxis;

		fitness = StaticMethods.calculateDiff(xAxis, yAxis, e.t.xAxis, e.t.yAxis, diagAxis, e.t.diagAxis);

		fitness += StaticMethods.calcHistDist(res.histogram, e.t.histogram);

	}

	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public GeneticObject combineWith(GeneticObject o) {
		Random rand = new Random();
		int xCorner1 = rand.nextInt(matrix.length);
		int xCorner2 = xCorner1 + rand.nextInt(matrix.length - xCorner1);
		int yCorner1 = rand.nextInt(matrix[0].length);
		int yCorner2 = yCorner1 + rand.nextInt(matrix[0].length - yCorner1);

		int[][] newMatrix = new int[matrix.length][matrix[0].length];
		CompressionObject other = (CompressionObject) o;
		for (int x = 0; x < matrix.length; ++x) {
			for (int y = 0; y < matrix[0].length; ++y) {
				newMatrix[x][y] = matrix[x][y];
			}

		}

		for (int x = xCorner1; x < xCorner2; ++x) {
			for (int y = yCorner1; y < yCorner2; ++y) {
				newMatrix[x][y] = other.matrix[x][y];
			}
		}
//		for (int i = 0; i < 5; i++){
//			if (rand.nextFloat() < 0.2f) {
//				movePixel(rand, newMatrix);
//			}
//		}
		
		// if (rand.nextFloat() < 0.1f) {
		// changePixel(rand, newMatrix);
		// }
		//
		for (int i = 0; i < 3; i++){
			if (rand.nextFloat() < 0.3f) {
				createRectangle(rand, newMatrix);
			}
		}

		// if (rand.nextFloat() < 0.01f) {
		// swapRectangle(rand, newMatrix);
		// }
		// if (rand.nextFloat() < 0.5f) {
		// swapPixel(rand, newMatrix);
		// }

		return new CompressionObject(newMatrix, e);
	}

	private void swapPixel(Random rand, int[][] newMatrix) {
		int x1 = rand.nextInt(matrix.length);
		int y1 = rand.nextInt(matrix[0].length);

		int x2 = rand.nextInt(matrix.length);
		int y2 = rand.nextInt(matrix[0].length);

		int temp = newMatrix[x1][y1];
		newMatrix[x1][y1] = newMatrix[x2][y2];

		newMatrix[x2][y2] = temp;

	}

	private void swapRectangle(Random rand, int[][] newMatrix) {
		int xCorner1 = rand.nextInt(matrix.length);
		int yCorner1 = rand.nextInt(matrix[0].length);
		int distX = rand.nextInt(matrix.length - xCorner1);
		int distY = rand.nextInt(matrix[0].length - yCorner1);
		int xCorner2 = rand.nextInt(matrix.length - distX);
		int yCorner2 = rand.nextInt(matrix[0].length - distY);

		int[][] temp = new int[distX][distY];
		int[][] temp2 = new int[distX][distY];

		// create temps
		for (int x = 0; x < distX; x++) {
			for (int y = 0; y < distY; y++) {
				temp[x][y] = matrix[x + xCorner1][y + yCorner1];
			}
		}
		for (int x = 0; x < distX; x++) {
			for (int y = 0; y < distY; y++) {
				temp2[x][y] = matrix[x + xCorner2][y + yCorner2];
			}
		}

		// swap
		for (int x = 0; x < distX; x++) {
			for (int y = 0; y < distY; y++) {
				matrix[x + xCorner1][y + yCorner1] = temp2[x][y];
			}
		}
		for (int x = 0; x < distX; x++) {
			for (int y = 0; y < distY; y++) {
				matrix[x + xCorner2][y + yCorner2] = temp[x][y];
			}
		}

	}

	private void movePixel(Random rand, int[][] newMatrix) {
		int x = rand.nextInt(matrix.length - 2) + 1;
		int y = rand.nextInt(matrix[0].length - 2) + 1;
		int temp = newMatrix[x][y];

		switch (rand.nextInt(4)) {
		case 0:
			newMatrix[x][y] = newMatrix[x + 1][y];
			newMatrix[x + 1][y] = temp;
			break;
		case 1:
			newMatrix[x][y] = newMatrix[x - 1][y];
			newMatrix[x - 1][y] = temp;
			break;
		case 2:
			newMatrix[x][y] = newMatrix[x][y + 1];
			newMatrix[x][y + 1] = temp;
			break;
		case 3:
			newMatrix[x][y] = newMatrix[x][y - 1];
			newMatrix[x][y - 1] = temp;
			break;
		}

	}

	private void changePixel(Random rand, int[][] newMatrix) {

		int x = rand.nextInt(matrix.length);
		int y = rand.nextInt(matrix[0].length);
		newMatrix[x][y] = Math.min(255, Math.max(0, (rand.nextInt(128) - 64) + newMatrix[x][y]));

	}

	private void createRectangle(Random rand, int[][] newMatrix) {
		int xCorner1;
		int xCorner2;
		int yCorner1;
		int yCorner2;
		xCorner1 = rand.nextInt(matrix.length);
		xCorner2 = (int) (xCorner1 + rand.nextInt(matrix.length - xCorner1) * mutationMagnitude);
		yCorner1 = rand.nextInt(matrix[0].length);
		yCorner2 = (int) (yCorner1 + rand.nextInt(matrix[0].length - yCorner1) * mutationMagnitude);

		int toPlace = rand.nextInt(256) - 128;
		for (int x = xCorner1; x < xCorner2; ++x) {
			for (int y = yCorner1; y < yCorner2; ++y) {
				newMatrix[x][y] = Math.min(255, Math.max(0, toPlace + newMatrix[x][y]));
			}
		}
	}

	public class IntTuple {
		public int i1;
		public int i2;

		public IntTuple(int i1, int i2) {
			this.i1 = i1;
			this.i2 = i2;
		}
	}

	public void reAdjust() {
		AxisTuple res = StaticMethods.calculateSumAndHist(matrix);

		int[] correct = e.t.histogram;
		int[] mine = res.histogram;

		ArrayList<IntTuple> negativeBalance = new ArrayList<IntTuple>();
		HashMap<Integer, Integer> positiveBalance = new HashMap<Integer, Integer>();

		for (int i = 0; i < correct.length; i++) {
			int balance = mine[i] - correct[i];
			if (balance > 0) {
				positiveBalance.put(i, balance);
			} else if (balance < 0) {
				negativeBalance.add(new IntTuple(i, balance));
			}
		}
		Random rand = new Random();
		int startX = rand.nextInt(matrix.length - 1) + 1;
		int startY = rand.nextInt(matrix[0].length - 1) + 1;

		//System.out.println("work work");
		for (int x = startX; x != startX - 1; x++, x %= matrix.length) {
			for (int y = startY; y != startY - 1; y++, y %= matrix[0].length) {
				if (positiveBalance.get(matrix[x][y]) != null && positiveBalance.get(matrix[x][y]) > 0) {
					int posBal = positiveBalance.get(matrix[x][y]);
					posBal--;
					positiveBalance.put(matrix[x][y], posBal);

					// int index = rand.nextInt(negativeBalance.size());
					IntTuple sel = negativeBalance.get(rand.nextInt(negativeBalance.size()));
					matrix[x][y] = sel.i1;
					sel.i2--;
					if (sel.i2 == 0)
						negativeBalance.remove(sel);
				}
			}
		}
	}

}
