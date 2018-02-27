import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Socket extends DatagramSocket {
	private int localPort;
	private int receiverPort;
	private InetAddress receiverAddr;
	byte[] sendBuf;
	private byte[] receiveBuf;
	DatagramPacket sendPacket, receivePacket;
	final static int BUF_SIZE = 125; 

	//packet header info (first byte)
	byte seqNum = 0;
	static byte seqMask = 0b00000001; //sequence Mask
	static byte EOTMask = 0b00000010; //End Of Transmission Mask
	static byte SOTMask = 0b00000100; //Start of Transmission Mask

	Socket(int localPort, int receiverPort, InetAddress receiverAddr) throws SocketException {
		super(localPort);

		this.localPort = localPort;
		this.receiverPort = receiverPort;
		this.receiverAddr = receiverAddr;
		sendBuf = new byte[BUF_SIZE];
		receiveBuf = new byte[BUF_SIZE];
		sendPacket = new DatagramPacket(sendBuf, BUF_SIZE, receiverAddr, receiverPort);
		receivePacket = new DatagramPacket(receiveBuf, BUF_SIZE);
	}

	void receive() throws IOException {
		receive(receivePacket);

		return;
	}

	void send(byte[] data, int length) throws IOException {
		sendBuf[0] = seqNum;
		//copy data into buf, leaving header alone
		System.arraycopy(data, 0, sendBuf, 1, data.length);
		sendPacket.setLength(length + 1);

		send(sendPacket);

		return;
	}
}
