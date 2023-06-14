package us.hcheng.javaio.learnhspedu.chapters.chapter21.util;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PlayWav extends Thread {
	private InputStream inputStream;
	private String filename;

	public PlayWav(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public PlayWav(String filename) {
		this.filename = filename;
	}

	public void run() {
		AudioInputStream inputStream = null;
		try {
			if (inputStream != null)
				inputStream = AudioSystem.getAudioInputStream(this.inputStream);
			else
				inputStream = AudioSystem.getAudioInputStream(new File(filename));
		} catch (UnsupportedAudioFileException | IOException ex){}
		if (inputStream == null)
			return;

		try (SourceDataLine auline = (SourceDataLine) AudioSystem.getLine(new DataLine.Info(SourceDataLine.class, inputStream.getFormat()))) {
			auline.open(inputStream.getFormat());
			auline.start();
			byte[] abData = new byte[512];
			for (int nBytesRead = -1; (nBytesRead = inputStream.read(abData, 0, abData.length)) != -1; )
				auline.write(abData, 0, nBytesRead);
		} catch (IOException | LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}

}