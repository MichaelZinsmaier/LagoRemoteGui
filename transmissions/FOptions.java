package transmissions;
import java.util.HashMap;


public class FOptions {
	
	public boolean _antiAlias;
	public boolean _adaptiveScaleBars;
	public int _labelCount;
	public boolean _showLabels;
	public boolean _animation;
	public float _aniDuration;
	public String _nodeFile;
	public String _edgeFile;
	public String _nodeCS;
	public String _edgeCS;
	public String _labelCS;
	
	public void update(HashMap<String, String> updateMap) {
		
		if (updateMap.containsKey("antiAlias")) {
			_antiAlias = (Boolean.parseBoolean(updateMap.get("antiAlias")));
		}
		if (updateMap.containsKey("adaptiveScaleBars")) {
			_adaptiveScaleBars = (Boolean.parseBoolean(updateMap.get("adaptiveScaleBars")));
		}
		if (updateMap.containsKey("nodeFile")) {
			_nodeFile = updateMap.get("nodeFile").substring(2);
		} 
		if (updateMap.containsKey("edgeFile")) {
			_edgeFile = updateMap.get("edgeFile").substring(2);
		} 			
		if (updateMap.containsKey("nodeCS")) {
			_nodeCS = updateMap.get("nodeCS");
		}
		if (updateMap.containsKey("labelCS")) {
			_labelCS = updateMap.get("labelCS");
		}
		if (updateMap.containsKey("showLabels")) {
			_showLabels = (Boolean.parseBoolean(updateMap.get("showLabels")));
		}
		if (updateMap.containsKey("labelCount")) {
			_labelCount = Integer.valueOf(updateMap.get("labelCount"));
		}
		if (updateMap.containsKey("animation")) {
			_animation = (Boolean.parseBoolean(updateMap.get("animation")));
		}
		if (updateMap.containsKey("aniDuration")) {
			_aniDuration = Float.valueOf(updateMap.get("aniDuration"));
		}		
	}
	
	
	/**
	 * @return translation of the FOptions object into a command string in the format
	 * parameter1$value parameter2$value parameter3$value...
	 */
	public String toCommandString() {
		
		String ret = "";
		
		ret += " antiAlias$" + String.valueOf(_antiAlias);
		ret += " adaptiveScaleBars$" + String.valueOf(_adaptiveScaleBars);
		ret += " nodeFile$" + "f." + _nodeFile;
		ret += " edgeFile$" + "f." + _edgeFile;
		ret += " nodeCS$" + _nodeCS;
		ret += " edgeCS$" + _edgeCS;
		ret += " labelCS$" + _labelCS;
		ret += " showLabels$" + String.valueOf(_showLabels);
		ret += " labelCount$" + _labelCount;
		ret += " animation$" + String.valueOf(_animation);
		ret += " aniDuration$" + _aniDuration;
		
		return ret;
	}
	
}
