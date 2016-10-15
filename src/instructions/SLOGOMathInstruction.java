package instructions;

import java.lang.reflect.Method;
import java.lang.reflect.Type;

public abstract class SLOGOMathInstruction extends SLOGOInstruction {

	@Override
	public double evaluate(){
		
		if (left != null && right != null)
		
		try {
			Method operation = MathOperations.class.getMethod(command, double.class, double.class);
		} catch (NoSuchMethodException | SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return 0;
	}

}
