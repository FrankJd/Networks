/*
 * Author: Troy Nechanicky, nech5860, 150405860 
 * 	Frank Khalil, khal6600, 160226600
 * Group: 08
 * Version: March 6, 2018
 */

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

/* Relies on the following files to be in default package:
 *  Socket
 */

public class SenderSocket extends Socket {

	SenderSocket(int localPort, int receiverPort, InetAddress receiverAddr) throws SocketException {
		super(localPort, receiverPort, receiverAddr);
	}

	//signal End Of Transmission bit  
	void sendEOT() throws IOException {
		sendBuf[0] = (byte) (EOTMask + seqNum);
		sendPacket.setLength(1);

		send(sendPacket);

		return;
	}
}
