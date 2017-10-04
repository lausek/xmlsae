package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

/**
 * Dann erben wir da 5 mal von!!!!
 */
public class Screen implements ActionListener {

	protected JPanel panel;
	protected Display parent;

	public void build() {
		// TODO - implement Screen.build
		throw new UnsupportedOperationException();
	}

	/**
	 * Override in subclasses
	 */
	public Object getMainResult() {
		// TODO - implement Screen.getMainResult
		throw new UnsupportedOperationException();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}