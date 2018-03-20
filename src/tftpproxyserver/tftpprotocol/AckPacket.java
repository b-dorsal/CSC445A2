package tftpproxyserver.tftpprotocol;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class AckPacket extends TFTPPacket {
    public static final int PACKET_TYPE = 4;

    private byte[] blockNumber;

    public AckPacket(InetAddress address, int port, int type, byte[] blockNumber) {
        super(address, port, type);
        this.blockNumber = blockNumber;
    }

    public AckPacket(DatagramPacket packet, int type) {
        super(packet, type);
        byte[] data = packet.getData();
        byte[] blockNumber = {data[2], data[3]};
        setBlockNumber(blockNumber);
    }

    @Override
    public DatagramPacket getDatagramPacket(){

        // allocate space for header
        byte[] data = new byte[2 + 2];

        data[0] = 0; //byte 1 -opcode
        data[1] = (byte)this.getType(); //byte 2 -opcode

        System.arraycopy(this.blockNumber, 0, data, 2, this.blockNumber.length);

        return new DatagramPacket(data, data.length, this.getAddress(), this.getPort());
    }

    public void setBlockNumber(byte[] blockNumber){
        this.blockNumber = blockNumber;
    }

    public byte[] getBlockNumber(){
        return this.blockNumber;
    }

}
