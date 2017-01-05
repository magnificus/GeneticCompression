package numerical;

import genetic.GeneticObject;

import java.util.Random;

import numerical.Operator.Top;

public class NumericalObject extends GeneticObject{

	//public ArrayList<Operator> operators;
	
	public Top top;
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
		double[] correct = env.correct;
		double[] input = env.input;
		
		double tot = 0;
		for (int i = 1; i < input.length; i++){
			tot += Math.pow(((top.operate(input[i])  - correct[i]) / correct[i]), 2);
		}
		fitness =  tot + top.getSize() * 0.1;
	}

	@Override
	public GeneticObject combineWith(GeneticObject o) {
		NumericalObject other = (NumericalObject) o;
		NumericalObject n = new NumericalObject(env);
		n.top = (Top) top.duplicate(null);
		if (other.top.next.left != null)
			n.top.next.left = other.top.next.left.duplicate(n.top.next);
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
