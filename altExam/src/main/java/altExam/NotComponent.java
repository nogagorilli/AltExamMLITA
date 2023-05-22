package altExam;

import java.awt.Component;
import java.util.ArrayList;

import altExam.CircuitComponent.Value;

public class NotComponent extends CircuitComponent{
	final private static int inputCount = 1;
	final private static int outputCount = 1;
	NotComponent(Component parentComp, int x, int y) {
		super(parentComp, "src/main/resources/pictures/NOT.png", x, y,50,30);
		this.instantiateInputNodes(inputCount);
		this.instantiateOutputNodes(outputCount);
	}
	@Override
	public void evaluate(){
		Boolean result;
		try {
			ArrayList<Boolean> inputs = this.getInputs();
			result = !inputs.get(0);
			if(result) {
				this.setValue(Value.TRUE);
			}else {
				this.setValue(Value.FALSE);
			}
		}catch(ImpossibleToEvaluateException ex) {
			this.setValue(Value.UNDEFINED);
		}		
	}
}