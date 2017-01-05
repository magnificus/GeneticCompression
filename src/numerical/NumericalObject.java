package numerical;

import genetic.GeneticObject;

import java.util.ArrayList;
import java.util.Random;

public class NumericalObject extends GeneticObject{

	public ArrayList<Operator> operators;
	public NumericalEnvironment env;
	
	public double fitness;
	
	public NumericalObject(NumericalEnvironment env){
		this.env = env;
		operators = new ArrayList<Operator>();
	}
	@Override
	public double getFitness() {
		//calcFitness();
		return fitness;
	}
	
	public void calcFitness(){
		double[] correct = env.correct;
		double[] input = env.input;
		
		double tot = 0;
		for (int i = 0; i < input.length; i++){
			tot += Math.pow(calculate(input[i]) - correct[i], 2);
		}
		fitness =  tot + operators.size()*100;
	}

	private double calculate(double d) {
		double tot = d;
		for (Operator o : operators){
			tot = o.operate(tot, d);
		}
		return tot;
	}
	@Override
	public GeneticObject combineWith(GeneticObject o) {
		NumericalObject n = new NumericalObject(env);
		for (Operator op : operators){
			n.operators.add(op.duplicate());
		}
		n.mutate();
		n.calcFitness();
		return n;
	}
	private void mutate() {
		Random r = new Random();
		
		
		for (int i = 0; i < 5; i++){
			float seed = r.nextFloat();
			
			if (seed < 0.1f){
				if (operators.size() > 0){
					operators.remove(r.nextInt(operators.size()));
				}
			} else if (seed < 0.2f){
				int res = r.nextInt(3);
				
				Operator newO = null;
				
				switch(res){
				case 0: newO = new Operator.AddOne(r.nextDouble()*100 - 50.0); break;
				//case 1: newO = new Operator.AddTwo(); break;
				case 1: newO = new Operator.MulOne(r.nextDouble() - 0.5); break;
				case 2: newO = new Operator.ModOne(r.nextInt(100) + 1); break;
				//case 3: newO = new Operator.MulTwo(); break;
				}
				if (operators.isEmpty()){
					operators.add(newO);
				} else{
					
					int loc = r.nextInt(operators.size());
					
					boolean set = false;
					if (loc - 1 > 0){
						if (operators.get(loc - 1).getClass().isInstance(newO)){
							operators.get(loc - 1).combine(newO);
							set = true;
						}
					}
					if (!set && loc + 1 < operators.size() -1){
						if (operators.get(loc + 1).getClass().isInstance(newO)){
							operators.get(loc + 1).combine(newO);
							set = true;
						}
					}
					if (!set){
						operators.add(loc, newO);
					}

				}
			} 
			else if (seed < 0.4f){
				if (operators.size() > 0){
					operators.get(r.nextInt(operators.size())).mutate(r.nextDouble()*0.4 - 0.2);
				}
			}
		}

		
	}
	
	public String toString(){
		String tot = "";
		for (Operator o : operators){
			tot += " " + o;
		}
		return tot;
		
	}

}
