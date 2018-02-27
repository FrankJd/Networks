

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.swing.JLabel;

/* Relies on the following files to be in default package:
 * 	GUI
 * 	IsbnValidator
 */

import javax.swing.JOptionPane;

public class Receiver extends Host {
	//private ReceiverSocket socket;
	private boolean reliable;
	private FileOutputStream fileOutput;
	GUI gui;

	Receiver(int receiverPort, int senderPort, String senderAddr, boolean reliable, String filename) {
		this.reliable = reliable; 

		try {
			socket = new ReceiverSocket(receiverPort, senderPort, InetAddress.getByName(senderAddr));
			fileOutput = new FileOutputStream(filename);
		} catch (SocketException | UnknownHostException e) {
			JOptionPane.showMessageDialog(null, "ERROR: Invalid connection parameters", null, JOptionPane.ERROR_MESSAGE);
			printException(e, "ERROR: Invalid parameters");
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "ERROR: Invalid filename", null, JOptionPane.ERROR_MESSAGE);
			printException(e, "ERROR: Invalid filename");
			return;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: Unexpected exception", null, JOptionPane.ERROR_MESSAGE);
			printException(e);
		}
	}

	Receiver(GUI gui) {
		this.gui = gui;
	}

	public static void main(String args[]) {
		new GUI();
	}

	public void transfer() {
		try {
			getFile();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "ERROR: Unexpected exception", null, JOptionPane.ERROR_MESSAGE);
			printException(e);
			closeAll();
			return;
		}
		
		closeAll();
		
		JOptionPane.showMessageDialog(null, "SUCCESS: Transfer completed", null, JOptionPane.PLAIN_MESSAGE);

		return;
	}

	private void getFile() throws IOException {
		boolean received = false;
		boolean EOT = false;
		byte[] buf;
		int bufLen;

		((ReceiverSocket) socket).sendSOT();

		while (!EOT) {
			((ReceiverSocket) socket).receive(reliable);

			buf = socket.receivePacket.getData();
			bufLen = socket.receivePacket.getLength();

			if (isValidSeq(buf[0])) {
				((ReceiverSocket) socket).sendACK();
				//GUI.displayPacketCount(socket.getReceivedCount());

				if (isEOT(buf[0])) {
					EOT = true;
				}
				else {
					fileOutput.write(buf, 1, bufLen-1);
				}
			} else {
				nextSeq();
				((ReceiverSocket) socket).sendACK();
			}

			nextSeq();			
		}

		/*while (!received) {
			socket.receive(reliable);

			buf = socket.receivePacket.getData();

			if (isValidSeq(buf[0])) {
				received = true;
				socket.sendACK();
			} else {
				nextSeq();
				socket.sendACK();
			}

			nextSeq();			
		}*/

		return;	
	}

	private boolean isEOT(byte header) {
		return (header & Socket.EOTMask) == Socket.EOTMask;
	}

	private void closeAll() {
		try {
			fileOutput.close();
			socket.close();
		}
		catch (Exception e) {}

		return;		

	}



}
