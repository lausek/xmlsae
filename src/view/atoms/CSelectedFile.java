package view.atoms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JPanel;

import view.ImportScreen;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFileChooser;

@SuppressWarnings("serial")
public class CSelectedFile extends JPanel {

	private static JFileChooser chooser;

	protected ImportScreen importScreen;
	protected JButton cmdRemove;
	protected JLabel lbSelectedFile;
	protected File selectedFile;

	static {
		chooser = new JFileChooser();
	}

	public CSelectedFile(ImportScreen parent) {

		this.importScreen = parent;
		
		cmdRemove = new JButton("-");
		cmdRemove.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				importScreen.remove(CSelectedFile.this);
			}

		});

		JButton cmdSelect = new JButton("...");
		cmdSelect.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO: Allow multiple files here
				if (JFileChooser.APPROVE_OPTION == chooser.showOpenDialog(null)) {
					selectedFile = chooser.getSelectedFile();
					setLabel(selectedFile.getPath());
				}
			}

		});
		add(cmdSelect);

		lbSelectedFile = new JLabel();
		lbSelectedFile.setMinimumSize(new Dimension(300, 30));
		lbSelectedFile.setPreferredSize(new Dimension(300, 30));
		lbSelectedFile.setMaximumSize(new Dimension(300, 30));
		add(lbSelectedFile);

		setRemovable(false);
		add(cmdRemove);

	}

	public boolean isEmpty() {
		return selectedFile == null || !selectedFile.isFile();
	}

	public void setRemovable(boolean removable) {
		cmdRemove.setText(removable ? "-" : "");
		cmdRemove.setOpaque(removable);
		cmdRemove.setContentAreaFilled(removable);
		cmdRemove.setBorderPainted(removable);
		cmdRemove.setEnabled(removable);
	}

	public void setLabel(String name) {
		lbSelectedFile.setText(name);
		setRemovable(!name.isEmpty());
		revalidate();
	}
	
	public File get() {
		return this.selectedFile;
	}
	
}
