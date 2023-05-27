package altExam;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

public class CircuitPopupMenu extends JPopupMenu{
	final private CircuitPopupMenu self = this;
	private CircuitPanel parentPanel;
	private JMenuItem orItem;
	private JMenuItem andItem;
	private JMenuItem notItem;
	private JMenuItem inputItem;
	private JMenuItem outputItem;
	private int popupX;
	private int popupY;
	
	CircuitPopupMenu(CircuitPanel circuitPanel,int x,int y){
		this.parentPanel = circuitPanel;
		this.popupX = x;
		this.popupY = y;
		orItem = new JMenuItem("OR");
		andItem = new JMenuItem("AND");
		notItem = new JMenuItem("NOT");
		inputItem = new JMenuItem("INPUT");
		outputItem = new JMenuItem("OUTPUT");
		
		orItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OrComponent comp = new OrComponent(parentPanel, popupX, popupY);
				parentPanel.addComponent(comp);

			}
		});
		
		andItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AndComponent comp = new AndComponent(parentPanel, popupX, popupY);
				parentPanel.addComponent(comp);
			}
		});
		
		notItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NotComponent comp = new NotComponent(parentPanel, popupX, popupY);
				parentPanel.addComponent(comp);
			}
		});
		inputItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				InputComponent comp = new InputComponent(parentPanel, popupX, popupY);
				parentPanel.addComponent(comp);
			}
		});
		outputItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				OutputComponent comp = new OutputComponent(parentPanel, popupX, popupY);
				parentPanel.addComponent(comp);
			}
		});
		this.add(andItem);
		this.add(orItem);
		this.add(notItem);
		this.add(inputItem);
		this.add(outputItem);
		
		
	}

}
