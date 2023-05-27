package altExam;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;


import javax.swing.JPanel;
import javax.swing.JPopupMenu;

/**
 * @author nogagorilli
 * This panel is used for creation of logic circuits\n
 * By pressing RMB on a panel you can choose a type of component to create.
 */
public class CircuitPanel extends JPanel{
	final private CircuitPanel circuitPanel = this;
	private ArrayList<CircuitComponent> circuitComponents = new ArrayList<CircuitComponent>();
	final private CircuitNode[] circuitNodeBuffer = {null, null}; 
	CircuitPanel(int width,int height){
		super();
		this.setBackground(Color.green);
		this.setLayout(null);
		this.setBounds(0, 0, width, height);
		this.addMouseListener(new RightMouseButtonListener());
	}
	
	/**
	 * Used to get a list of all circuit components, placed on a circuit panel
	 * @return
	 */
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
	
	/**
	 * Makes one step of circuit evaluation by simply
	 * evaluating each component once. Should be repeated
	 * at least 10 times in a row to get result somewhat consistently.
	 */
	public void evaluateOnce() {
		for(CircuitComponent comp:this.getCircuitComponents()) {
			comp.evaluate();
		}
	}
	
	/**
	 * Used for adding a circuit component to a circuit panel 
	 * @param comp
	 */
	public void addComponent(CircuitComponent comp) {
		this.getCircuitComponents().add(comp);
		this.add(comp);
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Used to remove component from circuit panel
	 * @param comp
	 */
	public void removeComponent(CircuitComponent comp) {
		ArrayList<CircuitConnection> forDeletion = new ArrayList<CircuitConnection>();
		for(CircuitNode node:comp.outputNodes) {
			for(CircuitConnection con:node.getConnections()){
				System.out.println("aaa");
				forDeletion.add(con);
			}
			comp.remove(node);
		}
		for(CircuitNode node:comp.inputNodes) {
			for(CircuitConnection con:node.getConnections()){
				
				forDeletion.add(con);
				System.out.println("bbb");

			}
			comp.remove(node);
			System.out.println("ccc");
		}
		for(CircuitConnection con:forDeletion) {
			con.removeFromPanel();
		}
		for(CircuitNode node:comp.inputNodes) comp.remove(node);
		
		this.remove(comp);
		this.getCircuitComponents().remove(comp);
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Creates a connection between an 
	 * OUTPUT OF FIRST COMPONENT
	 * and an 
	 * INPUT OF SECOND COMPONENT
	 * @throws WrongParentException 
	 * 
	 * 
	 */
	public void addConnection(CircuitComponent first, int firstNodeNumber,CircuitComponent second, int secondNodeNumber) throws InputOutputCollisionException, WrongParentException{
		if(first.getParent() != this || second.getParent() != this) {
			throw new WrongParentException("One or both components don't belong to circuit panel");
		}
		
		try {
			CircuitNode begin = first.getOutputNodes().get(firstNodeNumber);
			CircuitNode end = second.getInputNodes().get(secondNodeNumber);
			CircuitConnection connection = new CircuitConnection(begin, end);
			if(second.getInputNodes().get(secondNodeNumber).getConnections().size() == 0) {
				begin.getConnections().add(connection);
				end.getConnections().add(connection);
				this.add(connection);
			}else {
				throw new InputOutputCollisionException("Second node already has a connection");
			}
			
		}catch(Exception ex) {
			throw new InputOutputCollisionException("Can't create connection");
		}
		this.revalidate();
		this.repaint();
	}



	public CircuitNode[] getCircuitNodeBuffer() {
		return circuitNodeBuffer;
	}
	
	public void buffer(CircuitNode node) {
		this.getCircuitNodeBuffer()[0] = this.getCircuitNodeBuffer()[1];
		this.getCircuitNodeBuffer()[1] = node;
	}
	public void clearBuffer() {
		this.getCircuitNodeBuffer()[0] = null;
		this.getCircuitNodeBuffer()[1] = null;
	}
}
