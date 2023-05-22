package altExam;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

abstract class CircuitComponent extends JPanel{
	final private CircuitComponent circuitComponent = this;
	private ImageIcon image;
	private int HEIGHT;
	private int WIDTH;
	private Point prevPoint;
	private Component parentComponent;
	private Value value;
	protected ArrayList<CircuitNode> inputNodes;
	protected ArrayList<CircuitNode> outputNodes;
	
	CircuitComponent(Component parentComp,String imageName, int x, int y,int width,int height){
		
		this.image = new ImageIcon(new ImageIcon(imageName).getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH));
		this.setLayout(null);
		
		this.setWIDTH(image.getIconWidth());
		this.setHEIGHT(image.getIconHeight());
		this.setSize(new Dimension(this.WIDTH,this.HEIGHT));
		this.setLocation(new Point(x,y));
		this.setValue(Value.UNDEFINED);
		this.parentComponent = parentComp;
		this.inputNodes = new ArrayList<CircuitNode>();
		this.outputNodes = new ArrayList<CircuitNode>();
		
		ClickListener clickListener = new ClickListener();
		DragListener dragListener = new DragListener();
		this.addMouseListener(clickListener);
		this.addMouseListener(new RMBClickListener());
		this.addMouseMotionListener(dragListener);
		this.setBackground(new Color(25,50,250,10));
		this.setVisible(true);
	}
	

	private void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

	private void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		image.paintIcon(this, g, 0, 0);
	}
	
	private void translate(int dx, int dy) {
		int parentWidth = parentComponent.getWidth();
		int parentHeight = parentComponent.getHeight();
		int margin = 10;
		
		if(this.getX()+dx < parentWidth-margin && 
				this.getY()+dy < parentHeight-margin && 
				this.getY()+this.getHeight()+dy > 0+margin && 
				this.getX()+this.getWidth()+dx > 0+margin){		
			this.setLocation(this.getX()+dx,this.getY()+dy);
		}
		
	}
	
	public Value getValue() {
		return value;
	}

	public void setValue(Value value) {
		this.value = value;
	}
	
	public ArrayList<CircuitNode> getInputNodes(){
		return this.inputNodes;
	}
	
	public ArrayList<CircuitNode> getOutputNodes(){
		return this.outputNodes;
	}
	
	
	private class ClickListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			prevPoint = e.getPoint();
		}
	}
	
	private class RMBClickListener extends MouseAdapter{
		public void mousePressed(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON3) {
				circuitComponent.removeFromPanel();
			}
		}
	}
	public void removeFromPanel() {
		ArrayList<CircuitConnection> forDeletion = new ArrayList<CircuitConnection>();
		for(CircuitNode node:this.outputNodes) {
			for(CircuitConnection con:node.getConnections()){
				System.out.println("aaa");
				forDeletion.add(con);
			}
			this.remove(node);
		}
		for(CircuitNode node:this.inputNodes) {
			for(CircuitConnection con:node.getConnections()){
				
				forDeletion.add(con);
				System.out.println("bbb");

			}
			this.remove(node);
			System.out.println("ccc");
		}
		for(CircuitConnection con:forDeletion) {
			con.removeFromPanel();
		}
		for(CircuitNode node:this.inputNodes) this.remove(node);
		CircuitPanel tempParent = (CircuitPanel) this.getParent();
		tempParent.remove(this);
		tempParent.getCircuitComponents().remove(this);
		tempParent.repaint();
	}
	
	private class DragListener extends MouseMotionAdapter{
		public void mouseDragged(MouseEvent e) {
			Point currentPoint = e.getPoint();
			int dx = (int)(currentPoint.getX()-prevPoint.getX());
			int dy =  (int)(currentPoint.getY()-prevPoint.getY());

			translate(dx,dy); 
			for(CircuitNode i:inputNodes) {
				for(CircuitConnection con:i.getConnections()){
					con.repaint();
				}
			}
			for(CircuitNode i:outputNodes) {
				for(CircuitConnection con:i.getConnections()){
					con.repaint();
				}
			}
			repaint();
		}
	}
	
	protected void instantiateInputNodes(int nodeCount) {
		float interval = this.getHeight()/(nodeCount+1);
		for(int i = 1;i<=nodeCount;i++) {
			CircuitNode node = new CircuitNode();
			node.setLocation(0, (int) (interval*i-node.getHeight()*0.5) );
			node.setMode(CircuitNode.Mode.INPUT);
			this.inputNodes.add(node);
			this.add(node);
		}
		this.repaint();
	}
	
	protected void instantiateOutputNodes(int nodeCount) {
		float interval = this.getHeight()/(nodeCount+1);
		for(int i = 1;i<=nodeCount;i++) {
			CircuitNode node = new CircuitNode();
			node.setLocation((this.getWidth()-node.getWidth()), (int) (interval*i-node.getHeight()*0.5) );
			node.setMode(CircuitNode.Mode.OUTPUT);
			this.outputNodes.add(node);
			this.add(node);
		}
		this.repaint();
	}
	
	enum Value{
		FALSE{
			public int toInteger() {
				return 0;
			}
		},
		TRUE{
			public int toInteger() {
				return 1;
			}
		},
		UNDEFINED{
			public int toInteger() {
				return -1;
			}
		},
	}
	public abstract void evaluate();
	
	protected ArrayList<Boolean> getInputs() throws ImpossibleToEvaluateException{
		ArrayList<Boolean> ret = new ArrayList<Boolean>();
		for (CircuitNode node:this.inputNodes) {
			try {
				if(((CircuitComponent)(node.getConnections().get(0).getBeginNode().getParent())).getValue() == CircuitComponent.Value.FALSE) {
					ret.add(false);
				}else if(((CircuitComponent)(node.getConnections().get(0).getBeginNode().getParent())).getValue() == CircuitComponent.Value.TRUE) {
					ret.add(true);
				}else {
					throw new ImpossibleToEvaluateException("Some inputs are undefined");
				}
			}catch(IndexOutOfBoundsException ex) {
				throw new ImpossibleToEvaluateException("Some inputs are not connected");
			}
		}
		
		return ret;
	}
		

}


