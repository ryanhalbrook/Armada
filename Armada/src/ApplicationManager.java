import java.awt.*;
import javax.swing.*;

public class ApplicationManager {
    JPanel mainPanel;
    JFrame window = new JFrame();
    GameManager gm = null;
    
    static final int DEFAULT_WINDOW_WIDTH = 1000;
    static final int DEFAULT_WINDOW_HEIGHT = 700;
    
    static final int MIN_WINDOW_WIDTH = 700;
    static final int MIN_WINDOW_HEIGHT = 500;
    
    public ApplicationManager() {
        mainPanel = new MainMenuPanel(this);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        gm = null;
        swapPanel(new MainMenuPanel(this));
    }
    
    private void swapPanel(JPanel p) {
        if (p == null) return;
        
        window.remove(mainPanel);
        window.add(p);
        window.pack();
        enforceDefaultWindowSize();
    }
    
    private void enforceDefaultWindowSize() {
        if (window != null) window.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    }
    
}