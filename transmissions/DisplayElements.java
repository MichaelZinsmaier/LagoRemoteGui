package transmissions;


import java.util.HashMap;

/**
 * encapsulates the values of the display elements. Those are not readable GUI elements like
 * e.g. the fps label.
 * 
 * @author michaZ
 *
 */
public class DisplayElements {

	public int _clientNr;
	public float _sideLength;
	public float _pixelSize;
	
	public boolean _valid;
	
	public DisplayElements (int clientNr, HashMap<String, String> updateMap) {
		
		_valid = true;
		_clientNr = clientNr;
		_sideLength = -1;
		_pixelSize = -1;
		
		if (updateMap.containsKey("pixelSize")) {
			_pixelSize = Float.parseFloat(updateMap.get("pixelSize"));
		} else {
			_valid = false;
		}
		
		if (updateMap.containsKey("sideLength")) {
			_sideLength = Float.parseFloat(updateMap.get("sideLength"));
		} else {
			_valid = false;
		}
				
	}
	
}
