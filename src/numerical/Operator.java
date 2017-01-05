package numerical;

import java.util.Random;

public abstract class Operator {
	public Operator left;
	public Operator right;
	public Operator parent;

	public abstract double operate(double input);

	public void attachChild(Operator prevChild, Operator newChild) {
		if (left == prevChild) {
			left = newChild;
		} else {
			right = newChild;
		}
	}
	
	public Operator(){
		
	}

	public Operator(Operator parent) {
		this.parent = parent;
	}

	public Operator duplicate(Operator parent) {
		Operator op = null;
		try {
			op = this.getClass().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if (left != null) {
			op.left = left.duplicate(op);
		}
		if (right != null) {
			op.right = right.duplicate(op);
		}
		
		op.parent = parent;
		return op;

	}

	public static class Top extends Operator {
		public Operator next;

		public Top() {
			//super(null);
			next = new Value(0, true, this);

		}
		
		@Override
		public Operator duplicate(Operator parent){
			Top t = new Top();
			t.next = next.duplicate(t);
//			System.out.prdoubleln("duplicated " + this + " doubleo: " + t);
			return t;
			
		}

		@Override
		public double operate(double input) {
			return next.operate(input);
		}
		@Override
		public void attachChild(Operator prevChild, Operator newChild) {
			next = newChild;
		}
		@Override
		public double getSize(){
			return next.getSize();
		}
		@Override
		public void mutate(Random r){
			next.mutate(r);
		}
		@Override
		public String toString(){
			return next.toString();
		}
		@Override
		public void normalize(){
			next.normalize();
		}
	}

	public static class Value extends Operator {
		public double value;
		public boolean useInput;

		public Value(double value, boolean useInput, Operator parent) {
			super(parent);
			this.value = value;
			this.useInput = useInput;
		}
		
		public Value(){
			
		}

		@Override
		public double operate(double in) {
			return useInput ? in : value;
		}

		@Override
		public double getSize() {
			return 0;
		}

		@Override
		public String toString() {
			return useInput ? "input " : String.valueOf(value) + " ";
		}
		
		@Override
		public Operator duplicate(Operator parent){
			Value newV = (Value) super.duplicate(parent);
			newV.value = value;
			newV.useInput = useInput;
			return newV;
			
		}
		
		@Override
		public void normalize(){
			
		}

		@Override
		public void mutate(Random r) {
			if (r.nextFloat() < 0.05) {
				int res = r.nextInt(10);
				Operator newO = null;

				switch (res) {
				case 0:
					newO = new Add(parent);
					break;
				case 1:
					newO = new Mul(parent);
					break;
				case 2:
					newO = new Div(parent);
					break;
				case 3:
					newO = new ShiftL(parent);
					break;
				case 4:
					newO = new Or(parent);
					break;
				case 5:
					newO = new Xor(parent);
					break;
				case 6:
					newO = new ShiftR(parent);
					break;
				case 7:
					newO = new And(parent);
					break;
				case 8:
					newO = new Mod(parent);
					break;
				case 9:
					newO = new Cos(parent);
					break;
				
				}
				parent.attachChild(this, newO);
				
				if (r.nextBoolean()){
					newO.left = this;
					newO.right = new Value(r.nextDouble()*100 - 50, r.nextBoolean(), newO);
				} else{
					newO.right = this;
					newO.left = new Value(r.nextDouble()*100 - 50, r.nextBoolean(), newO);
				}
			}

			if (r.nextFloat() < 0.01) {
				useInput = !useInput;
				value = r.nextDouble()*100 - 50;
			} else if (!useInput && r.nextFloat() < 0.03) {
				value += r.nextDouble()*50 - 25;
			}

		}

	}

	public static class Add extends Operator {

		public Add(){
			
		}
		public Add(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "(" + left + " + " + right + ") ";
		}

		@Override
		public double operate(double in) {
			return left.operate(in) + right.operate(in);
		}

	}

	public static class Mul extends Operator {
		public Mul(){
			
		}
		public Mul(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "(" + left + " * " + right + ") ";
		}

		@Override
		public double operate(double in) {
			return left.operate(in) * right.operate(in);
		}
	}
	
	public static class Div extends Operator {
		public Div(){
			
		}
		public Div(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "(" + left + " / " + right + ") ";
		}

		@Override
		public double operate(double in) {
			return left.operate(in) / (right.operate(in) != 0 ? right.operate(in) : 1);
		}
	}
	
	public static class ShiftL extends Operator {
		public ShiftL(){
			
		}
		public ShiftL(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "((int)" + left + " << (int)" + right + "(int)) ";
		}

		@Override
		public double operate(double in) {
			return (int) left.operate(in) << (int) right.operate(in);
		}
	}
	
	public static class ShiftR extends Operator {
		public ShiftR(){
			
		}
		public ShiftR(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "((int)" + left + " >> (int)" + right + ") ";
		}

		@Override
		public double operate(double in) {
			return (int) left.operate(in) >> (int) right.operate(in);
		}
	}
	
	public static class Cos extends Operator {
		public Cos(){
			
		}
		public Cos(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "Cos (" + left + ")";
		}

		@Override
		public double operate(double in) {
			return Math.cos(in);
		}
	}
	
	public static class Or extends Operator {
		public Or(){
			
		}
		public Or(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "((int)" + left + " | " + right + "(int)) ";
		}

		@Override
		public double operate(double in) {
			return (int) left.operate(in) | (int) right.operate(in);
		}
	}
	
	public static class Xor extends Operator {
		public Xor(){
			
		}
		public Xor(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "((int)" + left + " ^ " + right + "(int)) ";
		}

		@Override
		public double operate(double in) {
			return (int) left.operate(in) ^ (int) right.operate(in);
		}
	}
	
	public static class And extends Operator {
		public And(){
			
		}
		public And(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "((int)" + left + " & " + right + "(int)) ";
		}

		@Override
		public double operate(double in) {
			return (int) left.operate(in) & (int) right.operate(in);
		}
	}

	public static class Mod extends Operator {
		public Mod(){
			
		}
		public Mod(Operator parent) {
			super(parent);
		}

		public String toString() {
			return "((int)" + left + " % " + right + "(int)) ";
		}

		@Override
		public double operate(double in) {
			return left.operate(in) % (right.operate(in) != 0 ? right.operate(in) : 1);
		}

	}

	public double getSize() {
		return left.getSize() + right.getSize() + 1;
	}

	public void mutate(Random r) {
		if (r.nextFloat() < 0.05f) {
			left = new Value(r.nextDouble()*100 - 50, r.nextBoolean(), this);
			// remove kiddo left
		} else if (r.nextFloat() < 0.05f) {
			right = new Value(r.nextDouble()*100 - 50, r.nextBoolean(), this);
			// remove kiddo right

		}
		if (r.nextFloat() < 0.05f){
			//swap it 
			Operator tempRight = right;
			Operator tempLeft = left;
			
			
		}
		left.mutate(r);
		right.mutate(r);
	}

	public void normalize() {
//		if (left instanceof Value && right instanceof Value){
//			boolean canEliminate = left.v
//		}
//		left.normalize();
//		right.normalize();
		
	}


}
