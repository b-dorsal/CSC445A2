package tftpproxyserver.tftpprotocol;

import java.io.IOException;
import java.net.*;

public class TFTP {

    private DatagramSocket socket;


    public static final int MAX_PACKET_SIZE = 516;

    public TFTP() throws SocketException {
        socket = new DatagramSocket();
    }

    public TFTP(int port, String host) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(port, InetAddress.getByName(host));
    }


    public void sendPacket(TFTPPacket packet, boolean drop) throws IOException {
        socket.send(packet.getDatagramPacket());
    }

    public TFTPPacket receivePacket(int timeout) throws IOException {
        //create a new DatagramPacket to hold the data
        DatagramPacket packet = new DatagramPacket(new byte[MAX_PACKET_SIZE], MAX_PACKET_SIZE);

        //set the timeout
        socket.setSoTimeout(timeout);

        socket.receive(packet);
        System.out.println(packet.getPort());
        socket.setSoTimeout(0);

        return TFTPPacket.getTFTPPacket(packet);

    }
}
