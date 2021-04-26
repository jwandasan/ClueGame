package clueGame;

import javax.sound.sampled.AudioSystem;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlayMusic {

	Long frame;
	Clip aClip;
	
	AudioInputStream audioStream;
	
	public PlayMusic() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		audioStream = AudioSystem.getAudioInputStream(new File("data/oldhoodies.wav").getAbsoluteFile());
		aClip = AudioSystem.getClip();
		aClip.open(audioStream);
		aClip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	
	public void play() {
		aClip.start();
	}
	
}
