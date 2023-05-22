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
				System.out.println("Connection creation");
				CircuitPanel circuitPanel = (CircuitPanel) circuitNode.getParent().getParent();
				CircuitConnection bufferConnection = circuitPanel.getBufferConnection();
				if(bufferConnection == null) {
					circuitPanel.setBufferConnection(new CircuitConnection(null,null));
					bufferConnection = circuitPanel.getBufferConnection();
				}
				if(bufferConnection.getBeginNode() == null && circuitNode.getMode() == Mode.OUTPUT) {
					bufferConnection.setBeginNode(circuitNode);
				}else if(circuitNode.getMode() == Mode.INPUT){
					bufferConnection.setEndNode(circuitNode);
					if(bufferConnection.getBeginNode() != bufferConnection.getEndNode()&&
							bufferConnection.getBeginNode() != null &&
							bufferConnection.getEndNode() != null &&
							bufferConnection.getEndNode().getConnections().size() == 0&&
							
							bufferConnection.getBeginNode().getParent() != bufferConnection.getEndNode().getParent()) {
						circuitPanel.add(bufferConnection);
						bufferConnection.getBeginNode().getConnections().add(bufferConnection);
						bufferConnection.getEndNode().getConnections().add(bufferConnection);
						bufferConnection.repaint();
						circuitPanel.setBufferConnection(new CircuitConnection(null,null));
					}else {
						circuitPanel.setBufferConnection(new CircuitConnection(null,null));
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
