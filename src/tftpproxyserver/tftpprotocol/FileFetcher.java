package tftpproxyserver.tftpprotocol;

import java.net.*;
import java.io.*;

public class FileFetcher {
    public static byte[] getFileBytes(String url) {

        try {
            URL fileUrl = new URL(url);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            try {
                byte[] chunk = new byte[4096];
                int bytesRead;
                InputStream stream = fileUrl.openStream();

                while ((bytesRead = stream.read(chunk)) > 0) {
                    outputStream.write(chunk, 0, bytesRead);
                }

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            return outputStream.toByteArray();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
