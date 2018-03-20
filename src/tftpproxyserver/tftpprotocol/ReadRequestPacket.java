package tftpproxyserver.tftpprotocol;

import java.net.DatagramPacket;
import java.net.InetAddress;

public class ReadRequestPacket extends TFTPPacket {
    public static final int PACKET_TYPE = 1;

    private String url;
    private String mode = "octet";


    public ReadRequestPacket(InetAddress address, int port, int type, String url) {
        super(address, port, type);
        this.url = url;
    }

    public ReadRequestPacket(DatagramPacket packet, int type) {
        super(packet, type);

        byte[] data = packet.getData();

        int modeOffset = -1;

        StringBuffer buffer = new StringBuffer();

        //start after the 2 bytes opcode and read in the url
        for(int i = 2; i < packet.getLength(); i++){
            //we want to stop when we hit the 0
            if(data[i] == 0){
                modeOffset = i + 1;
                break;
            }
            buffer.append((char)data[i]);
        }

        //set the filenmae from the StringBuffer
        url = buffer.toString();

        if(modeOffset == -1){
            System.out.println("Incorrect Format!");
        }

        buffer = new StringBuffer();
        for(int i = modeOffset; i < packet.getLength(); i++){
            if(data[i] == 0) break;
            buffer.append((char)data[i]);
        }

        //now we have the mode even though we really don't need it for this assignment
        this.setMode(buffer.toString());
    }

    @Override
    public DatagramPacket getDatagramPacket(){
        byte[] modeBytes = mode.getBytes();

        // allocate space for header
        byte[] data = new byte[2 + this.url.length() + 1 + modeBytes.length + 1];

        data[0] = 0; //byte 1 -opcode
        data[1] = (byte)this.getType(); //byte 2 -opcode

        System.arraycopy(this.url.getBytes(), 0, data, 2, this.url.length()); //append the filename after the opcode

        data[this.url.length() + 2] = 0; // 1 byte after url is 0

        System.arraycopy(modeBytes, 0, data, 2 + this.url.length() + 1, modeBytes.length); //append the mode

        data[2 + this.url.length() + 1 + modeBytes.length] = 0; //1 byte after mode is 0

        return new DatagramPacket(data, data.length, this.getAddress(), this.getPort());
    }

    public void setMode(String mode){this.mode = mode;}

    public String getUrl(){return this.url;};
}
