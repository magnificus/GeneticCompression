package image2;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.util.Random;

/**
 * This class contains the shapes used by the algorithm, currently only polygons
 * are used.
 * 
 * @author Tobbe
 *
 */
public abstract class EvolShape {
	public int[] strength;

	public static final int POLYGON_MAX_POINTS = 10;
	public static final int POLYGON_MIN_POINTS = 3;

	public static final int MUTATION_COLOR_MAX_DISTANCE = 50;
	public static final int MUTATION_DISTANCE_MAX_DISTANCE = 400;

	public static final float MUTATION_COLOR_CHANCE = 0.01f;
	public static final float MUTATION_POLYGON_REMOVE_POINT_CHANCE = 0.03f;
	public static final float MUTATION_POLYGON_ADD_POINT_CHANCE = 0.03f;
	public static final float MUTATION_POLYGON_MOVE_CHANCE = 0.01f;

	public abstract void apply(int[][][] matrix);

	public EvolShape(int[] strength) {
		this.strength = strength;
	}

	// public static class EvolCircle extends EvolShape {
	// public Point center;
	// public int radius;
	//
	// public EvolCircle(Point center, int radius, int[] strength) {
	// super(strength);
	// this.center = center;
	// this.radius = radius;
	// }
	//
	// @Override
	// public void apply(int[][][] matrix) {
	// int rad2 = (int) Math.pow(radius, 2);
	// for (int x = -radius; x <= radius; x++) {
	// for (int y = -radius; y <= radius; y++) {
	// if (x + center.x > 0 && x + center.x < Main.correctMatrix.length && y +
	// center.y > 0 && y + center.y < Main.correctMatrix[0].length
	// && (Math.pow(x, 2) + Math.pow(y, 2)) < rad2) {
	// matrix[x + center.x][y + center.y] += strength;
	// }
	// }
	// }
	//
	// }
	//
	// @Override
	// public EvolShape duplicate() {
	// return new EvolCircle(new Point(center.x, center.y), radius, strength);
	// }
	//
	// @Override
	// public void mutate(Random r) {
	// if (r.nextFloat() < 0.01f) {
	// center.x = Math.min(Main.correctMatrix.length - 1, Math.max(0, center.x +
	// r.nextInt(20) - 10));
	// }
	// if (r.nextFloat() < 0.01f) {
	// center.y = Math.min(Main.correctMatrix.length - 1, Math.max(0, center.y +
	// r.nextInt(20) - 10));
	// }
	// if (r.nextFloat() < 0.02f) {
	// strength += r.nextInt(50) - 25;
	// }
	// if (r.nextFloat() < 0.01f) {
	// radius += r.nextInt(20) - 10;
	// }
	// if (r.nextFloat() < 0.001f) {
	// int xLen = Main.correctMatrix.length;
	// int yLen = Main.correctMatrix[0].length;
	// center = new Point(r.nextInt(xLen), r.nextInt(yLen));
	// strength = r.nextInt(512) - 256;
	// radius = r.nextInt(500);
	// }
	//
	// }
	//
	// }

