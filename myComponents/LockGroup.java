package myComponents;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class LockGroup extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	enum LOCK_STATE {LOCKED, UNLOCKED, LOCKED_OVER};
	
	private JLabel _nodeLabel = new JLabel("nodeMax:");
	private JLabel _edgeLabel = new JLabel("edgeMax:");
	
	private JTextField _nodeText = new JTextField("1.0");
	private JTextField _edgeText = new JTextField("1.0");
	
	private JButton _lockButton = new JButton();
	
	private ImageIcon _unlocked = new ImageIcon("unlocked.gif");	
	private ImageIcon _locked = new ImageIcon("locked.gif");
	private ImageIcon _lockedOver = new ImageIcon("lockedOver.gif");
	LOCK_STATE _lockState = LOCK_STATE.UNLOCKED;
	
	
	public LockGroup() {
		initComponents();
		
	}
	
	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridy = 0;
		c.insets = new Insets(0,22,0,0);
		
		c.gridx = 0;
		add(_nodeLabel, c);
		
		c.gridx = 1;
		_nodeText.setColumns(9);
		add(_nodeText, c);

		c.gridx = 0;
		c.gridy = 1;
		add(_edgeLabel, c);

		c.gridx = 1;
		_edgeText.setColumns(9);
		add(_edgeText, c);
		
		c.insets = new Insets(0,35,0,0);
		c.gridx = 2;
		c.gridy = 0;
		c.gridheight = 2;
		_lockButton.setIcon(_unlocked);
		_lockButton.setSize(32,32);
		_lockButton.setPreferredSize(new Dimension(32,32));
		_lockButton.setMaximumSize(new Dimension(32,32));
		_lockButton.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				changeLockState();
			}
		});
		add(_lockButton, c);		
	}
	
	public void setData(boolean locked, boolean over, float nodeMax, float edgeMax) {
		if (locked) {
			if (!over) {
				_lockState = LOCK_STATE.LOCKED;
				_lockButton.setIcon(_locked);
			} else {
				_lockState = LOCK_STATE.LOCKED_OVER;
				_lockButton.setIcon(_lockedOver);
			}
		} else {
			_lockState = LOCK_STATE.UNLOCKED;
			_lockButton.setIcon(_unlocked);
		}
		
		_nodeText.setText(String.valueOf(nodeMax));
		_edgeText.setText(String.valueOf(edgeMax));
	}
	
	public boolean getLocked() {
		return !(_lockState.equals(LOCK_STATE.UNLOCKED));
	}
	
	public float getNodeMax() {
		float max;
		
		try {
			max = Float.parseFloat(_nodeText.getText());
		} catch (Exception e) {
			max = 1.0f;
		}
		
		if (max <= 0.0f) {
			max = 1.0f;
		}
		
		return max;
	}

	public float getEdgeMax() {
		float max;
		
		try {
			max = Float.parseFloat(_edgeText.getText());
		} catch (Exception e) {
			max = 1.0f;
		}
		
		if (max <= 0.0f) {
			max = 1.0f;
		}
		
		return max;
	}	
	
	private void changeLockState() {
		if (_lockState.equals(LOCK_STATE.UNLOCKED)) {
			_lockState = LOCK_STATE.LOCKED;
			_lockButton.setIcon(_locked);
		} else 
		if (_lockState.equals(LOCK_STATE.LOCKED) || _lockState.equals(LOCK_STATE.LOCKED_OVER)) {
			_lockState = LOCK_STATE.UNLOCKED;
			_lockButton.setIcon(_unlocked);
		}		
	}
	
}
