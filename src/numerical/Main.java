package numerical;

import text.Solution;
import text.TextCompEnvironment;

public class Main {
	public static Solution sol;
	public static int length;
	public static final int maxRuns = 100000;

	// try sum

	public static void main(String[] args) {
		NumericalEnvironment env = new NumericalEnvironment();
		
		for (int i = 0; i < maxRuns; i++){
			execute(env);
			
		}


	}

	private static void execute(NumericalEnvironment e) {
		long pre = System.currentTimeMillis();

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
				for (int i = 0; i < maxRuns; i++) {
					e.SimulationTick();
					calcPrintTime(pre, i, maxRuns);

				}
//
//			}
//
//		}).start();
	}



	private static void calcPrintTime(long pre, int i, int max) {
		long passed = System.currentTimeMillis() - pre;
		double pct = (double) i / max;

		long remaining = (long) ((1 - pct) * (passed / pct));

		long second = (remaining / 1000) % 60;
		long minute = (remaining / (1000 * 60)) % 60;
		long hour = (remaining / (1000 * 60 * 60)) % 24;

		String time = String.format("%02d:%02d:%02d", hour, minute, second);

		System.out.println("\t" + i + " of " + max + " Remaining: " + time);

	}

}
