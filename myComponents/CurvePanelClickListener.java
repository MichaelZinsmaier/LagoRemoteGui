package myComponents;

public interface CurvePanelClickListener {

	/**
	 * submits x and y coordinates in the interval 0..1
	 * and can be registerd on a CurvedPanel
	 * @param x
	 * @param y
	 */
	void panelClicked(float x, float y);
	
}
