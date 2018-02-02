import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @throws Exception if portNumber arg is invalid
 * 
 * @author troyn
 *
 */
public class Server {
	public static void main(String argv[]) throws Exception {
		int portNumber = new Integer(argv[0]);
		//will throw exception if portNumber is invalid
		@SuppressWarnings("resource")
		ServerSocket socket = new ServerSocket(portNumber);

		//continuously listen for connection requests
		//create thread for each request
		while (true) {
			//listen and accept connection request
			Socket connection = socket.accept();
			ClientHandler request = new ClientHandler(connection);
			Thread thread = new Thread(request);
			thread.start();
		}
	}
}
