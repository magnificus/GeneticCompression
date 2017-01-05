package numerical;

public abstract class Operator {
	
	public abstract double operate(double a, double b); 
	public abstract void mutate(double nextDouble);
	public abstract Operator duplicate();
	
	public static class AddOne extends Operator{
		public double val;
		
		public AddOne(double val){
			this.val = val;
		}
		
		@Override
		public double operate(double a, double b) {
			return a + val;
		}
		
		public String toString(){
			return "AddOne: " + val;
		}

		@Override
		public void mutate(double nextDouble) {
			val += nextDouble;
			
		}

		@Override
		public Operator duplicate() {
			return new AddOne(val);
		}

		@Override
		public void combine(Operator newO) {
			val += ((AddOne) newO).val;
			
		}
		
	}
	
	public static class AddTwo extends Operator{

		@Override
		public double operate(double a, double b) {
			return a + b;
		}
		
		public String toString(){
			return "AddTwo";
		}

		@Override
		public void mutate(double nextDouble) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Operator duplicate() {
			return new AddTwo();
		}

		@Override
		public void combine(Operator newO) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class MulOne extends Operator{
		public double val;
		public MulOne(double val){
			this.val = val;
		}
		
		@Override
		public double operate(double a, double b) {
			return a * val;
		}
		
		public String toString(){
			return "MulOne: " + val;
		}

		@Override
		public void mutate(double nextDouble) {
			val += nextDouble;
			
		}

		@Override
		public Operator duplicate() {
			return new MulOne(val);
		}

		@Override
		public void combine(Operator newO) {
			val *= ((MulOne) newO).val;
			
		}
		
	}
	
	public static class MulTwo extends Operator{
		
		@Override
		public double operate(double a, double b) {
			return a * b;
		}
		
		public String toString(){
			return "MulTwo";
		}

		@Override
		public void mutate(double nextDouble) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Operator duplicate() {
			return new MulTwo();
		}

		@Override
		public void combine(Operator newO) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public static class ModOne extends Operator{
		public int val;
		public ModOne(int val){
			this.val = val;
		}
		
		@Override
		public double operate(double a, double b) {
			return a % val;
		}
		
		public String toString(){
			return "ModOne: " + val;
		}

		@Override
		public void mutate(double nextDouble) {
			val += (int) nextDouble * 10;
		}

		@Override
		public Operator duplicate() {
			return new ModOne(val);
		}

		@Override
		public void combine(Operator newO) {
			
		}
		
	}

	public abstract void combine(Operator newO);



//	public static class Shift extends Operator{
//		public int val;
//		public Shift(int val){
//			this.val = val;
//		}
//		
//		@Override
//		public double operate(double a, double b) {
//			return a >> val;
//		}
//		
//	}


}

