package view.atoms;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JTextField;

/**
 * Custom Text Field
 * 
 * JTextField with placeholder functionality.
 * 
 * @author lausek
 *
 */

@SuppressWarnings("serial")
public class CTextField extends JTextField {

	private final Color PLACEHOLDER_COLOR = Color.GRAY;

	private String placeholder, def;

	public CTextField(String placeholder) {
		this(placeholder, "");
	}
	
	public CTextField(String placeholder, String def) {
		this.placeholder = placeholder;
		this.def = def;
	}

	@Override
	protected void paintComponent(final Graphics pG) {
		super.paintComponent(pG);

		if (super.getText().length() > 0) {
			return;
		}

		final Graphics2D g = (Graphics2D) pG;
		g.setColor(PLACEHOLDER_COLOR);
		g.drawString(placeholder, getInsets().left, pG.getFontMetrics().getMaxAscent() + getInsets().top);
	}

	@Override
	public String getText() {
		if(super.getText().equals("")) {
			return this.def;
		}
		return super.getText();
	}
	
}
