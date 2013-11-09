import java.awt.*;
import javax.swing.*;

/**
Responsible for switching between the different panels of the application.
Currently switches between the main menu and the game.
*/
public class ApplicationManager {

    static final int DEFAULT_WINDOW_WIDTH = 960;
    static final int DEFAULT_WINDOW_HEIGHT = 540;
    
    static final int MIN_WINDOW_WIDTH = 700;
    static final int MIN_WINDOW_HEIGHT = 500;
    
    /** The JPanel that should be currently displayed. */
    private JPanel mainPanel;
    /** The main window for this application */
    private JFrame window = new JFrame();
    
    /**
        Constructs an ApplicationManager object. Does NOT make the window show onscreen.
        Call start to do this.
        @see start
    */
    public ApplicationManager() {
        
        mainPanel = new MainMenuPanel(this);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Armada");
        window.add(mainPanel);
        enforceDefaultWindowSize();
        window.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
    }
    
    /**
        Makes the main application window visible on the screen.
    */
    public void start() {
        window.setVisible(true);
    }
    
    /**
        Switches to displaying a panel with a new game.
    */
    public void startGame() {
        ArmadaEngine engine = new ArmadaEngine();
        swapPanel(new ViewLayerPanel(new ViewLayerController(new ViewLayer(new BoundingRectangle(0, 0, 1000, 1000)))));
    }
    
    /**
        Switches back to the main menu panel.
    */
    public void endGame() {
        swapPanel(new MainMenuPanel(this));
    }
    
    /**
        Swaps the current panel for the panel given to this method.
        @param p The panel that will be switched to.
    */
    private void swapPanel(JPanel p) {
        if (p == null) return;
        
        window.remove(mainPanel);
        mainPanel = null;
        mainPanel = p;
        window.add(mainPanel);
        window.validate();
        mainPanel.requestFocus();
    }
    
    /**
        Sets the window size to the default window size.
    */
    private void enforceDefaultWindowSize() {
        if (window != null) window.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    }
    
}