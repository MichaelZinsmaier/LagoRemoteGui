import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;

import transmissions.DisplayElements;
import transmissions.FOptions;


public class ClientHandlerThread extends Thread{

	private static int clientNr = 0;
	
	
	public boolean _run;
	
	private int _clientNr;
	private Socket _client;
	private Window _window;
	
	public ClientHandlerThread(Socket client, Window window) {
		_window = window;
		_run = true;
		_client = client;
		clientNr++;
		_clientNr = clientNr;
	}
	
	
	
	public void run() {
			
		try {
			PrintWriter out = new PrintWriter(_client.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(_client.getInputStream()));
			String input;
			
			while (_run) {
				
				input = in.readLine();
				
				if (input != null) {
					
					if (input.matches("(push|pull|exit)#(\\s*\\w+\\$\\S*)+")) {
					
						String cmd = extractCommand(input);
						HashMap<String, String> data = extractData(input);
						
						if (cmd.equals("pull")) {
							//update display elements	
							_window.updateDisplayElements(new DisplayElements(_clientNr, data));
							
							//return the requested values
							String output = "push# " +
												_window.getFOptions().toCommandString() +
												" target$node" + _window.getNodeScalingOptions().toCommandString() +
												" target$edge" + _window.getEdgeScalingOptions().toCommandString() + 
												" target$label" + _window.getLabelScalingOptions().toCommandString();
							out.println(output);
						} else 
						if (cmd.equals("push")) {
							//update FOptions
							FOptions fOpt = _window.getFOptions();
							fOpt.update(data);
							_window.setFOptions(fOpt);
							
							//update display elements	
							_window.updateDisplayElements(new DisplayElements(_clientNr, data));
						} else 
						if (cmd.equals("exit")) {
							_run = false;
						}			
						
					} else {
						System.out.println("invalid command");
					}	
	
				} else {
					//outgoing client connection already closed => shutdown
					_run = false;
				}	
		
			}
			
			_client.shutdownOutput();			//signal the end of the connection
			while ((input = in.readLine()) != null) { //read it empty if necessary
				
			}
			_window.removeDisplayElements(_clientNr);
			out.close(); 
			in.close();
			_client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * takes a network command string of the format   command# parameter1$value parameter2$value parameter3$value
	 * and returns a HashMap with all parameter, value pairs found
	 * 
	 * @param input network command string
	 * @return hash map with wall parameter name, value pairs found
	 */
	private HashMap<String, String> extractData(String input) {
		
		HashMap<String, String> ret = new HashMap<String, String>();
		
		//remove the command
		int cmdEndIndex = input.indexOf('#');
		String data = input.substring(cmdEndIndex + 1);
		
		StringTokenizer tk = new StringTokenizer(data, " $");

		//read all parameter$value pairs
		while (tk.hasMoreTokens()) {
			String parameter = tk.nextToken();
			String value = tk.nextToken();
			ret.put(parameter, value);
		}
		
		return ret;
	}
	
	/**
	 * takes a network command string of the format   command# parameter1$value parameter2$value parameter3$value
	 * and returns the command 
	 * 
	 * @param input a network command string
	 * @return the command
	 */
	private String extractCommand(String input) {
		int cmdEndIndex = input.indexOf('#');
		String cmd = input.substring(0, cmdEndIndex);
		
		return cmd;
	}
	
	
}
