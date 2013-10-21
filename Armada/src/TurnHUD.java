import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;


public class TurnHUD extends HUD{

	private Color player1Color = new Color(1.0f, 0.0f, 0.0f, 0.5f);
    private Color player2Color = new Color(0.0f, 0.0f, 1.0f, 0.5f);
	static int BAR_HEIGHT= 20;
    
	public TurnHUD(Grid gr) {
		super(0, 0, 2000, BAR_HEIGHT, gr);
		// TODO Auto-generated constructor stub
	}
	
	public void draw(Graphics g){
		Color currentPlayerColor = Color.WHITE;
	    String playerName = "";
	    if (grid.getTurn() == 1) {
	        currentPlayerColor = player1Color;
	        playerName = "Player 1";
	    } else {
	        currentPlayerColor = player2Color;
	        playerName = "Player 2";
	    }
		
		g.setColor(currentPlayerColor);
		g.fillRect(0, 0, grid.getAp().getWidth(), BAR_HEIGHT);
		g.setColor(Color.WHITE);
		FontMetrics fm = g.getFontMetrics();
		int textWidth = fm.stringWidth(playerName);
		g.drawString(playerName, grid.getAp().getWidth() - 5 - textWidth, 15);
		
		if(grid.getActiveE()!=null){
			g.setColor(Color.WHITE);
			g.drawString("1-Move | 2-Attack Hull | 3-Attack Engines | 4-Unselect", 5, 15);
		}
	}

}
