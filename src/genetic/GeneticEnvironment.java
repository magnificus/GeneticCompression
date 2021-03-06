package genetic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public abstract class GeneticEnvironment {
	
	// on average 1/cullingconstant entities are removed each generation
	public static final int cullingConstant = 5;


	protected List<GeneticObject> objects;
	
	public static final boolean useMultiThread = true;
	
	public double SimulationTick(){
		Collections.sort(objects, new GeneticComp());
		List<GeneticObject> winners = new ArrayList<GeneticObject>();
		List<GeneticObject> losers = Collections.synchronizedList(new ArrayList<GeneticObject>());
		
		Random rand = new Random();
		
		double average = 0;
		// the entry with best fitness
		double best = objects.get(0).getFitness();
		

		
		winners.add(objects.get(0));
		
		// decide, with a random factor, which entries lives and which dies, but where worse entries have a higher chance to die
		average += objects.get(0).getFitness();
		for (int i = 1; i < objects.size(); i++){
			average += objects.get(i).getFitness();
			if (rand.nextFloat() * objects.size() * cullingConstant > i){
				winners.add(objects.get(i));
			} else{
				losers.add(objects.get(i));
			}
		}
		
		average /= objects.size();
		AtomicInteger currentIndex = new AtomicInteger(-1);
		Runnable toRun = new Runnable(){
			@Override
			public void run() {
				int recieved = currentIndex.incrementAndGet();
				while (recieved < losers.size()){
					GeneticObject p1 = winners.get(rand.nextInt(winners.size()));
					GeneticObject p2 = winners.get(rand.nextInt(winners.size()));
					objects.remove(losers.get(recieved));
					objects.add(p1.combineWith(p2));
					recieved = currentIndex.incrementAndGet();
				}
				
			}
		};
		
		int availableProc = useMultiThread ? Runtime.getRuntime().availableProcessors() : 1;
		Thread[] threads = new Thread[availableProc];
		
		for (int i = 0; i < availableProc; i++){
			threads[i] = new Thread(toRun);
			threads[i].start();
		}
		
		for (Thread t : threads) {
			try {
				t.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.print("Average: " + average + " Best: " + best + " " + objects.get(0));
		return best;
	}
}
