package text;

import java.util.Random;

import genetic.GeneticObject;

public class TextGeneticObject extends GeneticObject{
	public String currString;
	public double fitness;
	public double mutationChance = 0.1;
	
	public TextGeneticObject(String randomString) {
		currString = randomString;
		fitness = Main.getFitness(Main.getSolution(currString), Main.sol);
	}

	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public GeneticObject combineWith(GeneticObject o) {
		TextGeneticObject t = (TextGeneticObject) o;
		Random rand = new Random();
		String newString = "";
		int cutOff = rand.nextInt(currString.length()/2);
		newString = currString.substring(0, cutOff) + t.currString.substring(cutOff, currString.length());
		String totalString = "";
		for (int i = 0; i < currString.length(); i++){
			if (rand.nextFloat() < mutationChance){
				totalString += (char)(rand.nextInt(26) + 'a');
			} else{
				totalString += newString.charAt(i);
			}
			
		}
		return new TextGeneticObject(totalString);
	}
	
	public String toString(){
		return currString;
	}

}
