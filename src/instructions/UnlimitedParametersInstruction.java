package instructions;

import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public interface UnlimitedParametersInstruction {
	
	public abstract double evaluateUnlimitedParameters(SLOGOView view, SLOGOModel model);

}