	// public static class EvolRectangle extends EvolShape {
	// public Point p1;
	// public Point p2;
	//
	// public EvolRectangle(Point p1, Point p2, int strength) {
	// super(strength);
	// this.p1 = p1;
	// this.p2 = p2;
	// }
	//
	// @Override
	// public void apply(int[][] matrix) {
	// for (int x = p1.x; x < p2.x; x++) {
	// for (int y = p1.y; y < p2.y; y++) {
	// matrix[x][y] += strength;
	// }
	// }
	//
	// }
	//
	// @Override
	// public EvolShape duplicate() {
	// return new EvolRectangle(new Point(p1.x, p1.y), new Point(p2.x, p2.y),
	// strength);
	// }
	//
	// @Override
	// public void mutate(Random r) {
	// if (r.nextFloat() < 0.001f) {
	// int xLen = Main.correctMatrix.length;
	// int yLen = Main.correctMatrix[0].length;
	// p1 = new Point(r.nextInt(xLen), r.nextInt(yLen));
	// p2 = new Point(r.nextInt(xLen - p1.x) + p1.x, r.nextInt(yLen - p1.y) +
	// p1.y);
	// strength = r.nextInt(512) - 256;
	// }
	// if (r.nextFloat() < 0.01f) {
	// p1.x = Math.min(Main.correctMatrix.length - 1, Math.max(0, p1.x +
	// r.nextInt(50) - 25));
	// }
	// if (r.nextFloat() < 0.01f) {
	// p1.y = Math.min(Main.correctMatrix[0].length - 1, Math.max(0, p1.y +
	// r.nextInt(50) - 25));
	// }
	// if (r.nextFloat() < 0.01f) {
	// p2.x = Math.min(Main.correctMatrix.length - 1, Math.max(p1.x, p2.x +
	// r.nextInt(50) - 25));
	// }
	// if (r.nextFloat() < 0.01f) {
	// p2.y = Math.min(Main.correctMatrix[0].length - 1, Math.max(p1.y, p2.y +
	// r.nextInt(50) - 25));
	// }
	// if (r.nextFloat() < 0.01f) {
	// strength += r.nextInt(50) - 25;
	//
	// }
	//
	// }
	//
	// }

	public static class EvolTriangle extends EvolShape {
		public Point p1;
		public Point p2;
		public Point p3;

		public EvolTriangle(int[] strength, Point p1, Point p2, Point p3) {
			super(strength);
			this.p1 = p1;
			this.p2 = p2;
			this.p3 = p3;
		}

		@Override
		public void apply(int[][][] matrix) {
			int minX = Math.min(p3.x, Math.min(p1.x, p2.x));
			int maxX = Math.max(p3.x, Math.max(p1.x, p2.x));

			int minY = Math.min(p3.y, Math.min(p1.y, p2.y));
			int maxY = Math.max(p3.y, Math.max(p1.y, p2.y));

			Point test = new Point(0, 0);
			for (int x = minX; x < maxX; x++) {
				for (int y = minY; y < maxY; y++) {
					test.x = x;
					test.y = y;

					if (PointInTriangle(test, p1, p2, p3)) {
						matrix[x][y][0] += strength[0];
						matrix[x][y][1] += strength[1];
						matrix[x][y][2] += strength[2];
					}
				}
			}

		}

		float sign(Point p1, Point p2, Point p3) {
			return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
		}

		boolean PointInTriangle(Point pt, Point v1, Point v2, Point v3) {
			boolean b1, b2, b3;

			b1 = sign(pt, v1, v2) < 0.0f;
			b2 = sign(pt, v2, v3) < 0.0f;
			b3 = sign(pt, v3, v1) < 0.0f;

			return ((b1 == b2) && (b2 == b3));
		}

		@Override
		public EvolShape duplicate() {
			Point newP1 = new Point(p1.x, p1.y);
			Point newP2 = new Point(p2.x, p2.y);
			Point newP3 = new Point(p3.x, p3.y);
			int[] str = new int[] { strength[0], strength[1], strength[2] };
			return new EvolTriangle(str, newP1, newP2, newP3);
		}

