

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/* Relies on the following files to be in default package:
 * 	GUI
 * 	IsbnValidator
 */

import javax.swing.JOptionPane;

public class Receiver {
	public boolean isConnected;
	final int reservedPort = 1023; 
	Socket socket;
	DataOutputStream out;
	BufferedReader in;

	Receiver() { }

	public static void main(String args[]) {
		new GUI(new Receiver());
	}
}
