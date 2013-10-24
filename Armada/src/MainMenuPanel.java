import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;

/**
A class representing the main menu of the system.
*/
public class MainMenuPanel extends JPanel implements ActionListener{
    ApplicationManager am;
    JButton startButton = new JButton("Start Local 2 Player Game");
    JButton exitButton = new JButton("Quit Game");
    
    BufferedImage backgroundImage;
    static final String IMAGE_NAME = "ArmadaBackground";
    
    public MainMenuPanel(ApplicationManager am) {
        this.am = am;
        
        // Setup the buttons
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        JPanel buttonsPanel = new JPanel();
        this.setLayout(new BorderLayout());
        buttonsPanel.add(startButton);
        buttonsPanel.add(exitButton);
        buttonsPanel.setBorder(new EmptyBorder(10,400,250,10));
        buttonsPanel.setOpaque(false);
        this.add(buttonsPanel, BorderLayout.SOUTH);
        
        // Load the background image
        File f = new File(IMAGE_NAME+ ".jpg");
        
        backgroundImage = ImageLoader.getImage(IMAGE_NAME+".jpg");
        if (backgroundImage == null) System.out.println("Failed to load main menu image");
        /*
        backgroundImage = loadImage(f);
        if (backgroundImage == null) {
            backgroundImage = loadImage(new File("src/"+IMAGE_NAME+".jpg"));
        }
        */
    }
    
    // Respond to button clicks here.
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == startButton) {
            am.startGame();
        } else if (evt.getSource() == exitButton) {
            System.exit(0);
        }
    }
    
    // Draws the background image.
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        AffineTransform at = g2d.getTransform();
        if (backgroundImage != null) {
            int imageWidth = backgroundImage.getWidth();
            int panelWidth = this.getWidth();
            
            double scale = panelWidth / (imageWidth * 1.0);
            g2d.scale(scale, scale);
            g2d.drawImage(backgroundImage, 0, 0, null);
        } else {
            System.out.println("Repainting");
            //repaint();
        }
        g2d.setTransform(at);
    }
    
    private static BufferedImage loadImage(File f) {
        BufferedImage bi = null;
        
        /*
        try {
            bi = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Failed to load main menu image");
        }
        */
        return bi;
        
    }
}