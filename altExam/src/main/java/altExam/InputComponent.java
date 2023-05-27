package altExam;

import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTextField;

import altExam.CircuitComponent.Value;

public class InputComponent extends CircuitComponent {
	final private static int outputCount = 1;
	private JTextField text;
	InputComponent(CircuitPanel parentComp, int x, int y) {
		super(parentComp, "src/main/resources/pictures/INPUT.png", x, y,50,30);
		this.instantiateOutputNodes(outputCount);
		
		this.text = new JTextField("1");
		this.text.setSize(28,28);
		this.text.setLocation(2,2);
		
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
		if(this.getText().charAt(0) == '1') {
			this.setValue(Value.TRUE);
		}else if(this.getText().charAt(0) == '0') {
			this.setValue(Value.FALSE);	
		}else {
			this.setValue(Value.UNDEFINED);
		}		
	}
}
