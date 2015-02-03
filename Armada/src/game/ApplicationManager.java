package game;

import view.*;
import view.controller.GameController;
import view.controller.LobbyController;
import game.server.GameServer;
import game.server.HostServer;
import game.server.ProxyServer;
import java.awt.image.BufferedImage;

import java.awt.*;
import java.util.ArrayList;
import av.visual.ImageLoader;
import javax.swing.*;
/**
Responsible for switching between the different panels of the application.
Currently switches between the main menu and the game.
*/
public class ApplicationManager implements ChangeListener {

    static final int DEFAULT_WINDOW_WIDTH = 960;
    static final int DEFAULT_WINDOW_HEIGHT = 540;
    
    static final int MIN_WINDOW_WIDTH = 700;
    static final int MIN_WINDOW_HEIGHT = 500;
    
    GameServer gs = null;
    HostServer hs = null;
    ProxyServer ps = null;
    
    /** The JPanel that should be currently displayed. */
    private JPanel mainPanel;
    private ViewLayerPanel vp;
    /** The main window for this application */
    private JFrame window = new JFrame();
    static ApplicationManager INSTANCE;
    
    /**
    Get the instance of the ApplicationManager
    */
    public static ApplicationManager getInstance(){
    	if(INSTANCE==null){
    		INSTANCE=new ApplicationManager();
    	}
    	return INSTANCE;
    }
    
    /**
        Constructs an ApplicationManager object. Does NOT make the window show onscreen.
        Call start to do this.
        @see start
    */
    private ApplicationManager() {
        mainPanel = new MainMenuPanel(this);
	mainPanel.setBackground(Color.BLACK);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("Armada");
        window.add(mainPanel);
        enforceDefaultWindowSize();
        window.setMinimumSize(new Dimension(MIN_WINDOW_WIDTH, MIN_WINDOW_HEIGHT));
	GraphicsDevice gd =
            GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

	if (gd.isFullScreenSupported()) {
	    window.setUndecorated(true);
	    gd.setFullScreenWindow(window);
	    mainPanel.repaint();
	} else {
	    System.err.println("Full screen not supported");
	}
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
    public void startLocalGame() {
        BufferedImage img = null;
        
        ArmadaEngine engine = new ArmadaEngine();
        Spawner.spawnPlanets(engine, 7);
        ViewLayerPanel vlp = new ViewLayerPanel();
        GameController gc = new GameController(this, engine, vlp);
        vlp.setViewLayerController(gc);
        vlp.setAntialiasingEnabled(true);
        swapPanel(vlp);
    }
    
    /**
    Start a networked game as a host.
    */
    public void startNetworkedGame() {
        hs = new HostServer();
        gs = hs;
        hs.setConnectionListener(this);
        ViewLayerPanel vp = new ViewLayerPanel();
        
        LobbyController lc = new LobbyController(this, vp);
        vp.setViewLayerController(lc);
        //vp.setAntialiasingEnabled(true);
        swapPanel(vp);
    }
    
    /**
    Called when networking has been set up.
    */
    public void changeOccurred() {
        ArmadaEngine engine = new ArmadaEngine(gs, 1);
        Spawner.spawnPlanets(engine, 7);
        ViewLayerPanel vlp = new ViewLayerPanel();
        GameController gc = new GameController(this, engine, vlp);
        vlp.setViewLayerController(gc);
        vlp.setAntialiasingEnabled(true);
        swapPanel(vlp);
    }
    /**
    Start a game connected to another instance that is a host.
    */
    public void connectToNetworkedGame() {
        ps = new ProxyServer("127.0.0.1");
        if (ps == null) System.out.println("ps is null");  
        ArmadaEngine engine = new ArmadaEngine(ps, 2);
        ViewLayerPanel vlp = new ViewLayerPanel();
        GameController gc = new GameController(this, engine, vlp);
        vlp.setViewLayerController(gc);
        vlp.setAntialiasingEnabled(true);
        swapPanel(vlp);
    }
    
    /**
    Shuts down the network connection.
    */
    public void shutdownConnection() {
        gs.disconnectNetwork();
        endGame();
    }
    
    /**
        Switches back to the main menu panel.
    */
    public void endGame() {
        swapPanel(new MainMenuPanel(this));
    }
    /**
    Ends the game, showing the win screen.
    */
    public void endGame(int alliance) {
    	if(alliance ==1)
    		swapPanel(new MainMenuPanel(this,"redwin.png"));
    	else if(alliance==2)
    		swapPanel(new MainMenuPanel(this,"bluewin.png"));
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
    /**
    Returns the window that displays this application.
    */
	public JFrame getWindow() {
		return window;
	}
    
}
