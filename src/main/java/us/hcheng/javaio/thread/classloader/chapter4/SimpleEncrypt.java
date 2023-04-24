package us.hcheng.javaio.thread.classloader.chapter4;

import java.util.stream.IntStream;

public class SimpleEncrypt {

	private static final String plain = "Hello Classloader";
	private static final byte ENCRYPT_FACTOR = (byte) 0xff;

	public static void main(String[] args) {
		String name = "Moka";
		byte[] enc = doENCRYPT2(name.getBytes());

		System.out.println(name);   // original
		System.out.println(new String(enc));    // encrypt
		System.out.println(new String(doENCRYPT2(enc)));    // back to original
	}


	private static byte[] doENCRYPT2(byte[] bytes) {
		byte[] ret = new byte[bytes.length];
		IntStream.range(0, bytes.length).forEach(i->{
			ret[i] = (byte) (bytes[i] ^ ENCRYPT_FACTOR);
		});
		return ret;
	}

}
