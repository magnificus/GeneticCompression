package image2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import genetic.GeneticComp;
import genetic.GeneticEnvironment;
import genetic.GeneticObject;

public class CompressionEnvironment extends GeneticEnvironment {

	public int nbrObjects = 300;
	public int count = 0;
	public int[][] matrix;

	public CompressionEnvironment() {
		objects = Collections.synchronizedList(new ArrayList<GeneticObject>());
		for (int i = 0; i < nbrObjects; i++) {
			objects.add(new CompressionObject());
		}

	}

	public int[][][] getBest() {
		Collections.sort(objects, new GeneticComp());
		return ((CompressionObject) objects.get(0)).getMatrix();
	}

	@Override
	public double SimulationTick() {
		count++;

		return super.SimulationTick();

	}

}
