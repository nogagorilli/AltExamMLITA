package altExam;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class AltExamGUI extends JFrame {
	CircuitPanel inputCircuitPanel;
	CircuitPanel outputCircuitPanel;
	JPanel middlePanel;
	JPanel topLeftPanel;
	JPanel topRightPanel;
	JButton inputEvaluateButton;
	JButton outputEvaluateButton;
	JButton invertButton;
	AltExamGUI(){
		
		int topSize = 100;
		int middleWidth = 100;
		
		this.setSize(new Dimension(1200,700));
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		middlePanel = new AltExamPanel();
		middlePanel.setSize(middleWidth,this.getHeight());
		middlePanel.setLocation(this.getWidth()/2-middlePanel.getWidth()/2,0);
		
		
		topLeftPanel = new AltExamPanel();
		topLeftPanel.setSize((this.getWidth() - middleWidth)/2,topSize);
		topLeftPanel.setLocation(0,0);
		
		
		topRightPanel = new AltExamPanel();
		topRightPanel.setSize((this.getWidth() - middleWidth)/2,topSize);
		topRightPanel.setLocation((this.getWidth() - middleWidth)/2 + middleWidth,0);
		
		
		inputCircuitPanel = new CircuitPanel((this.getWidth() - middleWidth)/2, this.getHeight()-topSize);
		inputCircuitPanel.setBackground(Color.green);
		inputCircuitPanel.setLocation(0, topSize);
		
		outputCircuitPanel = new CircuitPanel((this.getWidth() - middleWidth)/2, this.getHeight()-topSize);
		outputCircuitPanel.setBackground(Color.green);
		outputCircuitPanel.setLocation((this.getWidth() - middleWidth)/2 + middleWidth, topSize);
		
		inputEvaluateButton = new JButton("EVALUATE");
		inputEvaluateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i =0;i<20;i++) {
					inputCircuitPanel.evaluateOnce();
				}	
			}
		});
		topLeftPanel.add(inputEvaluateButton);
		
		outputEvaluateButton = new JButton("EVALUATE");
		outputEvaluateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i =0;i<20;i++) {
					outputCircuitPanel.evaluateOnce();
				}	
			}
		});
		topRightPanel.add(outputEvaluateButton);
		
		
		invertButton = new JButton("INVERT");
		middlePanel.add(invertButton);
		
		this.add(inputCircuitPanel);
		this.add(outputCircuitPanel);
		this.add(topLeftPanel);
		this.add(topRightPanel);
		this.add(middlePanel);
		this.setVisible(true);
	}
	private class AltExamPanel extends JPanel{
		AltExamPanel(){
			super();
			Border border = BorderFactory.createLineBorder(new Color(255, 102, 0, 150), 10);
			this.setBorder(border);
			this.setBackground(new Color(179, 159, 122));
		}
	}
}
