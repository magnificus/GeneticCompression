package text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import genetic.GeneticComp;
import genetic.GeneticEnvironment;
import genetic.GeneticObject;

public class TextCompEnvironment extends GeneticEnvironment {


	public int maxCount = 1000;
	public int count = 0;
	
	
	public TextCompEnvironment(){
		objects = Collections.synchronizedList(new ArrayList<GeneticObject>());
		for (int i = 0; i < maxCount; i++){
			TextGeneticObject t = new TextGeneticObject(getRandomString(Main.length));
			objects.add(t);
		}

	}
	
	
	private String getRandomString(int length) {
		Random rand = new Random();
		String tot = "";
		for (int i = 0; i < length; i++){
			tot += (char)(rand.nextInt(26) + 'a');
		}
		return tot;
	}


	@Override
	public double SimulationTick(){
		count++;
		
		return super.SimulationTick();
		
		
	}

}
