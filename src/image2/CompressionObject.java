package image2;


import genetic.GeneticObject;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CompressionObject extends GeneticObject {
	
	public static final int numShapes = 50;

	public double fitness;
	//public int[][] matrix;
	public CompressionEnvironment e;
	public List<Shape> shapes;


	public CompressionObject() {
		shapes = new ArrayList<Shape>();
		Random r = new Random();
		for (int i = 0; i < 1; i++){
			int xLen = Main.correctMatrix.length;
			int yLen = Main.correctMatrix[0].length;
			Point p1 = new Point(r.nextInt(xLen), r.nextInt(yLen));
			Point p2 = new Point(r.nextInt(xLen - p1.x) + p1.x, r.nextInt(yLen - p1.y) + p1.y);
			int strength = r.nextInt(20);
			shapes.add(new Shape.Rectangle(p1,p2,strength));
		}
		calculateFitness();
	}
	
	public CompressionObject(List<Shape> shapes) {
		this.shapes = shapes;
		calculateFitness();
	}



	private void calculateFitness() {
		int[][] matrix = getMatrix();
		int tot = 0;
		for (int x = 0; x < matrix.length; x++){
			for (int y = 0; y < matrix[0].length; y++){
				tot += Math.abs(Main.correctMatrix[x][y] - matrix[x][y]);
			}
		}
		fitness = tot;
	}


	@Override
	public void normalize() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public double getFitness() {
		return fitness;
	}


	@Override
	public GeneticObject combineWith(GeneticObject o) {
		List<Shape> newShapes = new ArrayList<Shape>();
		Random r = new Random();
		for (Shape s : shapes){
			Shape s1 = s.duplicate();
			s1.mutate(r);
			newShapes.add(s1);
		}
		
		if (newShapes.size() <= numShapes && r.nextFloat() > 0.1f){
			int xLen = Main.correctMatrix.length;
			int yLen = Main.correctMatrix[0].length;
			Point p1 = new Point(r.nextInt(xLen), r.nextInt(yLen));
			Point p2 = new Point(r.nextInt(xLen - p1.x) + p1.x, r.nextInt(yLen - p1.y) + p1.y);
			int strength = r.nextInt(20);
			newShapes.add(new Shape.Rectangle(p1,p2,strength));
		}
		if (r.nextFloat() < 0.1f && !newShapes.isEmpty()){
			newShapes.remove(r.nextInt(newShapes.size()));
		}

		return new CompressionObject(newShapes);
	}

	public int[][] getMatrix() {
		int[][] matrix = new int[Main.correctMatrix.length][Main.correctMatrix[0].length];
		for (Shape s : shapes){
			s.apply(matrix);
		}
		return matrix;
	}

}
