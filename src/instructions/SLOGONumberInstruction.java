package instructions;

public class SLOGONumberInstruction extends SLOGOInstruction {
	
	private double value;

	public SLOGONumberInstruction(double value) {
		this.left = null;
		this.right = null;
		this.value = value;
	}

	@Override
	public double evaluate() {
		return value;
	}

}
