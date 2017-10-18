package view.atoms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Predicate;

import javax.swing.JButton;

import view.Display;
import view.Display.AppScreen;

/**
 * 
 * Class for navigating around with arrows. TODO: Should this work with keyboard
 * shortcuts too?
 * 
 * @author lausek
 *
 */

@SuppressWarnings("serial")
public class CSwitchArrow extends JButton implements ActionListener {

	public enum MoveDirection {
		LEFT, RIGHT
	}

	private Display display;
	private AppScreen switchTo;
	private Predicate<AppScreen> leaveCondition;
	
	public CSwitchArrow(Display display, AppScreen switchTo) {
		this.display = display;
		this.switchTo = switchTo;

		setSize(45, 20);

		addActionListener(this);
	}
	
	public CSwitchArrow(Display display, AppScreen switchTo, MoveDirection direction) {
		this(display, switchTo);
		setDirection(direction);
	}

	public CSwitchArrow setDirection(MoveDirection direction) {

		// TODO: make symbols prettier
		switch (direction) {
		case LEFT:
			setText("<-");
			break;
		case RIGHT:
			setText("->");
			break;
		}

		return this;
	}
	
	public void setCondition(Predicate<AppScreen> condition) {
		leaveCondition = condition;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(leaveCondition != null && !leaveCondition.test(switchTo)) {
			return;
		}
		
		display.setScreen(switchTo);
		
	}

}
