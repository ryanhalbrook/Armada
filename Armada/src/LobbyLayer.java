package src; import src.view.*;
import java.awt.*;
public class LobbyLayer extends ViewLayer {
    public LobbyLayer(BoundingRectangle r) {
        super(r);
    }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(0, 0, r.getWidth(), r.getHeight());
        g.setColor(Color.BLACK);
        g.drawString("Waiting for a client to connect. Press escape to cancel.", 500, 200);
    }
}