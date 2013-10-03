import javax.swing.JFrame;

/*
 * Responsable for Main. 
 * 
 */

public class Game {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		//Frame f = new Frame();
        //ArmadaPanel p = new ArmadaPanel();
        //JFrame window = new JFrame("Armada");
        //window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setSize(500,500);
        ApplicationManager AM = new ApplicationManager();
        AM.init();
        //JFrame gameFrame = new JFrame("Armada");
        //gameFrame.add(new MainMenuPanel());
       // gameFrame.setSize(200, 200);
        //gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //gameFrame.setVisible(true);
	}
	
}
