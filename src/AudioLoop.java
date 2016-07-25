import java.io.BufferedInputStream;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;


public class AudioLoop implements Runnable {
	public static final int EXTERNAL_BUFFER_SIZE = 128000;
	AudioInputStream stream;
	
	private AudioLoop(AudioInputStream stream){
		this.stream = stream;
	}
	
	@Override
	public void run() {
		play();
	}
	
	public void play(){
		AudioFormat format = stream.getFormat();
		SourceDataLine line = null;
		DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
		
		//Open up the audio line
		try{
			line = (SourceDataLine)AudioSystem.getLine(info);
			line.open(format);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		line.start();
		
		//Start reading in the bytes
		int nBytesRead = 0;
		byte[] abData = new byte[EXTERNAL_BUFFER_SIZE];
		while (nBytesRead != -1){
			//Attempt to read the stream in
			try {
				nBytesRead = stream.read(abData, 0, abData.length);
			} catch (IOException e){
				e.printStackTrace();
			}
			
			//Now write it out to the line
			if (nBytesRead > 0){
				line.write(abData, 0, nBytesRead);
			}
		}
		
		//Flush it
		line.drain();
		line.close();
	}
	
	public static AudioLoop createLoop(String filename){
		
		try {
			BufferedInputStream input = new BufferedInputStream(AudioLoop.class.getResourceAsStream(filename));
			AudioInputStream stream = AudioSystem.getAudioInputStream(input);
			return new AudioLoop(stream);
		} catch (IOException e){
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
