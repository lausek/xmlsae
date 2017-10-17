package view.atoms;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import javax.imageio.ImageIO;
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
			// TODO: adjust path to image
			BufferedImage buffer = ImageIO.read(new File("link/to/icon"));
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