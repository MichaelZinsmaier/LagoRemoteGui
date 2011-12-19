package transmissions;
import java.util.HashMap;


public class FOptions {
	
	public boolean _antiAlias;
	public boolean _adaptiveScaleBars;
	public String _nodeFile;
	public String _edgeFile;
	public String _nodeCS;
	public String _edgeCS;
	
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
		if (updateMap.containsKey("edgeCS")) {
			_edgeCS = updateMap.get("edgeCS");
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
		
		return ret;
	}
	
}
