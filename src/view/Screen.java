package view;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.swing.JPanel;

/**
 * Dann erben wir da 5 mal von!!!!
 */
public class Screen extends JPanel implements ActionListener {

	protected Display parent;
	
	public Screen(Display parent) {
		this.build();
		
	}
	
	public void onEnter() {
		
	}
	
	public void onLeave() {
		
	}
	
	public void build() {
		
	}

	/**
	 * Override in subclasses
	 */
	public void getMainResult(Consumer<Object> action) {
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}