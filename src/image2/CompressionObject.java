package image2;

import genetic.GeneticObject;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompressionObject extends GeneticObject {

	public static final int numShapes = 10;

	public double fitness;
	// public int[][] matrix;
	public CompressionEnvironment e;
	public List<EvolShape> shapes;

	public CompressionObject() {
		shapes = new ArrayList<EvolShape>();
		Random r = new Random();
		for (int i = 0; i < numShapes; i++) {
			shapes.add(EvolShape.getNewShape(r));
		}
		calculateFitness();
	}

	public CompressionObject(List<EvolShape> shapes) {
		this.shapes = shapes;
		calculateFitness();
	}

	private void calculateFitness() {
		int[][] matrix = getMatrix();
		long tot = 0;
		for (int x = 0; x < matrix.length; x++) {
			for (int y = 0; y < matrix[0].length; y++) {
				tot += Math.pow(Math.abs(Main.correctMatrix[x][y] - matrix[x][y]), 2);
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
		List<EvolShape> newShapes = new ArrayList<EvolShape>();
		Random r = new Random();
		for (int i = 0; i < numShapes/2 && i < shapes.size(); i++){
			EvolShape s1 = shapes.get(i).duplicate();
			s1.mutate(r);
			newShapes.add(s1);
		}

		 CompressionObject other = (CompressionObject) o;
		for (int i = numShapes/2; i < numShapes && i < other.shapes.size(); i++){
			EvolShape s1 = other.shapes.get(i).duplicate();
			s1.mutate(r);
			newShapes.add(s1);
		}
		// for (Shape s : other.shapes){
		// if (r.nextFloat() < 0.5f && newShapes.size() < numShapes){
		// Shape s1 = s.duplicate();
		// s1.mutate(r);
		// newShapes.add(s1);
		// }
		// }


		if (r.nextFloat() < 0.5f && !newShapes.isEmpty()) {
			newShapes.remove(r.nextInt(newShapes.size()));
		}
		if (newShapes.size() < numShapes && r.nextFloat() > 0.5f) {
			newShapes.add(EvolShape.getNewShape(r));
		}

		return new CompressionObject(newShapes);
	}

	public int[][] getMatrix() {
		int[][] matrix = new int[Main.correctMatrix.length][Main.correctMatrix[0].length];
		for (EvolShape s : shapes) {
			s.apply(matrix);
		}
		return matrix;
	}

}
