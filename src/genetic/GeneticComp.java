package genetic;

import java.util.Comparator;

public class GeneticComp implements Comparator<GeneticObject>{

	@Override
	public int compare(GeneticObject o1, GeneticObject o2) {
		if (o1.getFitness() < o2.getFitness()){
			return -1;
		}
		else if (o1.getFitness() == o2.getFitness()){
			return 0;
		}
		else {
			return 1;
		}
	}


}
