import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;


public class BGMPlayer{

	private MediaPlayer player;
	
	public BGMPlayer(){
		URL url = this.getClass().getClassLoader().getResource("sound/"+"Gravity.mp3");
		try {
			new JFXPanel();
			Media m = new Media(url.toURI().toString());
			player = new MediaPlayer(m);
			player.setCycleCount(MediaPlayer.INDEFINITE);
		} catch (URISyntaxException e) {
			System.out.println("Music could not be found!");
			e.printStackTrace();
		}
		
	}
	public BGMPlayer(String musicName){
		URL url = this.getClass().getClassLoader().getResource("sound/"+musicName);
		try {
			
			player = new MediaPlayer(new Media(url.toURI().toString()));
		} catch (URISyntaxException e) {
			System.out.println("Music could not be found!");
			e.printStackTrace();
		}
	}
	
	public void changeBGM(String musicName){
		player = null;
		System.gc();
		URL url = this.getClass().getClassLoader().getResource("sound/"+musicName);
		try {
			player = new MediaPlayer(new Media(url.toURI().toString()));
		} catch (URISyntaxException e) {
			System.out.println("Music could not be found!");
			e.printStackTrace();
		}
	}
	public void play(){
		player.play();
	}
	public void mute(){
		player.setMute(true);
	}
	public void unMute(){
		player.setMute(false);
	}
	public void pause(){
		player.pause();
	}
	public void stop(){
		player.stop();
	}
}

