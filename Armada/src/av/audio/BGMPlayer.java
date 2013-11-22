package av.audio;

import java.net.URISyntaxException;
import java.net.URL;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


/**
 * 
 * @author Yun Suk Chang
 * 
 * Class used to play large music file (mainly for BGM)
 *
 */
public class BGMPlayer{

	private MediaPlayer player;
	/**
	 * Default constructor for BGMPlayer
	 * loads the music file
	 */
	public BGMPlayer(){
		//Gets the music from the bin
		
		URL url = this.getClass().getClassLoader().getResource("sound/"+"Gravity.mp3");
		try {
			new JFXPanel();
			if (url != null) player = new MediaPlayer(new Media(url.toURI().toString()));
			if (player != null) player.setCycleCount(MediaPlayer.INDEFINITE);
			else System.out.println("Music could not be found!");
		} catch (Exception e) {
			System.out.println("Music could not be found!");
			e.printStackTrace();
		}
		
	}
	/**
	 * Default constructor for BGMPlayer
	 * loads the music file with given name
	 * @param musicName name of the music file (with its property)
	 */
	public BGMPlayer(String musicName){
		URL url = this.getClass().getClassLoader().getResource("sound/"+musicName);
		try {
			new JFXPanel();
			if (url != null) player = new MediaPlayer(new Media(url.toURI().toString()));
			if (player != null) player.setCycleCount(MediaPlayer.INDEFINITE);
			else System.out.println("Music could not be found!");
		} catch (Exception e) {
			System.out.println("Music could not be found!");
			e.printStackTrace();
		}
	}
	/**
	 * changes the music to given name
	 * @param musicName name of the music file (with its property)
	 */
	public void changeBGM(String musicName){
		player = null;
		System.gc();
		URL url = this.getClass().getClassLoader().getResource("sound/"+musicName);
		try {
			player = new MediaPlayer(new Media(url.toURI().toString()));
			player.setCycleCount(MediaPlayer.INDEFINITE);
		} catch (Exception e) {
			System.out.println("Music could not be found!");
			e.printStackTrace();
		}
	}
	/**
	 * plays the music
	 */
	public void play(){
	    if (player != null)
		    player.play();
	}
	/**
	 * mutes the music
	 */
	public void mute(){
		player.setMute(true);
	}
	/**
	 * unmutes the music
	 */
	public void unMute(){
		player.setMute(false);
	}
	/**
	 * toggles between play() and stop()
	 */
	public void toggleStop(){
		if(player.getCurrentTime().toSeconds() > 0){
			player.stop();
		}
		else{
			player.play();
		}
	}
	/**
	 * pauses the music
	 */
	public void pause(){
		player.pause();
	}
	/**
	 * stops the music
	 */
	public void stop(){
		player.stop();
	}
}

