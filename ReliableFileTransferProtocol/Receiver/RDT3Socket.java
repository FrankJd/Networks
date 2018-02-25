import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class RDT3Socket extends DatagramSocket {
	int localPort;
	int receiverPort;
	InetAddress receiverAddr;
	byte[] sendBuf, receiveBuf;
	DatagramPacket sendPacket, receivePacket;
	
	RDT3Socket(int localPort, int receiverPort, InetAddress receiverAddr, int bufSize) throws SocketException {
		super(localPort);
	
		this.localPort = localPort;
		this.receiverPort = receiverPort;
		this.receiverAddr = receiverAddr;
		sendBuf = new byte[bufSize];
		receiveBuf = new byte[bufSize];
		sendPacket = new DatagramPacket(sendBuf, bufSize, receiverAddr, receiverPort);
		receivePacket = new DatagramPacket(receiveBuf, bufSize);
	}
	
	void send(byte[] data) {
		
	}

	
}
