package myComponent;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import transmissions.DisplayElements;

/**
 * Displays client specific elements like fps... one panel per client.
 * 
 * @author michaZ
 *
 */
@SuppressWarnings("serial")
public class DisplayElementsPanel extends JPanel {

	private JLabel _headLabel;
	private JLabel _pixelSizeLabel;
	private JLabel _sideLength;
	
	public DisplayElementsPanel() {

		this.setLayout(new GridBagLayout());
		this.setBorder(BorderFactory.createLineBorder(Color.gray));
			
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.FIRST_LINE_START;

		c.gridx = 0;
		c.gridy = 0;		
		_headLabel = new JLabel("client: ~");
		this.add(_headLabel, c);
		
		c.gridx = 0;
		c.gridy = 1;
		_pixelSizeLabel = new JLabel("pixelSize: ~");
		this.add(_pixelSizeLabel, c);	

		c.gridx = 0;
		c.gridy = 2;
		_sideLength = new JLabel("sideLength: ~");
		this.add(_sideLength, c);	

		
	}
	
	public void updatePanel(DisplayElements el) {
		_headLabel.setText("client: " + el._clientNr);
		
		if (el._valid) {
			_pixelSizeLabel.setText("pixelSize: " + el._pixelSize);
			_sideLength.setText("sideLength: " + el._sideLength);
		}
	}
}
