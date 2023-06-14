package us.hcheng.javaio.learnhspedu.chapters.chapter21.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import java.io.InputStream;

public class PlayWav extends Thread {
	private InputStream inputStream;

	public PlayWav(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public void run() {
		try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(this.inputStream);
             SourceDataLine auline = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, inputStream.getFormat()))) {
			auline.open(inputStream.getFormat());
			auline.start();
			byte[] abData = new byte[512];
			for (int nBytesRead = -1; (nBytesRead = inputStream.read(abData, 0, abData.length)) != -1; )
				auline.write(abData, 0, nBytesRead);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}