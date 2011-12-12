package transmissions;
import java.util.HashMap;


public class FOptions {
	
	public boolean _antiAlias;
	public String _nodeFile;
	public String _edgeFile;
	
	public void update(HashMap<String, String> updateMap) {
		
		if (updateMap.containsKey("antiAlias")) {
			_antiAlias = (Boolean.parseBoolean(updateMap.get("antiAlias")));
		}
		if (updateMap.containsKey("nodeFile")) {
			_nodeFile = updateMap.get("nodeFile").substring(2);
		} 
		if (updateMap.containsKey("edgeFile")) {
			_edgeFile = updateMap.get("edgeFile").substring(2);
		} 				
	}
	
	
	/**
	 * @return translation of the FOptions object into a command string in the format
	 * parameter1$value parameter2$value parameter3$value...
	 */
	public String toCommandString() {
		
		String ret = "";
		
		ret += " antiAlias$" + String.valueOf(_antiAlias);
		ret += " nodeFile$" + "f." + _nodeFile;
		ret += " edgeFile$" + "f." + _edgeFile;
		
		return ret;
	}
	
}
