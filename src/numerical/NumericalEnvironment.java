package numerical;

import java.util.ArrayList;
import java.util.Collections;

import text.Main;
import text.TextGeneticObject;
import genetic.GeneticEnvironment;
import genetic.GeneticObject;

public class NumericalEnvironment extends GeneticEnvironment{
	
	public double[] input;
	public double[] correct;
	
	
	public static final int size = 1000;
	
	public static final int numberObjects = 10000;
	
	public NumericalEnvironment(){
		input = new double[size];
		correct = new double[size];
		
		for (int i = 1; i < size; i++){
			int j = i;
			input[i] = j;
			correct[i] = j * 100 + 44 % 5;
		}
		
		objects = Collections.synchronizedList(new ArrayList<GeneticObject>());
		for (int i = 0; i < numberObjects; i++){
			NumericalObject n = new NumericalObject(this);
			objects.add(n);
		}
	}

	public void normalize() {
		for (GeneticObject o : objects){
			o.normalize();
		}
		
	}

}