		@Override
		public void mutate(Random r) {
			int xLen = Main.correctMatrix.length;
			int yLen = Main.correctMatrix[0].length;
			if (r.nextFloat() < 0.01f) {
				p1.x = Math.min(xLen - 1, Math.max(0, p1.x + r.nextInt(50) - 25));
			}
			if (r.nextFloat() < 0.01f) {
				p1.y = Math.min(yLen - 1, Math.max(0, p1.y + r.nextInt(50) - 25));
			}
			if (r.nextFloat() < 0.01f) {
				p2.x = Math.min(xLen - 1, Math.max(0, p2.x + r.nextInt(50) - 25));
			}
			if (r.nextFloat() < 0.01f) {
				p2.y = Math.min(yLen - 1, Math.max(0, p2.y + r.nextInt(50) - 25));
			}
			if (r.nextFloat() < 0.01f) {
				p3.x = Math.min(xLen - 1, Math.max(0, p3.x + r.nextInt(50) - 25));
			}
			if (r.nextFloat() < 0.01f) {
				p3.y = Math.min(yLen - 1, Math.max(0, p3.y + r.nextInt(50) - 25));
			}
			if (r.nextFloat() < MUTATION_COLOR_CHANCE) {
				strength[0] += r.nextInt(MUTATION_COLOR_MAX_DISTANCE) - MUTATION_COLOR_MAX_DISTANCE / 2;
			}
			if (r.nextFloat() < MUTATION_COLOR_CHANCE) {
				strength[1] += r.nextInt(MUTATION_COLOR_MAX_DISTANCE) - MUTATION_COLOR_MAX_DISTANCE / 2;
			}
			if (r.nextFloat() < MUTATION_COLOR_CHANCE) {
				strength[2] += r.nextInt(MUTATION_COLOR_MAX_DISTANCE) - MUTATION_COLOR_MAX_DISTANCE / 2;
			}

		}

	}

	public static class EvolPolygon extends EvolShape {
		public Polygon p;

		public EvolPolygon(int[] strength, Polygon p) {
			super(strength);
			this.p = p;
		}

		@Override
		public void apply(int[][][] matrix) {
			Rectangle bounds = p.getBounds();
			for (int x = bounds.x; x < bounds.width + bounds.x; x++) {
				for (int y = bounds.y; y < bounds.height + bounds.y; y++) {
					if (p.contains(x, y)) {
						matrix[x][y][0] += strength[0];
						matrix[x][y][1] += strength[1];
						matrix[x][y][2] += strength[2];

					}
				}
			}

		}

		@Override
		public EvolShape duplicate() {
			Polygon nP = new Polygon();
			for (int i = 0; i < p.npoints; i++) {
				nP.addPoint(p.xpoints[i], p.ypoints[i]);
			}
			int[] str = new int[] { strength[0], strength[1], strength[2] };
			return new EvolPolygon(str, nP);
		}

		@Override
		public void mutate(Random r) {

			// mutate color
			if (r.nextFloat() < MUTATION_COLOR_CHANCE) {
				strength[0] += r.nextInt(MUTATION_COLOR_MAX_DISTANCE) - MUTATION_COLOR_MAX_DISTANCE / 2;
			}
			if (r.nextFloat() < MUTATION_COLOR_CHANCE) {
				strength[1] += r.nextInt(MUTATION_COLOR_MAX_DISTANCE) - MUTATION_COLOR_MAX_DISTANCE / 2;
			}
			if (r.nextFloat() < MUTATION_COLOR_CHANCE) {
				strength[2] += r.nextInt(MUTATION_COLOR_MAX_DISTANCE) - MUTATION_COLOR_MAX_DISTANCE / 2;
			}
			int xLen = Main.correctMatrix.length;
			int yLen = Main.correctMatrix[0].length;

			// remove point in polygon chain
			if (r.nextFloat() < MUTATION_POLYGON_REMOVE_POINT_CHANCE && p.npoints > POLYGON_MIN_POINTS) {
				int pos = r.nextInt(p.npoints);
				Polygon newP = new Polygon();

				for (int i = 0; i < p.npoints; i++) {
					if (i != pos) {
						newP.addPoint(p.xpoints[i], p.ypoints[i]);
					}
				}

				p = newP;
			}

			// add point in polygon chain

			if (r.nextFloat() < MUTATION_POLYGON_ADD_POINT_CHANCE && p.npoints < POLYGON_MAX_POINTS) {
				int pos = r.nextInt(p.npoints - 1) + 1;
				Polygon newP = new Polygon();

				for (int i = 0; i < p.npoints; i++) {
					if (i == pos) {
						newP.addPoint(
								Math.min(xLen, Math.max(0, p.xpoints[i - 1] + r.nextInt(MUTATION_DISTANCE_MAX_DISTANCE) - MUTATION_DISTANCE_MAX_DISTANCE / 2)),
								Math.min(yLen, Math.max(0, p.ypoints[i - 1] + r.nextInt(MUTATION_DISTANCE_MAX_DISTANCE) - MUTATION_DISTANCE_MAX_DISTANCE / 2)));

					}
					newP.addPoint(p.xpoints[i], p.ypoints[i]);

				}
				p = newP;

			}

			// potentially move polygon slightly
			for (int i = 0; i < p.npoints; i++) {
				if (r.nextFloat() < MUTATION_POLYGON_MOVE_CHANCE) {
					p.xpoints[i] = Math.min(xLen, Math.max(0, p.xpoints[i] + r.nextInt(MUTATION_COLOR_MAX_DISTANCE) - MUTATION_COLOR_MAX_DISTANCE / 2));
				}
				if (r.nextFloat() < MUTATION_POLYGON_MOVE_CHANCE) {
					p.ypoints[i] = Math.min(yLen, Math.max(0, p.ypoints[i] + r.nextInt(MUTATION_COLOR_MAX_DISTANCE) - MUTATION_COLOR_MAX_DISTANCE / 2));

				}
			}
		}

	}

