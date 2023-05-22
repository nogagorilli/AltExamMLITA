package altExam;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTextField;

import altExam.CircuitComponent.Value;

public class OutputComponent extends CircuitComponent {
	final private static int inputCount = 1;
	private JTextField text;
	OutputComponent(Component parentComp, int x, int y) {
		super(parentComp, "src/main/resources/pictures/OUTPUT.png", x, y,50,30);
		this.instantiateInputNodes(inputCount);
		this.text = new JTextField("0");
		this.text.setSize(28,28);
		this.text.setLocation(this.getWidth()-this.text.getWidth()-2,2);
		
		this.add(this.text);
	}
	public String getText() {
		return text.getText();
	}
	public void setText(String text) {
		this.text.setText(text);
	}
	
	@Override
	public void evaluate(){
		Boolean result;
		try {
			ArrayList<Boolean> inputs = this.getInputs();
			result = inputs.get(0);
			if(result) {
				this.setValue(Value.TRUE);
				this.setText("1");
			}else {
				this.setValue(Value.FALSE);
				this.setText("0");
			}
		}catch(ImpossibleToEvaluateException ex) {
			this.setValue(Value.UNDEFINED);
		}		
	}
}
