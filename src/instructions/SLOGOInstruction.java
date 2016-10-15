package instructions;

public abstract class SLOGOInstruction {
	
	protected SLOGOInstruction left, right;
	protected String command;
	
	public static final String DEFAULT_COMMAND = "DO NOTHING";

	public SLOGOInstruction(){
		super();
		this.left = null;
		this.right = null;
		this.command = DEFAULT_COMMAND;
	}
	
	public SLOGOInstruction(SLOGOInstruction left, SLOGOInstruction right, String command) {
		super();
		this.left = left;
		this.right = right;
		this.command = command;
	}
		
	public abstract double evaluate();

}
