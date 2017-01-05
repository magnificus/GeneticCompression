package numerical;

import genetic.GeneticObject;

import java.util.Random;

import numerical.Operator.Value;

public class NumericalObject extends GeneticObject{

	//public ArrayList<Operator> operators;
	
	public Operator top;
	public NumericalEnvironment env;
	
	public double fitness;
	
	public NumericalObject(NumericalEnvironment env){
		this.env = env;
		top = new Operator.Top();
		calcFitness();
		//operators = new ArrayList<Operator>();
		//left )= 
	}
	@Override
	public double getFitness() {
//		calcFitness();
//		System.out.println(fitness);
		return fitness;
	}
	
	private void calcFitness(){
		int[] correct = env.correct;
		int[] input = env.input;
		
		double tot = 0;
		for (int i = 0; i < input.length; i++){
			tot += Math.pow(top.operate(input[i]) - correct[i], 2);
		}
		fitness =  tot + top.getSize() * 100;
	}

	@Override
	public GeneticObject combineWith(GeneticObject o) {
		NumericalObject n = new NumericalObject(env);
		n.top = top.duplicate(null);
		n.mutate();
		n.calcFitness();
		return n;
	}
	private void mutate() {
		Random r = new Random();	
		top.mutate(r);

		
	}
	
	public String toString(){
		return top.toString();
		
	}
	@Override
	public void normalize() {
		top.normalize();
		
	}

}
