package us.hcheng.javaio.learnhspedu.chapters.chapter21;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.stream.Stream;

public class InetAddressClient {

	public static void main(String[] args) throws UnknownHostException {
		InetAddress localHost = InetAddress.getLocalHost();
		System.out.println("getHostAddress: " + localHost.getHostAddress());
		System.out.println("getCanonicalHostName: " + localHost.getCanonicalHostName());
		System.out.println("getHostName: " + localHost.getHostName());

		System.out.println(InetAddress.getByName(localHost.getHostName()));
		System.out.println(InetAddress.getByName("www.yahoo.com"));

		Stream.of("www.google.com", "www.baidu.com").forEach(s -> {
			try {
				Arrays.stream(InetAddress.getAllByName(s)).forEach(System.out::println);
			} catch (UnknownHostException ex) {
				ex.printStackTrace();
			}
		});
	}

}
