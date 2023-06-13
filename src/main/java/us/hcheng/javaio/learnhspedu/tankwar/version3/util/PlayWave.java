package us.hcheng.javaio.learnhspedu.tankwar.version3.util;

import javax.sound.sampled.*;
import java.io.File;

public class PlayWave extends Thread {
	private String filename;

	public PlayWave(String filename) {
		this.filename = filename;
	}

	public void run() {
		try (AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(PlayWave.class.getResource(filename).toURI()));
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