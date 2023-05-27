package altExam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class CircuitNode extends JPanel{
	private ImageIcon image;
	private ArrayList<CircuitConnection> connections;
	private Mode mode;
	final private CircuitNode circuitNode = this;

	CircuitNode(){
		this.setLayout(new BorderLayout());
		this.image = new ImageIcon(new ImageIcon("src/main/resources/pictures/node.png").getImage().getScaledInstance(7,7, Image.SCALE_REPLICATE));
		int a = 0;
		JLabel label = new JLabel(image,JLabel.LEFT);
		
		label.setBackground(Color.red);
		this.setSize(this.image.getIconWidth(),this.image.getIconHeight());
		this.add(label);
		
		this.setConnections(new ArrayList<CircuitConnection>());
		this.setMode(Mode.UNDEFINED);
		
		this.addMouseListener(new LMBClickListener());
		this.addMouseListener(new RMBClickListener());

		repaint();
		
	}

	public ArrayList<CircuitConnection> getConnections() {
		return connections;
	}

	public void setConnections(ArrayList<CircuitConnection> connections) {
		this.connections = connections;
	}
	
	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	

	private class LMBClickListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				
				CircuitPanel circuitPanel = (CircuitPanel) circuitNode.getParent().getParent();
				circuitPanel.buffer(circuitNode);
				if(circuitPanel.getCircuitNodeBuffer()[0] != null && circuitPanel.getCircuitNodeBuffer()[1] != null ) {
					
					if((circuitPanel.getCircuitNodeBuffer()[0].getMode() == CircuitNode.Mode.INPUT && circuitPanel.getCircuitNodeBuffer()[1].getMode() == CircuitNode.Mode.OUTPUT)|
							(circuitPanel.getCircuitNodeBuffer()[0].getMode() == CircuitNode.Mode.OUTPUT && circuitPanel.getCircuitNodeBuffer()[1].getMode() == CircuitNode.Mode.INPUT)) {
						System.out.println("Connection creation");
						CircuitNode begin = circuitPanel.getCircuitNodeBuffer()[0];
						CircuitNode end = circuitPanel.getCircuitNodeBuffer()[1];
						if(begin.getMode() == CircuitNode.Mode.INPUT) {
							CircuitNode temp = end;
							end = begin;
							begin = temp;
						}
						CircuitComponent beginComp = (CircuitComponent) begin.getParent();
						CircuitComponent endComp = (CircuitComponent) end.getParent();
						int beginNumber = beginComp.getOutputNodes().indexOf(begin);
						int endNumber = endComp.getInputNodes().indexOf(end);
						try {
							circuitPanel.addConnection(beginComp, beginNumber, endComp, endNumber);
						} catch (InputOutputCollisionException e1) {
							e1.printStackTrace();
						}catch (WrongParentException e2) {
							e2.printStackTrace();
						}
						circuitPanel.clearBuffer();
					}else {
						circuitPanel.clearBuffer();
					}
				}
			}
				
		}
	}
				
	
	private class RMBClickListener extends MouseAdapter{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				ArrayList<CircuitConnection> forDeletion = new ArrayList<CircuitConnection>();
				System.out.println(circuitNode.getConnections().size());
				for(CircuitConnection con:circuitNode.getConnections()) {
					//System.out.println("Connection deletion");
					forDeletion.add(con);
				}
				for(CircuitConnection con: forDeletion) {
					con.removeFromPanel();
				}
				System.out.println(circuitNode.getConnections().size());
				//circuitNode.connections.clear();
			}
		}
	}
	
	enum Mode{
		INPUT,
		OUTPUT,
		UNDEFINED
	}
	

}
