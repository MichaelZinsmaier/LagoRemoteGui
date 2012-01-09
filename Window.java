import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private JCheckBox _adaptiveScaleBars;
	private JCheckBox _showLabels;
	private JCheckBox _animation;
	private JTextField _nodePath;
	private JTextField _edgePath;
	private JTextField _labelCount;
	private JTextField _aniDuration;
	private JComboBox _nodeCS;
	private JComboBox _edgeCS;
	private JComboBox _labelCS;
	
	private CurvePanelEnvironment _curvEnvNode;
	private CurvePanelEnvironment _curvEnvEdge;
	private CurvePanelEnvironment _curvEnvLabel;
	final JFileChooser fc = new JFileChooser();
	
	private JPanel _displayElementsPanel;
	private HashMap<Integer, DisplayElementsPanel> _clientsDisplayElements;
	private HashMap<Integer, Component> _clientsDisplayElementsEmptyBoxes;
	
	public Window() {	

		this.setTitle("Density Points Remote GUI");
		this.setSize(800,770);		
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
	
	private void populateBoxes(JComboBox box) {
		final String PATH_TO_BASE_DIR = "../HGvis/HGvis/_Tex/colorSchemes" ;
		File dir = new File(PATH_TO_BASE_DIR);

		File[] filesInDir = dir.listFiles();
		
		for (int i = 0; i < filesInDir.length; i++) {
			box.addItem(filesInDir[i].getName());
		}
	}
	
	private void selectPath(boolean node) {
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
			_adaptiveScaleBars = new JCheckBox("adaptive scalebars");
			_adaptiveScaleBars.setSelected(true);
			fOptionPanel.add(_adaptiveScaleBars, c2);
			
			c2.gridx = 0;
			c2.gridy = 2;	
			_showLabels = new JCheckBox("show labels");
			_showLabels.setSelected(true);
			fOptionPanel.add(_showLabels, c2);
			
			c2.gridx = 1;
			c2.gridy = 2;
			_labelCount = new JTextField("20");
			_labelCount.setColumns(5);
			fOptionPanel.add(_labelCount, c2);

			c2.gridx = 0;
			c2.gridy = 3;	
			_animation = new JCheckBox("activate Animation");
			_animation.setSelected(true);
			fOptionPanel.add(_animation, c2);
			
			c2.gridx = 1;
			c2.gridy = 3;
			_aniDuration = new JTextField("2.0");
			_aniDuration.setColumns(5);
			fOptionPanel.add(_aniDuration, c2);
			
			
			c2.gridx = 0;
			c2.gridy = 4;
			_nodePath = new JTextField(NODE_EMPTY, 18);
			_nodePath.setFocusable(false);		
			fOptionPanel.add(_nodePath, c2);	
			
			c2.gridx = 1;
			c2.gridy = 4;
			JButton nodeAction = new JButton("...");
			nodeAction.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					selectPath(true);
				}
			});
			fOptionPanel.add(nodeAction, c2);

			c2.gridx = 0;
			c2.gridy = 5;	
			_edgePath = new JTextField(EDGE_EMPTY, 18);
			_edgePath.setFocusable(false);
			fOptionPanel.add(_edgePath, c2);	
			
			c2.gridx = 1;
			c2.gridy = 5;
			JButton edgeAction = new JButton("...");
			edgeAction.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					selectPath(false);
				}
			});
			fOptionPanel.add(edgeAction, c2);

			JPanel combos = new JPanel(new GridBagLayout());
			GridBagConstraints c3 = new GridBagConstraints();
			c2.insets = new Insets(15,15,15,15);
			c2.gridx = 0;
			c2.gridy = 6;
			c2.gridwidth = 2;
			fOptionPanel.add(combos, c2);
			
				c3.gridx = 0;
				c3.gridy = 0;
				combos.add(new JLabel("node cs"), c3);

				c3.gridx = 1;
				combos.add(new JLabel("edge cs"), c3);

				c3.gridx = 2;
				combos.add(new JLabel("label cs"), c3);			

			
				c3.gridx = 0;
				c3.gridy = 5;
				_nodeCS = new JComboBox();
				populateBoxes(_nodeCS);
				_nodeCS.setSelectedItem("node.tga");
				combos.add(_nodeCS, c3);
			
	
				c3.gridx = 1;
				_edgeCS = new JComboBox();
				populateBoxes(_edgeCS);
				_edgeCS.setSelectedItem("edge.tga");
				combos.add(_edgeCS, c3);
						
				c3.gridx = 2;
				_labelCS = new JComboBox();
				populateBoxes(_labelCS);
				_labelCS.setSelectedItem("label.tga");
				combos.add(_labelCS, c3);
			
			
		c.gridy = 1;
		c.gridx = 0;
		JTabbedPane curveTabs = new JTabbedPane();
		_curvEnvEdge = new CurvePanelEnvironment("E.");
		_curvEnvNode = new CurvePanelEnvironment("N.");
		_curvEnvLabel = new CurvePanelEnvironment("L.");
		curveTabs.add("node scaling", _curvEnvNode);
		curveTabs.add("edge scaling", _curvEnvEdge);
		curveTabs.add("label scaling", _curvEnvLabel);
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
	 * @return read the label scaling mode from the GUI
	 */
	public ScalingOptions getLabelScalingOptions() {
		return _curvEnvLabel.getScalingOptions();
	}

	/**
	 * @return read the options from the GUI
	 */
	public FOptions getFOptions() {
		FOptions ret = new FOptions();
		
		ret._antiAlias = _antiAlias.isSelected();
		ret._adaptiveScaleBars = _adaptiveScaleBars.isSelected();
		ret._showLabels = _showLabels.isSelected();
		ret._animation = _animation.isSelected();
		
		int labels;
		try {
			labels = Integer.parseInt(_labelCount.getText());
		} catch(Exception e) {
			labels = 20;
			_labelCount.setText("20");
		}		
		if (labels <= 0) {
			labels = 20;
			_labelCount.setText("20");			
		}
		
		ret._labelCount = labels;
		
		
		float duration;
		try {
			duration = Float.parseFloat(_aniDuration.getText());
		} catch(Exception e) {
			duration = 2.0f;
			_aniDuration.setText("2.0");
		}		
		if (duration <= 0.0f) {
			duration = 2.0f;
			_aniDuration.setText("2.0");
		}
		
		ret._aniDuration = duration;
		
		ret._edgeFile = _edgePath.getText();
		if (ret._edgeFile.equals(EDGE_EMPTY)) {
			ret._edgeFile = "";
		}
		ret._nodeFile = _nodePath.getText();
		if (ret._nodeFile.equals(NODE_EMPTY)) {
			ret._nodeFile = "";
		}
		
		ret._nodeCS = _nodeCS.getSelectedItem().toString();
		ret._edgeCS = _edgeCS.getSelectedItem().toString();
		ret._labelCS = _labelCS.getSelectedItem().toString();
		
		return ret;
	}
		
	
	/**
	 * @param opt sets the fOptions to the specified values
	 */
	public void setFOptions(FOptions opt) {
		_antiAlias.setSelected(opt._antiAlias);
		_adaptiveScaleBars.setSelected(opt._adaptiveScaleBars);
		_showLabels.setSelected(opt._showLabels);
		
		_labelCount.setText(String.valueOf(opt._labelCount));
		
		_nodePath.setText(opt._nodeFile);
		if (opt._nodeFile.isEmpty()) {
			_nodePath.setText(NODE_EMPTY);
		}		
		_edgePath.setText(opt._edgeFile);
		if (opt._edgeFile.isEmpty()) {
			_edgePath.setText(EDGE_EMPTY);
		}		
		
		_nodeCS.setSelectedItem(opt._nodeCS);
		_edgeCS.setSelectedItem(opt._edgeCS);
		_labelCS.setSelectedItem(opt._labelCS);
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
