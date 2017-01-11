package image2;

import java.awt.Point;
import java.util.Random;

public abstract class Shape {
	public int strength;
	public abstract void apply(int[][] matrix);

	public Shape(int strength){
		this.strength = strength;
	}
	
	public static class Circle extends Shape{
		public Circle(int strength) {
			super(strength);
			// TODO Auto-generated constructor stub
		}

		public Point center;
		public int radius;
		
		@Override
		public void apply(int[][] matrix) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Shape duplicate() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void mutate(Random r) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class Rectangle extends Shape{
		public Point p1;
		public Point p2;
		public Rectangle(Point p1, Point p2, int strength) {
			super(strength);
			this.p1 = p1;
			this.p2 = p2;
		}
		@Override
		public void apply(int[][] matrix) {
			for (int x = p1.x; x < p2.x; x++){
				for (int y = p1.y; y < p2.y; y++){
					matrix[x][y] += strength;
				}
			}
			
		}
		@Override
		public Shape duplicate() {
			return new Rectangle(new Point(p1.x, p1.y), new Point(p2.x, p2.y), strength);
		}
		@Override
		public void mutate(Random r) {
			if (r.nextFloat() < 0.001f){
				int xLen = Main.correctMatrix.length;
				int yLen = Main.correctMatrix[0].length;
				p1 = new Point(r.nextInt(xLen), r.nextInt(yLen));
				p2 = new Point(r.nextInt(xLen - p1.x) + p1.x, r.nextInt(yLen - p1.y) + p1.y);
				strength = r.nextInt(100);
			}
			if (r.nextFloat() < 0.001f){
				p1.x = Math.min(Main.correctMatrix.length-1, Math.max(0, p1.x + r.nextInt(10) - 5));
			}
			if (r.nextFloat() < 0.01f){
				p1.y = Math.min(Main.correctMatrix[0].length-1, Math.max(0, p1.y + r.nextInt(10) - 5));
			}
			if (r.nextFloat() < 0.01f){
				p2.x = Math.min(p1.x, Math.max(0, p2.x + r.nextInt(10) - 5));
			}
			if (r.nextFloat() < 0.01f){
				p2.y = Math.min(p1.y, Math.max(0, p2.y + r.nextInt(10) - 5));
			}
			if (r.nextFloat() < 0.03f){
				strength = Math.max(0, strength + r.nextInt(10) - 5);
				
			}
			
		}
		
	}

	public abstract Shape duplicate();

	public abstract void mutate(Random r);
}

