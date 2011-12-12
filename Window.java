import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import myComponent.CurvePanelEnvironment;
import myComponent.DisplayElementsPanel;

import transmissions.DisplayElements;
import transmissions.FOptions;
import transmissions.ScalingOptions;


public class Window extends JFrame {	
	
	final String NODE_EMPTY = "node path";
	final String EDGE_EMPTY = "edge path";
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JCheckBox _antiAlias;
	private JTextField _nodePath;
	private JTextField _edgePath;
	
	private CurvePanelEnvironment _curvEnvNode;
	private CurvePanelEnvironment _curvEnvEdge;

	
	private JPanel _displayElementsPanel;
	private HashMap<Integer, DisplayElementsPanel> _clientsDisplayElements;
	private HashMap<Integer, Component> _clientsDisplayElementsEmptyBoxes;
	
	public Window() {	

		this.setTitle("Density Points Remote GUI");
		this.setSize(800,630);		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.getContentPane().setBackground(Color.white);
		this.setVisible(true);
		
		_clientsDisplayElements = new HashMap<Integer, DisplayElementsPanel>();
		_clientsDisplayElementsEmptyBoxes = new HashMap<Integer, Component>();
		_displayElementsPanel = new JPanel();
		_displayElementsPanel.setLayout(new BoxLayout(_displayElementsPanel, BoxLayout.Y_AXIS));
		_displayElementsPanel.setBackground(Color.white);
		
		addContent();
		validate();

	}
	
