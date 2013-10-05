import java.awt.*;
import javax.swing.*;
public class ApplicationManager {
    JPanel mainPanel;
    JFrame window = new JFrame();
    GameManager gm = null;
    boolean gameActive = false;
    
    public ApplicationManager() {
        mainPanel = new MainMenuPanel(this);
        
    }
    
    public void init() {
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1000,700);
        window.add(mainPanel);
        window.setVisible(true);
        window.setMinimumSize(new Dimension(700,500));
    }
    
    public void startGame() {
        gm = new GameManager();
        window.remove(mainPanel);
        mainPanel = new ArmadaPanel(this,gm);
        //ImageIcon img = new ImageIcon("space.png");
		//JLabel j = new JLabel(img);
		//mainPanel.add(j);
		window.add(mainPanel);
		//int w = window.getWidth();
		//int h = window.getHeight();
		window.repaint();
		
		//window.setSize(w,h);
    }
    
    public void endGame() {
        gm = null;
        window.remove(mainPanel);
        mainPanel = new MainMenuPanel(this);
        window.add(mainPanel);
        window.pack();
        window.setSize(1000,700);
    }
    
}