package us.hcheng.javaio.learnhspedu.chapters.chapter21.util;

import java.io.*;
import java.net.Socket;

public class IOUtils {

    public static String inputStreamToString(InputStream is) {
        try {
            StringBuilder sb = new StringBuilder();
            byte[] buf = new byte[2048];
            for (int len = -1; (len = is.read(buf)) != -1; )
                sb.append(new String(buf, 0, len));
            return sb.toString();
        } catch (IOException ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 1. for stream to signal EOF, it needs socket.shutdownOutput()
     * 2. OutputStream cannot close() here becuase it will close the socket
     * 3. it needs to call OutputStream.flush() to push message over
     */
    public static void pushStreamString(Socket socket, OutputStream os, String msg) {
        try {
            os.write(msg.getBytes());
            os.flush();
            socket.shutdownOutput();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void pushBWString(BufferedWriter bw, String s) {
        try {
            bw.write(s); // or append System.lineSeparator() or include a line break
            bw.newLine();
            bw.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void pushByteData(Socket socket, ByteArrayOutputStream bos, byte[] data) {
        try {
            bos.write(data);
            bos.flush();
            socket.shutdownOutput();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] readByteData(ByteArrayInputStream bis) {
        return bis.readAllBytes();
    }

}
