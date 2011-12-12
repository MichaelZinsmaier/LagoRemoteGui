import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.LinkedList;


public class GuiServer {

	
	public boolean _run;
	private LinkedList<ClientHandlerThread> _clients = new LinkedList<ClientHandlerThread>();
	private Window _window;
	
	
	public GuiServer(Window window) {

		_window = window;
		_window.addWindowListener(new MyWindowListener());
		_run = true;
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(4224);
			server.setSoTimeout(1000);
			
			System.out.println("Created Server waiting for client");
			
			while (_run) {
				Socket client = null;
				
				try {
					client = server.accept();

					System.out.println("Connection established");
					
					ClientHandlerThread tmp = new ClientHandlerThread(client, _window);
					tmp.start();
					_clients.add(tmp);
				} catch (SocketTimeoutException e) {
					//if a timeout occurs just search again for a new client
					//keeps the server reactive to _run = false
				}
			}

			
			for (ClientHandlerThread client : _clients) {
				client._run = false;
			}
			
			server.close();
			_window.dispose();			
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	
	
	
	
	private class MyWindowListener implements WindowListener {
		
		@Override
		public void windowOpened(WindowEvent e) {
		}

		@Override
		public void windowClosing(WindowEvent e) {
			_run = false;
		}

		@Override
		public void windowClosed(WindowEvent e) {
		}

		@Override
		public void windowIconified(WindowEvent e) {
		}

		@Override
		public void windowDeiconified(WindowEvent e) {
		}

		@Override
		public void windowActivated(WindowEvent e) {
		}

		@Override
		public void windowDeactivated(WindowEvent e) {
		}
		
	}
	
}
