package altExam;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class CircuitPanel extends JPanel{
	final private CircuitPanel circuitPanel = this;
	private ArrayList<CircuitComponent> circuitComponents = new ArrayList<CircuitComponent>();
	private CircuitConnection bufferConnection;
	CircuitPanel(int width,int height){
		super();
		this.setBackground(Color.green);
		this.setLayout(null);
		this.setBounds(0, 0, width, height);
		this.addMouseListener(new RightMouseButtonListener());
		this.setBufferConnection(null);
		
		
	}
	
	public CircuitConnection getBufferConnection() {
		return bufferConnection;
	}

	public void setBufferConnection(CircuitConnection bufferConnection) {
		this.bufferConnection = bufferConnection;
	}

	public ArrayList<CircuitComponent> getCircuitComponents() {
		return circuitComponents;
	}

	public void setCircuitComponents(ArrayList<CircuitComponent> circuitComponents) {
		this.circuitComponents = circuitComponents;
	}

	private class RightMouseButtonListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				System.out.println(e.getX());
				JPopupMenu popup = new CircuitPopupMenu(circuitPanel,e.getX(),e.getY());	
				popup.show(circuitPanel,e.getX(),e.getY());
				circuitPanel.getParent().repaint();
			}
			
		}
	}
	public void evaluateOnce() {
		for(CircuitComponent comp:this.getCircuitComponents()) {
			comp.evaluate();
		}
	}
}
