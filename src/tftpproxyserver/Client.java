package tftpproxyserver;

import tftpproxyserver.tftpprotocol.*;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketTimeoutException;

public class Client {

    public static void main(String args[]) throws IOException {
        int port = 2691;
        String url = "http://cs.oswego.edu/~bdorsey/doit.jpg";
        String host = "192.168.50.60";
        InetAddress serverAddress = InetAddress.getByName(host);

        TFTP tftp = new TFTP();

//        ReadRequestPacket requestPacket = new ReadRequestPacket(serverAddress, port, ReadRequestPacket.PACKET_TYPE, url);

        byte[] blockNumber = {(byte)0, (byte)0};

        byte[] blockData = new byte[10];
        DataPacket pd = new DataPacket(serverAddress, port, DataPacket.PACKET_TYPE, blockNumber, blockData);

//        System.out.println(requestPacket.toString());
//        // send a request
//        tftp.sendPacket(requestPacket, false);

        System.out.println(pd.toString());
        tftp.sendPacket(pd, false);
        System.out.println("Request Sent.");

//        Thread.sleep(10);
        try {
            TFTPPacket packet = tftp.receivePacket(100);


            System.out.println("HERE");
            if (packet.getType() == 3) {
                DataPacket dataPacket = (DataPacket) packet;
                System.out.println("We got a data packet, lets send an ACK");
                for (byte b : dataPacket.getBlockData()) {

                    System.out.print(b);
                }

                AckPacket ackPacket = new AckPacket(serverAddress, port, AckPacket.PACKET_TYPE, dataPacket.getBlockNumber());
                tftp.sendPacket(ackPacket, false);
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
        }


    }

}
