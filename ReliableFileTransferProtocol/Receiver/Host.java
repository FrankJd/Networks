
abstract public class Host {
	Socket socket;
	
	Host() { }

	//true if seqNum matches sequence number in data
	boolean isValidSeq(byte header) {
		byte packetSeq = (byte) (header & Socket.seqMask);

		return socket.seqNum == packetSeq;
	}
}
