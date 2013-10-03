import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class MainMenuPanel extends JPanel implements ActionListener{
    JButton startButton = new JButton("Start Game");
    JButton exitButton = new JButton("Quit Game");
    ApplicationManager am;
    Game game;
    
    public MainMenuPanel(ApplicationManager am) {
    this.am = am;
        startButton.addActionListener(this);
        exitButton.addActionListener(this);
        this.add(startButton);
        this.add(exitButton);
        this.setBackground(Color.BLACK);
    }
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == startButton) {
            am.startGame();
        } else if (evt.getSource() == exitButton) {
            System.exit(0);
        }
    }
}