	private void selectPath(boolean node) {
		final JFileChooser fc = new JFileChooser();
		int returnVal = fc.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (node) {
				_nodePath.setText(fc.getSelectedFile().getPath());
				_edgePath.setText(EDGE_EMPTY);
			} else {
				_edgePath.setText(fc.getSelectedFile().getPath());
			}			
		} else {
			if (node) {
				_nodePath.setText(NODE_EMPTY);
				_edgePath.setText(EDGE_EMPTY);
			} else {
				_edgePath.setText(EDGE_EMPTY);
			}			
		}
	}
	
	
	private void addContent() {
		
		this.getContentPane().setLayout(new GridBagLayout());	
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(20, 20, 0, 5);		
		c.anchor = GridBagConstraints.FIRST_LINE_START;
		
		c.gridx = 0;
		c.gridy = 0;
		JPanel fOptionPanel = new JPanel(new GridBagLayout());
		fOptionPanel.setBorder(BorderFactory.createLineBorder(Color.gray, 2));
		this.add(fOptionPanel, c);
		
			GridBagConstraints c2 = new GridBagConstraints();
			c2.anchor = GridBagConstraints.FIRST_LINE_START;
			c2.insets = new Insets(15,15,0,15);
			c2.gridx = 0;
			c2.gridy = 0;	
			_antiAlias = new JCheckBox("activate / deactivate Antialiasing");
			_antiAlias.setSelected(true);
			fOptionPanel.add(_antiAlias, c2);

			
			c2.gridx = 0;
			c2.gridy = 1;
			_nodePath = new JTextField(NODE_EMPTY, 18);
			_nodePath.setFocusable(false);		
			fOptionPanel.add(_nodePath, c2);	
			
			c2.gridx = 1;
			c2.gridy = 1;
			JButton nodeAction = new JButton("...");
			nodeAction.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					selectPath(true);
				}
			});
			fOptionPanel.add(nodeAction, c2);

			c2.gridx = 0;
			c2.gridy = 2;	
			_edgePath = new JTextField(EDGE_EMPTY, 18);
			_edgePath.setFocusable(false);
			fOptionPanel.add(_edgePath, c2);	
			
			c2.insets = new Insets(15,15,15,15);
			c2.gridx = 1;
			c2.gridy = 2;
			JButton edgeAction = new JButton("...");
			edgeAction.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					selectPath(false);
				}
			});
			fOptionPanel.add(edgeAction, c2);
			
		c.gridy = 1;
		c.gridx = 0;
		JTabbedPane curveTabs = new JTabbedPane();
		_curvEnvEdge = new CurvePanelEnvironment("E.");
		_curvEnvNode = new CurvePanelEnvironment("N.");
		curveTabs.add("node scaling", _curvEnvNode);
		curveTabs.add("edge scaling", _curvEnvEdge);
		this.add(curveTabs, c);
			
			
		c.gridx = 1;
		c.gridy = 0;
		c.weightx = 1.0;
		c.weighty = 1.0;
		c.gridwidth = GridBagConstraints.REMAINDER;
		c.gridheight = GridBagConstraints.REMAINDER;
		c.anchor = GridBagConstraints.FIRST_LINE_END;
		this.add(_displayElementsPanel, c);
		


					
	}
		
	/**
	 * @return read the node scaling mode from the GUI
	 */
	public ScalingOptions getNodeScalingOptions() {
		return _curvEnvNode.getScalingOptions();
	}

	/**
	 * @return read the edge scaling mode from the GUI
	 */
	public ScalingOptions getEdgeScalingOptions() {
		return _curvEnvEdge.getScalingOptions();
	}
	

	/**
	 * @return read the options from the GUI
	 */
	public FOptions getFOptions() {
		FOptions ret = new FOptions();
		
		ret._antiAlias = _antiAlias.isSelected();
		ret._edgeFile = _edgePath.getText();
		if (ret._edgeFile.equals(EDGE_EMPTY)) {
			ret._edgeFile = "";
		}
		ret._nodeFile = _nodePath.getText();
		if (ret._nodeFile.equals(NODE_EMPTY)) {
			ret._nodeFile = "";
		}
		
		return ret;
	}
		
	
	/**
	 * @param opt sets the fOptions to the specified values
	 */
	public void setFOptions(FOptions opt) {
		_antiAlias.setSelected(opt._antiAlias);
		_nodePath.setText(opt._nodeFile);
		if (opt._nodeFile.isEmpty()) {
			_nodePath.setText(NODE_EMPTY);
		}		
		_edgePath.setText(opt._edgeFile);
		if (opt._edgeFile.isEmpty()) {
			_edgePath.setText(EDGE_EMPTY);
		}		
	}
	
	
	/**
	 * @param el update the {@link DisplayelementsPanel} that displays the clients information 
	 */
	public void updateDisplayElements(DisplayElements el) {
		
		if (_clientsDisplayElements.containsKey(Integer.valueOf(el._clientNr))) {
			DisplayElementsPanel tmp = _clientsDisplayElements.get(Integer.valueOf(el._clientNr));
			tmp.updatePanel(el);
		} else {
			DisplayElementsPanel tmp = new DisplayElementsPanel();
			Component tmp2 = Box.createRigidArea(new Dimension(0,4));
			
			tmp.updatePanel(el);
			_clientsDisplayElements.put(el._clientNr, tmp);
			_clientsDisplayElementsEmptyBoxes.put(el._clientNr, tmp2);
			_displayElementsPanel.setAlignmentX(LEFT_ALIGNMENT);
			_displayElementsPanel.setAlignmentY(TOP_ALIGNMENT);
			_displayElementsPanel.add(tmp);
			_displayElementsPanel.add(tmp2);
		}
	}
	
	/**
	 * 
	 * @param clientNr remove the {@link DisplayElementsPanel} of a client
	 */
	public void removeDisplayElements(int clientNr) {
		if (_clientsDisplayElements.containsKey(Integer.valueOf(clientNr))) {
			DisplayElementsPanel tmp = _clientsDisplayElements.remove(Integer.valueOf(clientNr));
			Component tmp2 = _clientsDisplayElementsEmptyBoxes.remove(Integer.valueOf(clientNr));
			_displayElementsPanel.remove(tmp);
			_displayElementsPanel.remove(tmp2);
		}
		
		_displayElementsPanel.revalidate();
	}
	
}
