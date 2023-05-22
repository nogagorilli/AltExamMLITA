package altExam;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Line2D;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class CircuitConnection extends JLabel{
	private CircuitNode beginNode;
	private CircuitNode endNode;
	CircuitConnection(CircuitNode begin,CircuitNode end){
		beginNode = begin;
		endNode = end;
		this.setBackground(Color.red);
		this.setSize(5000,5000);
	}
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.black);
	    Line2D lin = new Line2D.Float( (float) (beginNode.getLocationOnScreen().getX()-this.getParent().getX()-beginNode.getWidth()),
				(float) (beginNode.getLocationOnScreen().getY()-this.getParent().getY()-beginNode.getParent().getHeight()),
				(float) (endNode.getLocationOnScreen().getX()-this.getParent().getX()-endNode.getWidth()),
				(float) (endNode.getLocationOnScreen().getY()-this.getParent().getY())-endNode.getParent().getHeight());
	    g2.draw(lin);
	}
	
	public CircuitNode getBeginNode() {
		return this.beginNode;
	}
	public void setBeginNode(CircuitNode beginNode) {
		this.beginNode = beginNode;
	}
	public CircuitNode getEndNode() {
		return this.endNode;
	}
	public void setEndNode(CircuitNode endNode) {
		this.endNode = endNode;
	}
	public void removeFromPanel() {

		
		if(this.getBeginNode().getConnections().remove(this)) {
//			System.out.println("does exist begin");
//			System.out.print(this.getBeginNode().getX());
//			System.out.print(" ");
//			System.out.print(this.getBeginNode().getY());
//			System.out.print("\n");
		}
		System.out.print("Deleted\n");
		if(this.getEndNode().getConnections().remove(this)){
//			System.out.println("does exist end");
//			System.out.print(this.getEndNode().getX());
//			System.out.print(" ");
//			System.out.print(this.getEndNode().getY());
//			System.out.print("\n");
		}
		
		CircuitPanel tempParent = (CircuitPanel) this.getParent();
		this.getParent().remove(this);
		this.setBeginNode(null);
		this.setEndNode(null);
		tempParent.repaint();
	}

}
