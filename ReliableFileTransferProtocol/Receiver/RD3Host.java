
public class RD3Host {
	byte seqNum = 0;
	static byte seqMask = 0b00000001;
	static byte EOTMask = 0b00000010;
	static byte IFMask = 0b00000100;
	
	//true if seqNum matches sequence number in data
	boolean isValidSeq(byte[] data) {
		byte packetSeq = (byte) (data[1] & seqMask);
		
		return seqNum == packetSeq;
	}

}
