package view.atoms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

	public CSwitchArrow(Display display, AppScreen switchTo) {
		this.display = display;
		this.switchTo = switchTo;

		setSize(45, 20);

		addActionListener(this);
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

	@Override
	public void actionPerformed(ActionEvent e) {
		display.setScreen(switchTo);
	}

}
