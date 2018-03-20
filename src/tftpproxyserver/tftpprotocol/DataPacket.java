package tftpproxyserver.tftpprotocol;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class DataPacket extends TFTPPacket {
    public static final int PACKET_TYPE = 3;

    private byte[] blockNumber;
    private byte[] blockData;


    public DataPacket(InetAddress address, int port, int type, byte[] blockNumber, byte[] blockData) {
        super(address, port, type);
        this.blockNumber = blockNumber;
        this.blockData = blockData;
    }

    public DataPacket(DatagramPacket packet, int type) {
        super(packet, type);

        byte[] data = packet.getData();

        byte[] blockNumber = {data[2], data[3]};

        byte[] blockData = new byte[data.length - 4];
        System.arraycopy(data, 4, blockData, 0, data.length - 4); //append the url after the opcode

        setBlockData(blockData);
        setBlockNumber(blockNumber);
    }

    @Override
    public DatagramPacket getDatagramPacket(){

        // allocate space for header
        byte[] data = new byte[2 + 2 + this.blockData.length];

        data[0] = 0; //byte 1 -opcode
        data[1] = (byte)this.getType(); //byte 2 -opcode

        System.arraycopy(this.blockNumber, 0, data, 2, this.blockNumber.length);

        System.arraycopy(blockData, 0, data, 4, blockData.length); //append the mode

        return new DatagramPacket(data, data.length, this.getAddress(), this.getPort());
    }

    public void setBlockNumber(byte[] blockNumber){
        this.blockNumber = blockNumber;
    }

    public void setBlockData(byte[] data){
        this.blockData = data;
    }

    public byte[] getBlockNumber(){
        return this.blockNumber;
    }

    public byte[] getBlockData(){
        return this.blockData;
    }

    @Override
    public String toString(){
        return "Data Packet " + this.getBlockNumber()[0] + "," + this.getBlockNumber()[1];
    }


}
