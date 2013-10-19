import java.awt.*;
import javax.swing.*;

/**
Manages the overall state of the application. Responsible for switching between the game
screen and the main menu screen.
*/
public class ApplicationManager {
    JPanel mainPanel;
    JFrame window = new JFrame();
    GameManager gm = null;
    
    static final int DEFAULT_WINDOW_WIDTH = 960;
    static final int DEFAULT_WINDOW_HEIGHT = 540;
    
    static final int MIN_WINDOW_WIDTH = 700;
    static final int MIN_WINDOW_HEIGHT = 500;
    
    public ApplicationManager() {
        mainPanel = new MainMenuPanel(this);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Armada");
        window.add(mainPanel);
        enforceDefaultWindowSize();
        window.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
    }
    
    public void start() {
        window.setVisible(true);
    }
    
    public void startGame() {
        gm = new GameManager();
        swapPanel(new ArmadaPanel(this,gm));
    }
    
    public void endGame() {
        System.out.println("Ending game");
        gm = null;
        swapPanel(new MainMenuPanel(this));
    }
    
    private void swapPanel(JPanel p) {
        if (p == null) return;
        
        window.remove(mainPanel);
        mainPanel = null;
        mainPanel = p;
        window.add(mainPanel);
        window.pack();
        enforceDefaultWindowSize();
    }
    
    private void enforceDefaultWindowSize() {
        if (window != null) window.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    }
    
}