package instructions;

import java.util.ArrayList;
import java.util.List;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOView;

public class AskWith extends MultipleTurtlesCommand {

	@Override
	public int getNumRequiredParameters() {
		return 2;
	}

	@Override
	public double evaluate(SLOGOView view, SLOGOModel model) {
		List<SLOGOModel> models = view.getController().getModels();
		List<Instruction> instructions = new ArrayList<Instruction>();
		Instruction ifCondition = new If();
		ifCondition.setParameters(this.parameters);
		instructions.add(ifCondition);
		double toReturn = 0;
		for(int i = 0; i < models.size(); i++){
			List<Integer> toAsk = new ArrayList<Integer>();
			toAsk.add(i);
			toReturn = view.getController().ask(toAsk, instructions);
		}
		return toReturn;
	}

}
