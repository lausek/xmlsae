package view.atoms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

/**
 * A custom panel that is used for displaying databases on SelectionScreen.
 * 
 * @author lausek
 *
 */
@SuppressWarnings("serial")
public class CListItem extends JPanel {

	public static final Color COLOR_SELECTED = new Color(255, 156, 20);
	public static final Color COLOR_DESELECTED = new Color(100, true);

	private static final int WIDTH = 100;
	private static final int HEIGHT = 40;

	private static ImageIcon icon;

	private boolean selected;
	private JLabel nameLabel;

	static {
		try {
			Image img = javax.imageio.ImageIO.read(new java.io.File("media/img/db_48x48.png")).getScaledInstance(32, 32,
					Image.SCALE_DEFAULT);
			// Create buffered version of this image (add alpha too)
			BufferedImage buffer = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);

			// Convert Image to BufferedImage by writing to buffer
			Graphics2D graphics = buffer.createGraphics();
			graphics.drawImage(img, 0, 0, null);
			graphics.dispose();

			icon = new ImageIcon(buffer);
		} catch (IOException e) {
			// TODO: add logger here
		}
	}

	public CListItem(String name) {
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		JLabel iconLabel = new JLabel(icon);
		iconLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(iconLabel);

		nameLabel = new JLabel(name);
		add(nameLabel);

		// Make everything deselected
		selected = true;
		// toggleSelection makes 'selected' false
		toggleSelection();
	}

	public void toggleSelection() {
		selected = !selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public String getName() {
		return nameLabel.getText();
	}

	@Override
	public Dimension getMinimumSize() {
		return new Dimension(WIDTH, HEIGHT);
	}

	@Override
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}

}