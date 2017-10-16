package view.atoms;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPasswordField;

/**
 * Custom Password Field
 * 
 * JPasswordField with placeholder functionality. TODO: We can probably rewrite
 * this together with CTextField.
 * 
 * @author lausek
 *
 */

@SuppressWarnings("serial")
public class CPasswordField extends JPasswordField {

	private final Color PLACEHOLDER_COLOR = Color.GRAY;

	private String placeholder;

	public CPasswordField(String placeholder) {
		this.placeholder = placeholder;
	}

	@Override
	protected void paintComponent(final Graphics pG) {
		super.paintComponent(pG);

		if (getPassword().length > 0) {
			return;
		}

		final Graphics2D g = (Graphics2D) pG;
		g.setColor(PLACEHOLDER_COLOR);
		g.drawString(placeholder, getInsets().left, pG.getFontMetrics().getMaxAscent() + getInsets().top);
	}

}