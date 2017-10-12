package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Screen extends JPanel implements ActionListener {

	protected Display parent;
	
	public Screen(Display parent) {
		this.build();
		this.parent = parent;
	}
	
	public void onEnter() { }
	
	public void onLeave() { }
	
	public void build() { }

	/**
	 * Override in subclasses
	 */
	public void getMainResult(Consumer<Object> action) { }

	@Override
	public void actionPerformed(ActionEvent arg0) { }

}