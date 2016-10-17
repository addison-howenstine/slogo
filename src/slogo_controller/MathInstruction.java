package slogo_controller;

import slogo_model.SLOGOModel;
import slogo_view.SLOGOViewExternal;

public class MathInstruction {
	
	MathOperations operator;

	public MathInstruction() {
		operator = new MathOperations();
	}
	
	protected double sum(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.sum((double) left, (double) right);
	}
	
	protected double difference(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.difference((double) left, (double) right);
	}
	
	protected double product(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.product((double) left, (double) right);
	}
	
	protected double quotient(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.quotient((double) left, (double) right);
	}
	
	protected double remainder(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.remainder((double) left, (double) right);
	}
	
	protected double minus(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.minus((double) right);
	}
	
	protected double random(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.random((double) right);
	}

	protected double sine(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.sine((double) right);
	}

	protected double cosine(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.cosine((double) right);
	}

	protected double tangent(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.tangent((double) right);
	}

	protected double arcTangent(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.arcTangent((double) right);
	}

	protected double naturalLog(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.naturalLog((double) right);
	}
	
	protected double power(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.power((double) left, (double) right);
	}

	protected double pi(SLOGOViewExternal view, SLOGOModel model, Object left, Object right){
		return operator.pi();
	}
}
