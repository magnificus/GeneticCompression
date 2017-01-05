package text;

public class Main {
	public static Solution sol;
	// public static int width;
	// public static int height;
	public static int length;
	public static int checkLen = 2;
	public static final int maxRuns = 100000;

	// try sum

	public static void main(String[] args) {
		String text = "Lorem ipsum ka".toLowerCase().replace(" ", "");

		length = text.length();

		System.out.println(text);
		System.out.println(length);

		sol = getSolution(text);

		TextCompEnvironment t = new TextCompEnvironment();

		decrypt(t);

	}

	private static void decrypt(TextCompEnvironment e) {
		long pre = System.currentTimeMillis();

		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < maxRuns; i++) {
					e.SimulationTick();
					calcPrintTime(pre, i, maxRuns);

				}

			}

		}).start();
	}

	public static Solution getSolution(String text) {
		int[][] res = new int[checkLen][];

		for (int i = 0; i < 2; i++){
			res[i] = new int[10];
		}
		for (int i = 0; i < text.length(); i++) {
			res[0][i % 4] += text.charAt(i);
			res[1][i / 4] += text.charAt(i);
		}

		Solution sol = new Solution(res);

		return sol;
	}

	public static double getFitness(Solution s1, Solution s2) {
		double tot = 0;
		for (int i = 0; i < s1.result.length; i++) {
			for (int j = 0; j < s1.result[i].length; j++){
				tot += Math.abs(s1.result[i][j] - s2.result[i][j]);

			}
		}
		return tot;

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
