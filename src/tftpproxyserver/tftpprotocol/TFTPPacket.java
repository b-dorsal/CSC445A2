package tftpproxyserver.tftpprotocol;

import java.net.DatagramPacket;
import java.net.InetAddress;

public abstract class TFTPPacket {
    public static final int READ_REQUEST = 1; //RRQ
//    public static final int WRITE_REQUEST = 2; //WRQ
    public static final int DATA = 3; //DATA
    public static final int ACKNOWLEDGEMENT = 4; //ACK
    public static final int ERROR = 5; //ERROR

    private int type;
    private int port;
    private InetAddress address;

    public TFTPPacket(InetAddress address, int port, int type){
        this.type = type;
        this.port = port;
        this.address = address;
    }

    public TFTPPacket(DatagramPacket packet, int type){
        this.type = type;
        this.port = packet.getPort();
        this.address = packet.getAddress();
    }

    public abstract DatagramPacket getDatagramPacket();

    public final static TFTPPacket getTFTPPacket(DatagramPacket packet){
        TFTPPacket tftpPacket = null;

        byte[] data = packet.getData();

        byte type = data[1];
        if(type == READ_REQUEST){
            tftpPacket = new ReadRequestPacket(packet, type);
        }else if(type == DATA){
            tftpPacket = new DataPacket(packet, type);
        }else if(type == ACKNOWLEDGEMENT){
            tftpPacket = new AckPacket(packet, type);
        }else if(type == ERROR){
//            tftpPacket = new ErrorPacket(packet, type);
        }

        return tftpPacket;
    }


    public int getType(){return this.type;}
    public int getPort(){return this.port;}
    public InetAddress getAddress(){return this.address;}

    public String toString(){
        return "TFTPPacket (addr:" + this.address + ", port:" + this.port + ", type:" + this.type + ")";
    }

}
