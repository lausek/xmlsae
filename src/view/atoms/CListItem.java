package view.atoms;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CListItem extends JPanel {

	private JLabel nameLabel;

	public CListItem(String name) {
		nameLabel = new JLabel(name);
		add(nameLabel);
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
		return new Dimension(100, 20);
	}

	@Override
	public Dimension getPreferredSize() {
		return getMinimumSize();
	}

}