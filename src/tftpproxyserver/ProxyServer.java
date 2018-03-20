package tftpproxyserver;

import org.omg.CORBA.DATA_CONVERSION;
import tftpproxyserver.tftpprotocol.*;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ProxyServer {

    public static void main(String args[]){
        String host = "192.168.50.60";
        TFTP server = null;
        try {
            server = new TFTP(2691, host);
            System.out.println("TFTP Proxy Server Started on " + host + ":" + 2691);
        }catch(SocketException ex){
            ex.printStackTrace();
        }catch(UnknownHostException ex){
            ex.printStackTrace();
        }

        if(server != null){
            while(true){
//                TFTPPacket packet;
//                try{
//                    packet = server.receivePacket(1000);
//
//                    ReadRequestPacket rPacket = (ReadRequestPacket) packet;
//                    String url = rPacket.getUrl();
//
//                    byte[] fileData = FileFetcher.getFileBytes(url);
//
//                    boolean sending = true;
//
//                    byte[] blockNumber = {(byte)0, (byte)0};
//                    byte[] blockData;
//                    int offset = 0;
//                    while(sending){
//                        int size = 512;
//                        if(fileData.length - offset < 512){
//                            blockData = new byte[fileData.length - offset];
//                            size = fileData.length - offset;
//                        }else{
//                            blockData = new byte[512];
//                        }
//
//                        System.arraycopy(fileData, offset, blockData, offset, size);
//
    //                        DataPacket dataPacket = new DataPacket(rPacket.getAddress(), 2691, 3, blockNumber, blockData);
//                        server.sendPacket(dataPacket, false);
//
//                        Thread.sleep(100);
//                        packet = server.receivePacket(10000);
//                        if(packet.getType() == 4){
//                            AckPacket ackPacket = (AckPacket) packet;
//                            if(ackPacket.getBlockNumber().equals(blockNumber)){
//                                System.out.println("We have acknoledgement, lest do it again.");
//                                sending = false;
//                            }
//
//                        }
//
//                    }
//
//
//                    System.out.println(packet.toString());
//                }catch(IOException ex){
//                    ex.printStackTrace();
//                }

                TFTPPacket packet;
                try {
                    System.out.println("Waiting for a packet");
                    packet = server.receivePacket(0);
                    System.out.println("Got a packet");

                    if (packet.getType() == DataPacket.PACKET_TYPE) {
                        // use the PageFetcherManager to add requests to a
                        // thread pool
                        DataPacket rPack = (DataPacket) packet;
                        System.out.println(rPack.toString());

                    }


                    // check to see if its a request packet
                    if (packet.getType() == ReadRequestPacket.PACKET_TYPE) {
                        // use the PageFetcherManager to add requests to a
                        // thread pool
                        ReadRequestPacket rPack = (ReadRequestPacket) packet;
                        System.out.println(rPack.toString());


                        String url = rPack.getUrl();
//
                    byte[] fileData = FileFetcher.getFileBytes(url);

                    byte[] blockNumber = {(byte)0, (byte)0};

                    byte[] blockData = new byte[10];

                    System.arraycopy(fileData, 0, blockData, 0, 10);
                    DataPacket dataPacket = new DataPacket(rPack.getAddress(), 2691, 3, blockNumber, blockData);
                        for(byte b : blockData){
                            System.out.print(b + ", ");
                        }

                        System.out.println(dataPacket.toString());
                        System.out.println(dataPacket.getDatagramPacket().getAddress());
                        server.sendPacket(dataPacket, false);
//                        pfm.addToPacketsQueue((ReadRequestPacket) packet);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }else{
            System.out.println("SERVER INITIALIZATION FAILED");
        }
    }
}
