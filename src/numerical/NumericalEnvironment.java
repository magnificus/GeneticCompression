package numerical;

import java.util.ArrayList;
import java.util.Collections;

import text.Main;
import text.TextGeneticObject;
import genetic.GeneticEnvironment;
import genetic.GeneticObject;

public class NumericalEnvironment extends GeneticEnvironment{
	
	public int[] input;
	public int[] correct;
	
	
	public static final int size = 10000;
	
	public static final int numberObjects = 10000;
	
	public NumericalEnvironment(){
		input = new int[size];
		correct = new int[size];
		
		for (int i = 0; i < size; i++){
			int j = 2*i;
			input[i] = j;
			correct[i] = (int) Math.log(j);
			
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
