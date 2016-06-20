package main;

public class StaticMethods {
	public static final int histPenalty = 20;
	
	public static class AxisTuple {
		public int[] xAxis;
		public int[] yAxis;
		public int[] diagAxis;
		public int[] histogram;

		public AxisTuple(int[] xAxis, int[] yAxis, int[] diagAxis, int[] histogram) {
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.diagAxis = diagAxis;
			this.histogram = histogram;
		}
	}

	public static AxisTuple calculateSums(int[][] matrix) {
		int[] xAxis = new int[matrix.length];
		int[] yAxis = new int[matrix[0].length];
		int[] diagAxis = new int[matrix.length + matrix[0].length];

		for (int x = 0; x < xAxis.length; x++) {
			int totX = 0;
			for (int y = 0; y < yAxis.length; y++) {
				totX += matrix[x][y];
			}
			xAxis[x] = totX;
		}
		for (int y = 0; y < yAxis.length; y++) {
			int totY = 0;
			for (int x = 0; x < xAxis.length; x++) {
				totY += matrix[x][y];
			}
			yAxis[y] = totY;
		}
		for (int c = 0; c < xAxis.length ; c++){
			int totC = 0;
			for (int c2 = c; c2 > 0 && c - c2 > 0; c2--){
				totC += matrix[c2][c-c2];
			}
			diagAxis[c] = totC;
		}
		
		for (int c = 0; c < yAxis.length ; c++){
			int totC = 0;
			for (int c2 = c; c2 > 0 && xAxis.length - c2 > 0; c2--){
				totC += matrix[xAxis.length-c][c2];
			}
			diagAxis[c + xAxis.length] = totC;
		}


		return new AxisTuple(xAxis, yAxis, diagAxis, null);

	}
	
	public static AxisTuple calculateSumAndHist(int[][] matrix){
		AxisTuple t = calculateSums(matrix);
		int[] histogram = new int[256];
		for (int x = 0; x < matrix.length; x++){
			for (int y = 0; y < matrix[0].length; y++){
				histogram[matrix[x][y]]++;
			}
		}
		t.histogram = histogram;
		return t;
	}

	public static int calculateDiff(int[] xAxis1, int[] yAxis1, int[] xAxis2, int[] yAxis2,
			int[] diagAxis1, int[] diagAxis2) {
		
		int diff = 0;
		for (int x = 0; x < xAxis1.length; x++) {
			diff += Math.abs(xAxis1[x] - xAxis2[x]);
		}
		for (int y = 0; y < yAxis1.length; y++) {
			diff += Math.abs(yAxis1[y] - yAxis2[y]);
		}
		for (int d = 0; d < diagAxis1.length; d++) {
			diff += Math.abs(diagAxis1[d] - diagAxis2[d]);
		}
		return diff;

	}
	
	public static int calcHistDist(int[] hist1, int[] hist2){
		int tot = 0;
		for (int i = 0; i < hist1.length; i++){
			tot += Math.abs(hist1[i] - hist2[i])*histPenalty;
		}
		return tot;
	}
}
