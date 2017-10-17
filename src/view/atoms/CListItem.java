package view.atoms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageFilter;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class CListItem extends JPanel {

	private final int WIDTH = 100;
	private final int HEIGHT = 40;
	private final Color COLOR_SELECTED = Color.LIGHT_GRAY;
	private final Color COLOR_DESELECTED = new Color(100, true);

	private boolean selected;
	private JLabel nameLabel;

	private static ImageIcon icon;

	static {
		try {
			// TODO: replace this crappy image
			Image img = javax.imageio.ImageIO.read(new java.io.File("media/img/db_64x64.png")).getScaledInstance(32, 32,
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
		selected = false;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

		// TODO: add alternative path if icon is null
		JLabel iconLabel = new JLabel(icon);
		iconLabel.setHorizontalAlignment(SwingConstants.LEFT);
		add(iconLabel);

		nameLabel = new JLabel(name);
		add(nameLabel);

		toggleSelection();
	}

	public void toggleSelection() {
		if (selected) {
			setBackground(COLOR_SELECTED);
		} else {
			setBackground(COLOR_DESELECTED);
		}

		selected = !selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public String getTitle() {
		return nameLabel.getText();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
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