	/**
	 * Returns a new randomized shape, which might later evolve further, for
	 * now, only polygons.
	 * 
	 * @param r
	 * @return
	 */
	public static EvolShape getNewShape(Random r) {
		int type = r.nextInt(1);

		int xLen = Main.correctMatrix.length;
		int yLen = Main.correctMatrix[0].length;
		int[] strength = new int[] { r.nextInt(512) - 255, r.nextInt(512) - 255, r.nextInt(512) - 255 };
		switch (type) {
		case 0: {
			Polygon p = new Polygon();
			int nPoints = r.nextInt(POLYGON_MAX_POINTS - POLYGON_MIN_POINTS) + POLYGON_MIN_POINTS;
			Point prev = new Point(r.nextInt(xLen), r.nextInt(yLen));
			p.addPoint(prev.x, prev.y);

			for (int i = 0; i < nPoints; i++) {
				prev = new Point(Math.min(xLen, Math.max(0, prev.x + r.nextInt(MUTATION_DISTANCE_MAX_DISTANCE) - MUTATION_DISTANCE_MAX_DISTANCE / 2)),
						Math.min(yLen, Math.max(0, prev.y + r.nextInt(MUTATION_DISTANCE_MAX_DISTANCE) - MUTATION_DISTANCE_MAX_DISTANCE / 2)));
				p.addPoint(prev.x, prev.y);
			}
			return new EvolPolygon(strength, p);
		}
		// case 1: {
		// Point p1 = new Point(r.nextInt(xLen), r.nextInt(yLen));
		// Point p2 = new Point(r.nextInt(xLen - p1.x) + p1.x, r.nextInt(yLen -
		// p1.y) + p1.y);
		// return new EvolRectangle(p1, p2, strength);
		// }
		// case 2: {
		// Point p1 = new Point(r.nextInt(xLen), r.nextInt(yLen));
		// int radius = r.nextInt(500);
		// return new EvolCircle(p1, radius, strength);
		//
		// }

		// case 0: {
		// Point p1 = new Point(r.nextInt(xLen), r.nextInt(yLen));
		// Point p2 = new Point(r.nextInt(xLen), r.nextInt(yLen));
		// Point p3 = new Point(r.nextInt(xLen), r.nextInt(yLen));
		//
		// return new EvolTriangle(strength, p1, p2, p3);
		// }
		}
		return null;

	}

	public abstract EvolShape duplicate();

	public abstract void mutate(Random r);
}
