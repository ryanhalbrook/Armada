
import java.net.URISyntaxException;
import java.net.URL;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;



public class BGMPlayer{

	private MediaPlayer player;
	
	public BGMPlayer(){
		//Gets the music from the bin
		URL url = this.getClass().getClassLoader().getResource("sound/"+"Gravity.mp3");
		try {
			new JFXPanel();
			player = new MediaPlayer(new Media(url.toURI().toString()));
			player.setCycleCount(MediaPlayer.INDEFINITE);
		} catch (Exception e) {
			System.out.println("Music could not be found!");
			e.printStackTrace();
		}
		
	}
	public BGMPlayer(String musicName){
		URL url = this.getClass().getClassLoader().getResource("sound/"+musicName);
		try {
			new JFXPanel();
			player = new MediaPlayer(new Media(url.toURI().toString()));
			player.setCycleCount(MediaPlayer.INDEFINITE);
		} catch (Exception e) {
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
			player.setCycleCount(MediaPlayer.INDEFINITE);
		} catch (Exception e) {
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
	public void toggleStop(){
		if(player.getCurrentTime().toSeconds() > 0){
			player.stop();
		}
		else{
			player.play();
		}
	}
	public void pause(){
		player.pause();
	}
	public void stop(){
		player.stop();
	}
}

