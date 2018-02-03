import java.io.*;
import java.net.*;

public class TroyTestClient {
	public static void main(String[] args) throws IOException {

		Socket kkSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;

		try {
			kkSocket = new Socket("127.0.0.1", 8080);
			out = new PrintWriter(kkSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: taranis.");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: taranis.");
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromServer;
		String fromUser;

		while (true) {
			for (fromServer = in.readLine(); !fromServer.isEmpty(); fromServer = in.readLine()) {
				System.out.println("Server: " + fromServer);
			}
			System.out.println("waiting");
			for (fromUser = stdIn.readLine(); !fromUser.isEmpty(); fromUser = stdIn.readLine()) {
				//System.out.println("User: " + fromUser);
				out.println(fromUser);
			}
			//System.out.println("User: " + fromUser + ".");
			out.println(fromUser);
			System.out.println("Sent");
		}

		/*out.close();
		in.close();
		stdIn.close();
		kkSocket.close(); */
	}
}