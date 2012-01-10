package transmissions;

import myComponents.CurvePanel.SCALING_MODE;

public class ScalingOptions {
	
	private String _prefix;
	
	public ScalingOptions(String prefix) {
		_prefix = prefix;		
	}

	public SCALING_MODE _mode;
	public float[][] _linearControlPoints;
	public float _exponent;
	
	
	public String toCommandString() {
		if (_mode.equals(SCALING_MODE.EXP)) {
			//exponential case
			return " "+ _prefix + "mode$exp" + " "+ _prefix + "exp$" + _exponent;
		} else {
			//linear case
			return " "+ _prefix + "mode$lin " +
				   " "+ _prefix + "p1x$" + _linearControlPoints[0][0] + " "+ _prefix + "p1y$" + _linearControlPoints[0][1] +
				   " "+ _prefix + "p2x$" + _linearControlPoints[1][0] + " "+ _prefix + "p2y$" + _linearControlPoints[1][1] +
				   " "+ _prefix + "p3x$" + _linearControlPoints[2][0] + " "+ _prefix + "p3y$" + _linearControlPoints[2][1] +
				   " "+ _prefix + "p4x$" + _linearControlPoints[3][0] + " "+ _prefix + "p4y$" + _linearControlPoints[3][1];
		}
	}
	
}
