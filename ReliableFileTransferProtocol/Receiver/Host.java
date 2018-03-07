/*
 * Author: Troy Nechanicky, nech5860, 150405860 
 * 	Frank Khalil, khal6600, 160226600
 * Group: 08
 * Version: March 6, 2018
 */

abstract public class Host {
	Socket socket;

	Host() { }
	
	void nextSeq() {
		//flip seqNum bit
		socket.seqNum = (byte) (socket.seqNum ^ 1);
		
		return;
	}

	//true if seqNum matches sequence number in data
	public boolean isValidSeq(byte header) {
		byte packetSeq = (byte) (header & Socket.seqMask);

		return socket.seqNum == packetSeq;
	}
	
	static void printException(Exception e) {
		e.printStackTrace();
	}

	static void printException(Exception e, String msg) {
		System.out.println(msg);
		e.printStackTrace();
	}
}
