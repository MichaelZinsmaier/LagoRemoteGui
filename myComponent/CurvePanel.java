package myComponent;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;


import javax.swing.BorderFactory;
import javax.swing.JPanel;

import transmissions.ScalingOptions;

/**
 * displays a scaling curve. Either a exponential curve or a stepwise linear function.
 * 
 * @author MichaelZ
 *
 */
@SuppressWarnings("serial")
public class CurvePanel extends JPanel {
	
	
	public enum SCALING_MODE {EXP, LINEAR};
	
	private final int SIDE_LENGTH = 300;
	
	private SCALING_MODE _mode = SCALING_MODE.EXP;
	private float _exponent = 0.333f;
	private float[][] _controlPoints = { {0.8f, 1}, {1,1}, {1,1}, {1,1} }; 
	

	
	private CurvePanelClickListener _clickListen;
	
	private String _prefix;
	
	public CurvePanel(String prefix) {
		_prefix = prefix;
		
		if (_prefix.equals("L.")) {
			_exponent = 0.5f;
		}
		
		Dimension dim = new Dimension(SIDE_LENGTH, SIDE_LENGTH);	
		this.setSize(dim);
		this.setMaximumSize(dim);
		this.setMinimumSize(dim);
		this.setPreferredSize(dim);
		this.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		this.addMouseListener(new MyMouseListener());
	}
	
	/**
	 * @return the scaling options used at the call time
	 */
	public ScalingOptions getScalingOptions() {
		ScalingOptions ret = new ScalingOptions(_prefix);
		
		if (_mode.equals(SCALING_MODE.EXP)) {
			ret._mode = SCALING_MODE.EXP;
			ret._exponent = _exponent;
		} else {
			ret._mode = SCALING_MODE.LINEAR;
			ret._linearControlPoints = _controlPoints;
		}
		return ret;
	}
	
		
	public void registerClickListener(CurvePanelClickListener listener) {
		_clickListen = listener;
	}
	

	
	/**
	 * @param exponent x^exponent function will be displayed
	 */
	public void setExponent(float exponent) {
		_exponent = exponent;
		_mode = SCALING_MODE.EXP;
		this.repaint();
	}
	
	
	/**
	 * @param points [4][2] array of control points for the scaling
	 */
	public void setControlPoints(float[][] points) {
		_controlPoints = points;
		_mode = SCALING_MODE.LINEAR;
		this.repaint();
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    Graphics2D g2 = (Graphics2D) g;
	    
	    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    
	    Stroke oldStroke = g2.getStroke();
	    Color oldColor = g2.getColor();
	    
	    	//middle line dashed
	    	g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 1, new float[]{6,4}, 0));
	    	g2.drawLine( 0, SIDE_LENGTH, SIDE_LENGTH,  0);
	    
	    	
	    	//curve
	    	g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2));
	    	g2.setColor(Color.BLUE);
	    	
	    	
	    	if (_mode.equals(SCALING_MODE.EXP)) {

	    		double oldVal = 0.0; // 0^x = 0
	    		final int stepSize = 2;
	    		
	    		
	    		for (int x = stepSize; x < SIDE_LENGTH; x+=stepSize) {
	    			
	    			double value01 = Math.pow((1.0 / SIDE_LENGTH) * x, _exponent);
	    			double valueScaled = value01 * SIDE_LENGTH;

	    			g2.drawLine(x-stepSize, SIDE_LENGTH -(int)Math.floor(oldVal), x, SIDE_LENGTH - (int)Math.floor(valueScaled));
	    			
	    			oldVal = valueScaled;
	    			
	    		}	    		
	    	} else
	    	if (_mode.equals(SCALING_MODE.LINEAR)){
	    		
	    		int[][] p = new int[_controlPoints.length][_controlPoints[0].length];
	    		
	    		for (int i = 0; i < _controlPoints.length; i++) {
	    			for (int j = 0; j < 2; j++) {
	    				p[i][j] = (int)Math.floor(_controlPoints[i][j] * SIDE_LENGTH);
	    			}
	    		}
	    		
	    		g2.drawLine(      0, SIDE_LENGTH -       0, p[0][0], SIDE_LENGTH - p[0][1]);
	    		g2.drawLine(p[0][0], SIDE_LENGTH - p[0][1], p[1][0], SIDE_LENGTH - p[1][1]);
	    		g2.drawLine(p[1][0], SIDE_LENGTH - p[1][1], p[2][0], SIDE_LENGTH - p[2][1]);
	    		g2.drawLine(p[2][0], SIDE_LENGTH - p[2][1], p[3][0], SIDE_LENGTH - p[3][1]);
	    		g2.drawLine(p[3][0], SIDE_LENGTH - p[3][1], SIDE_LENGTH, 0);
	    		
	    		g2.setColor(Color.green);
	    		
	    		//control points
	    		g2.drawOval(p[0][0] - 2, SIDE_LENGTH - p[0][1] - 2, 4, 4);
	    		g2.drawOval(p[1][0] - 2, SIDE_LENGTH - p[1][1] - 2, 4, 4);
	    		g2.drawOval(p[2][0] - 2, SIDE_LENGTH - p[2][1] - 2, 4, 4);
	    		g2.drawOval(p[3][0] - 2, SIDE_LENGTH - p[3][1] - 2, 4, 4);
	    	}
	    
	    g2.setStroke(oldStroke);
	    g2.setColor(oldColor);
	}
	
	
	private class MyMouseListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (_clickListen != null) {
				float x = e.getPoint().x / (float)SIDE_LENGTH;
				float y = (SIDE_LENGTH - e.getPoint().y) / (float)SIDE_LENGTH;
				
				_clickListen.panelClicked(x, y);
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

	}



}
