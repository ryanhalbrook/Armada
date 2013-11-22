package view.hud;

import view.*;
import view.controller.GameController;
import view.hud.HUD.Position;
import game.player.Player;

import java.awt.*;
public class ArmadaHUD extends HUD {
    private GameController gc = null;
    private Player player = null;

    public ArmadaHUD(BoundingRectangle b, GameController gc) {
        super(b, gc, HUD.Position.CENTERED);
        this.gc = gc;
    }
    
    public void showForPlayer(Player p) {
        if (p == null) return;
        player = p;
        
    }
    
    public void draw(Graphics g) {
        updateLocation();
        g.setColor(new Color(0.1f, 0.1f, 0.1f, 0.7f));
        g.fillRect(r.getX(), r.getY(), r.getWidth(), r.getHeight());
    
    }
    
}