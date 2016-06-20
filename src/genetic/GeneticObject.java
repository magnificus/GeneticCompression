package genetic;

public abstract class GeneticObject {
	
	public abstract double getFitness();
	public abstract GeneticObject combineWith(GeneticObject o);
	

}
