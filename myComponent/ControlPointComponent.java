package myComponent;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class ControlPointComponent extends JPanel implements CurvePanelClickListener {

	
	private JButton[] _selectionButtons = new JButton[4];
	private JTextField[][] _controlPoints = new JTextField[4][2];
	
	private int _activeIndex = -1;
	private ControlPointsChangeListener _changeListener;
	
	private boolean _isConsistent = true;
	
	
	public ControlPointComponent() {		
		addContent();
		setupListeners();
	}

	public boolean isConsistent() {
		return _isConsistent;
	}
	
	/**
	 * @return a 4x2 array with the x=0 and y=1 coordinates of the control points
	 * if the data is consistent. If not a standard value will be returned.
	 */
	public float[][] getControlPoints() {
		if (!_isConsistent) {
			return new float[][]{{0,0}, {0,0}, {1,1}, {1,1}};
		} else {
			float[][] ret = new float[4][2];
			for(int y = 0; y < 4; y++) {
				for (int x = 0; x < 2; x++) {
					ret[y][x] = parseTextFieldValue(_controlPoints[y][x]);
				}
			}
			return ret;
		}
	}
	
	public void registerChangeListener(ControlPointsChangeListener listener) {
		_changeListener = listener;
	}
	
	private void addContent() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.anchor = GridBagConstraints.LINE_START;
		
		//add 4 control points and their selection buttons
		for (int y = 0; y < 4; y++) {
			c.gridy = y;
			c.gridx = 0;
			_selectionButtons[y] = new JButton(">>");
			_selectionButtons[y].setForeground(Color.gray);
			this.add(_selectionButtons[y], c);
			
			String value = String.valueOf(Math.round(y / 4.0));
			
			c.gridx = 1;			
			_controlPoints[y][0] = new JTextField(value,3);
			this.add(_controlPoints[y][0], c);		
			
			c.gridx = 2;
			_controlPoints[y][1] = new JTextField(value,3);
			this.add(_controlPoints[y][1], c);					
		}
	}
	
	

	/**
	 * get a click event from the {@link CurvePanel} and take the coordinates for the
	 * active controlPoint
	 */
	@Override
	public void panelClicked(float x, float y) {
		
		x = Math.round(x*100) / 100.0f;
		y = Math.round(y*100) / 100.0f;
		
		
		if (_activeIndex == 0) {
			_controlPoints[0][0].setText(String.valueOf(x));
			_controlPoints[0][1].setText(String.valueOf(y));
			testAndUpdateControlFields(0);
		}
		if (_activeIndex == 1) {
			_controlPoints[1][0].setText(String.valueOf(x));
			_controlPoints[1][1].setText(String.valueOf(y));
			testAndUpdateControlFields(1);
		}
		if (_activeIndex == 2) {
			_controlPoints[2][0].setText(String.valueOf(x));
			_controlPoints[2][1].setText(String.valueOf(y));
			testAndUpdateControlFields(2);
		}
		if (_activeIndex == 3) {
			_controlPoints[3][0].setText(String.valueOf(x));
			_controlPoints[3][1].setText(String.valueOf(y));
			testAndUpdateControlFields(3);
		}		
	}
	
	
	/**
	 * setup the listeners for the control point text fields and the 
	 * control point selection buttons
	 */
	private void setupListeners() {
		for (int i = 0; i < _controlPoints.length; i++) {
			addControlPointListener(_controlPoints[i][0], i);
			addControlPointListener(_controlPoints[i][1], i);
		}
		
		for (int i = 0; i < _selectionButtons.length; i++) {
			addPointSelectionButtonListener(i);
		}
	}
	
	/**
	 * adds a action listener to the JButton that sets the active index to the 
	 * field nr of the button.
	 * 
	 * @param fieldNr the index of the control point in the ordering on the GUI
	 */	
	private void addPointSelectionButtonListener(final int fieldNr) {
		_selectionButtons[fieldNr].addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				_activeIndex = fieldNr;
				for (int i = 0; i < _selectionButtons.length; i++) {
					_selectionButtons[i].setForeground(Color.gray);
				}
				_selectionButtons[fieldNr].setForeground(Color.green);
			}
		});
	}
	
	/**
	 * adds a action (enter) and focus lost listener to the JTextField. Both
	 * of them trigger {@link ControlPointComponent#testAndUpdateControlFields(int)} with
	 * the controlPoint as start field.
	 * 
	 * @param point half of a control point x or y textField
	 * @param fieldNr the index of the control point in the ordering on the GUI
	 */
	private void addControlPointListener(JTextField point, final int fieldNr) {
		point.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent arg0) {
				testAndUpdateControlFields(fieldNr);
			}
		});
		
		point.addFocusListener(new FocusListener() {			
			@Override
			public void focusLost(FocusEvent arg0) {
				testAndUpdateControlFields(fieldNr);
			}
			@Override
			public void focusGained(FocusEvent arg0) {			
			}
		});
	}
	
	/**
	 * checks for all y controlPoint fields if they are in the interval 0..1
	 * checks for all x controlPoint fields if they are in the interval 0..1
	 * and if they form a increasing row. First controlPoints x has to be <= 2nds...
	 * 
	 * If necessary tries to auto correct errors in the ordering. 
	 *  
	 * @param startFieldNr number of the field at which auto correction should start
	 */
	private void testAndUpdateControlFields(int startFieldNr) {
		
		boolean consistent = true;
		
		//test and correct y
		for (int i = 0; i < _controlPoints.length; i++) {
			if (parseTextFieldValue(_controlPoints[i][1]) == -1.0f) {
				consistent = false;
			}
		}
		
		//test and correct x at startFieldNr and above
		for (int i = startFieldNr; i >= 0; i--) {
			float value1 = parseTextFieldValue(_controlPoints[i][0]);
			if (value1 == -1.0f) {
				//check field value
				consistent = false;
			} else {
				//field value ok check if it is greater than the field above
				if (i > 0) {
					float value2 = parseTextFieldValue(_controlPoints[i-1][0]);
					if (value2 > value1) {
						//value 2 has to be <= value1 change it
						_controlPoints[i-1][0].setText(String.valueOf(value1));
					}
				}
			}
		}
	
		//test and correct x at startFieldNr and below
		for (int i = startFieldNr; i < _controlPoints.length; i++) {
			float value1 = parseTextFieldValue(_controlPoints[i][0]);
			if (value1 == -1.0f) {
				//check field value
				consistent = false;
			} else {
				//field value ok check if it is smaller than the field below
				if (i < (_controlPoints.length-1)) {
					float value2 = parseTextFieldValue(_controlPoints[i+1][0]);
					if (value2 < value1) {
						//value 2 has to be >= value1 change it
						_controlPoints[i+1][0].setText(String.valueOf(value1));
					}
				}
			}
		}
		
		_isConsistent = consistent;
		_changeListener.pointsChanged(consistent);
	}
	
	

	
	/**
	 * @param field 
	 * @return the float value of the text filed if parsing is possible and the value is between 0 and 1 or -1 if not. In the second
	 * case the textfield is cleard
	 */
	private float parseTextFieldValue(JTextField field) {
		float val = -1.0f;				
		try {
			val = Float.parseFloat(field.getText());
		} catch (Exception exception) {}
		
		if (val >= 0.0 && val <= 1.0) {
			return val;
		} else {
			field.setText("");
			return -1.0f;
		}
	}
	
}
