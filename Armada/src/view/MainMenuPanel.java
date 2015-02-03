package view;

import av.visual.ImageLoader;
import game.ApplicationManager;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.*;
import game.PreloadImageList;
import av.visual.ImageLoader;

import javax.imageio.ImageIO;

/**
A class representing the main menu of the system.
*/
public class MainMenuPanel extends JPanel implements ActionListener{
    private boolean launchingGame = false;
    ApplicationManager am;
    JButton startButton = new JButton("Start Game Old Way");
    JButton startButton2 = new JButton("Single Player");
    JButton exitButton = new JButton("Quit Game");
    JButton networkedGameHostButton = new JButton("New Multiplayer Host");
    JButton networkedGameClientButton = new JButton("Connect to Multiplayer Host");
    private String message = "";
    private float progress = 0.0f;
    BufferedImage backgroundImage;
    static final String IMAGE_NAME = "ArmadaBackground";
    /**
    Creates a new instance of MainMenuPanel.
    */
    public MainMenuPanel(ApplicationManager am) {
        this.am = am;
        
        // Setup the buttons
        startButton.addActionListener(this);
	startButton.setPreferredSize(new Dimension(500, 30));
        exitButton.addActionListener(this);
        startButton2.addActionListener(this);
        networkedGameHostButton.addActionListener(this);
        networkedGameClientButton.addActionListener(this);
        JPanel buttonsPanel = new JPanel();//new JPanel(new GridLayout(4,1));
	buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
        buttonsPanel.setPreferredSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout());
        buttonsPanel.add(startButton2);
        buttonsPanel.add(networkedGameHostButton);
        buttonsPanel.add(networkedGameClientButton);
        buttonsPanel.add(exitButton);
        
        //buttonsPanel.add(startButton);
        buttonsPanel.setBorder(new EmptyBorder(100,400,250,10));
        buttonsPanel.setOpaque(false);
	this.setLayout(null);
	int offsetx = 550;
	int offsety = 250;
	startButton2.setBounds(offsetx, offsety, 200, 25);
	exitButton.setBounds(offsetx, offsety + 30, 200, 25);
	this.add(startButton2);
	this.add(exitButton);
        
        // Load the background image
        File f = new File(IMAGE_NAME+ ".jpg");
        
        backgroundImage = ImageLoader.getInstance().getImage(IMAGE_NAME+".jpg");
        if (backgroundImage == null) System.out.println("Failed to load main menu image");
    }
    public MainMenuPanel(ApplicationManager am,String img) {
        this.am = am;
        
        
        // Setup the buttons
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        startButton2.addActionListener(this);
        networkedGameHostButton.addActionListener(this);
        networkedGameClientButton.addActionListener(this);
        JPanel buttonsPanel = new JPanel(new GridLayout(4,1));
        buttonsPanel.setPreferredSize(new Dimension(50, 500));
        this.setLayout(new BorderLayout());
        buttonsPanel.add(startButton2);
        buttonsPanel.add(networkedGameHostButton);
        buttonsPanel.add(networkedGameClientButton);
        buttonsPanel.add(exitButton);
        
        //buttonsPanel.add(startButton);
        buttonsPanel.setBorder(new EmptyBorder(100,400,250,10));
        buttonsPanel.setOpaque(false);
        this.add(buttonsPanel, BorderLayout.NORTH);
        
        // Load the background image
        File f = new File(IMAGE_NAME+ ".jpg");
        
        backgroundImage = ImageLoader.getInstance().getImage(img);
        if (backgroundImage == null) System.out.println("Failed to load main menu image");
    }
    // Respond to button clicks here.
    
    /**
    Handles button clicks.
    */
    public void actionPerformed(ActionEvent evt) {
        if (launchingGame) return;
        if (evt.getSource() == startButton2) {
            launchingGame = true;
            message = "Loading Images...";
            startButton2.setLabel(message);
            Thread resourceLoaderThread = new Thread(new ResourceLoader());
            resourceLoaderThread.start();
	    repaint();    
        } else if (evt.getSource() == networkedGameHostButton) {
            am.startNetworkedGame();
        } else if (evt.getSource() == networkedGameClientButton) {
            am.connectToNetworkedGame();
        } else if (evt.getSource() == exitButton) {
            System.exit(0);
        }
    }
    
    /**
    Draws the background image.
    */
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform at = g2d.getTransform();
        if (backgroundImage != null) {
            int imageWidth = backgroundImage.getWidth();
	    int imageHeight = backgroundImage.getHeight();
            int panelWidth = this.getWidth();
	    int panelHeight = this.getHeight();
	    double scale = 1.0; 
            if (panelWidth - imageWidth > panelHeight - imageHeight) {
		scale = panelWidth / (imageWidth * 1.0);
	    } else {
		scale = panelHeight / (imageHeight * 1.0);
	    }	
            g2d.scale(scale, scale);
            g2d.drawImage(backgroundImage, 0, 0, null);
            
        } else {
            System.out.println("Repainting");
            //repaint();
        }
	g2d.setColor(new Color(0.5f, 0.5f, 0.5f, 0.7f));
	System.out.println("Width: " + this.getWidth() + "; height: " + this.getHeight());
	
        g2d.setTransform(at);
	if (launchingGame) {
	g2d.fillRect(this.getWidth()/2 - 250, this.getHeight() - 100, 500, 50);
	g2d.setColor(Color.WHITE);
	System.out.println("P: " + progress);
	//progress = 1.0f;
	g2d.fillRect((int)(this.getWidth()/2 - (int)(500.0 * progress / 2.0f)), this.getHeight() - 100, (int)(500.0*progress), 50);
	//System.sleep(100);
	    try {
	    Thread.sleep(10);
	    } catch (Exception e) {
    
	    }
	    repaint();
	}
	
	
    }
    
    /**
    Preloads Resources.
    */
    private class ResourceLoader implements Runnable {
        public void run() {
        String[] list = PreloadImageList.getList();
            BufferedImage img = null;
            for (int i = 0; i < list.length; i++) {
		progress = (float)((i*1.0+1.0) / (1.0*list.length));
		
		System.out.println("Progress: " + progress);
                ImageLoader.getInstance().preloadImageAsync(list[i]);
                
                boolean quit = false;
                
                int count = 0;
                while(!quit) {
                    count++;
                    if (count > 49) {
                        System.out.println("Preloading an image timed out: " + list[i]);
                        quit = true;
                    }
                    try {
                        Thread.sleep(10);
                        if (ImageLoader.getInstance().imageIsLoaded(list[i])) quit = true;
                    } catch (Exception e) {
                        e.printStackTrace();
                        quit = true;
                    }
                }
               
            }
            am.startLocalGame(); 
        }
         
    }
}
