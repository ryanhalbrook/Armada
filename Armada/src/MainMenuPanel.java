import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.awt.geom.*;

public class MainMenuPanel extends JPanel implements ActionListener{
    JButton startButton = new JButton("Start Game");
    JButton exitButton = new JButton("Quit Game");
    ApplicationManager am;
    BufferedImage backgroundImage;
    Game game;
    
    public MainMenuPanel(ApplicationManager am) {
    this.am = am;
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        this.add(startButton);
        this.add(exitButton);
        //this.setBackground(Color.BLACK);
        File f = new File("ArmadaLogo.jpg");
        backgroundImage = loadImage(f);
    }
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == startButton) {
            am.startGame();
        } else if (evt.getSource() == exitButton) {
            System.exit(0);
        }
    }
    
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
            repaint();
        }
        g2d.setTransform(at);
    }
    
    private BufferedImage loadImage(File f) {
        BufferedImage bi = null;
        try {
            bi = ImageIO.read(f);
        } catch (IOException e) {
            System.out.println("Failed to load main menu image");
        }
        return bi;
        
    }
}