package view.atoms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.function.Predicate;

import javax.swing.ImageIcon;
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

	private static ImageIcon leftArrow = null;
	private static ImageIcon rightArrow = null;

	static {
		try {
			leftArrow = new ImageIcon(javax.imageio.ImageIO.read(new File("media/img/arrow_left_32x32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			rightArrow = new ImageIcon(javax.imageio.ImageIO.read(new File("media/img/arrow_right_32x32.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public enum MoveDirection {
		LEFT, RIGHT
	}

	private Display display;
	private AppScreen switchTo;
	private Predicate<AppScreen> leaveCondition;

	public CSwitchArrow(Display display, AppScreen switchTo) {
		this.display = display;
		this.switchTo = switchTo;
		
		setContentAreaFilled(false);
		
		addActionListener(this);
	}

	public CSwitchArrow(Display display, AppScreen switchTo, MoveDirection direction) {
		this(display, switchTo);
		setDirection(direction);
	}

	public CSwitchArrow setDirection(MoveDirection direction) {

		switch (direction) {
		case LEFT:
			setIcon(leftArrow);
			break;
		case RIGHT:
			setIcon(rightArrow);
			break;
		}

		return this;
	}

	public void setCondition(Predicate<AppScreen> condition) {
		leaveCondition = condition;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (leaveCondition != null && !leaveCondition.test(switchTo)) {
			return;
		}

		display.setScreen(switchTo);

	}

}
