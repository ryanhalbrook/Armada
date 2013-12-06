package view.hud; 
import view.*;
import view.controller.GameController;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Font;

/**
 * Displays the current turn, money of current Player, and time left for turn.
 * @author Aaron
 *
 */
public class TurnHUD extends HUD{

	private Color player1Color = new Color(1.0f, 0.0f, 0.0f, 0.5f);
    private Color player2Color = new Color(0.0f, 0.0f, 1.0f, 0.5f);
	static int BAR_HEIGHT= 40;
	private float transitionPhase = 1.0f;
	public static final int TRANSITION_MILLISECONDS = 700;
	String lastText = null;
	Grid grid = null;
	 
	public TurnHUD(Grid gr, GameController gc) {
	super(new BoundingRectangle(0, 0, 2000, BAR_HEIGHT), gc);
	    this.grid = gr;
		
	}
	
	public void refresh(long previousTime, long currentTime) {
	    int delta = (int)(currentTime - previousTime);
	    float step = delta / (TRANSITION_MILLISECONDS * 1.0f);
	    if (transitionPhase > 0.0f) {
	        transitionPhase -= step;
	    }
	    if (transitionPhase < 0.0f) {
	        transitionPhase = 0.0f;
	    }
	}
	
	public void showTransition() {
	    transitionPhase = 1.0f;
	    
	}
	
	public void draw(Graphics g){
	
	int dx = -(int)(transitionPhase * grid.getAp().getWidth());
	int dy = -(int)(transitionPhase * BAR_HEIGHT);
	    /*int x = r.getX();
	    int y = r.getY();
	    int width = r.getWidth();
	    int height = r.getHeight();*/
		Color currentPlayerColor = Color.WHITE;
		Color previousPlayerColor = Color.WHITE;
	    String playerName = "";
	    if (grid.getTurn() == 1) {
	        currentPlayerColor = player1Color;
	        previousPlayerColor = player2Color;
	        playerName = "Player 1";
	    } else {
	        currentPlayerColor = player2Color;
	        previousPlayerColor = player1Color;
	        playerName = "Player 2";
	    }
		
		g.setColor(previousPlayerColor);
		g.fillRect(0, 0-dy-BAR_HEIGHT, grid.getAp().getWidth(), BAR_HEIGHT);
		g.setColor(currentPlayerColor);
		g.fillRect(0, 0-dy, grid.getAp().getWidth(), BAR_HEIGHT);
		
		
		// Draw time bar
		double time = grid.secondsRemainingForTurn();
		//int w = (int)Math.round(grid.getAp().getWidth() * time / grid.maxSecondsForTurn());
		int h = (int)Math.round(BAR_HEIGHT * time/ grid.maxSecondsForTurn());
        g.setColor(new Color(0.9f, 0.9f, 0.9f, 0.6f));
        g.fillRect(grid.getAp().getWidth() - 15, 0-dy, 10, BAR_HEIGHT);
        g.setColor(new Color(currentPlayerColor.getRed() / 255.0f, currentPlayerColor.getGreen() / 255.0f, currentPlayerColor.getBlue() / 255.0f, 0.9f));
        for(int i = BAR_HEIGHT; i > BAR_HEIGHT - h - dy; i--) {
            if (i % 2 == 0)
            g.fillRect(grid.getAp().getWidth() - 15, i-dy, 10, 1);
        }
        //g.fillRect(grid.getAp().getWidth() - 15, BAR_HEIGHT - h-dy, 10, h);
        //g.fillRect(0, BAR_HEIGHT - (int)(BAR_HEIGHT * 1/10), w, (int)(BAR_HEIGHT * 1/10));
        Font f = g.getFont();
        g.setFont(new Font(null, Font.PLAIN, 16));
        
		g.setColor(Color.WHITE);
		FontMetrics fm = g.getFontMetrics();
		String displayString = "";
		
		if(Math.round(time/1000)%60<10)
			displayString+="Turn Timer: " + Math.round(time/1000)/60+ ":0" +Math.round(time/1000)%60 + "     ";
		else
			displayString+="Turn Timer: " + Math.round(time/1000)/60+ ":" +Math.round(time/1000)%60 + "     ";
		
		int textWidth = fm.stringWidth(displayString);
		g.drawString(displayString, grid.getAp().getWidth() - 5 - textWidth, 25-dy);
		g.drawString(playerName + " | Money: " + grid.getPlayerManager().getPlayerMoney(grid.getTurn()), 10, 25-dy);
		/*
		if(grid.getActiveE()!=null){
			g.setColor(Color.WHITE);
			g.drawString("1-Move | 2-Attack Hull | 3-Attack Engines | 4-Dock", 5, 25);
		}
		*/
		g.setColor(Color.BLACK);
		g.drawRect(0, 0, grid.getAp().getWidth()-1, BAR_HEIGHT-dy);
		g.setFont(f);
	}

}
