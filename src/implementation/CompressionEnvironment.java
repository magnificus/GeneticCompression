package implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import genetic.GeneticComp;
import genetic.GeneticEnvironment;
import genetic.GeneticObject;
import main.StaticMethods.AxisTuple;


public class CompressionEnvironment extends GeneticEnvironment{

	public int maxCount = 2000;
	public int count = 0;
	public AxisTuple t;
	public CompressionEnvironment(AxisTuple t){
		this.t = t;
		objects = Collections.synchronizedList(new ArrayList<GeneticObject>());
		objects.add(new CompressionObject(0, this));
		objects.add(new CompressionObject(1, this));
		for (int j = 0; j < 1; ++j){
			for (int i = 0; i <= 255; i++){
				objects.add(new CompressionObject(i, this));
			}
		}

	}
	
	public int[][] getBest(){
		Collections.sort(objects, new GeneticComp());
		return ((CompressionObject) objects.get(0)).matrix;
	}
	
	@Override
	public double SimulationTick(){
		count++;
		
//		if (count == maxCount){
//			for (GeneticObject c : objects){
//				CompressionObject o = (CompressionObject) c;
//				o.reAdjust();
//			}
//			
//			count = 0;
//		}
		return super.SimulationTick();
		
		
	}

}
