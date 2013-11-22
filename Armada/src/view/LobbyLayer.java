package view;
import view.*;
import java.awt.*;
public class LobbyLayer extends ViewLayer {
    public LobbyLayer(BoundingRectangle r) {
        super(r);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(0.1f, 0.1f, 0.5f, 1.0f));
        g.fillRect(0, 0, r.getWidth(), r.getHeight());
        g.setColor(Color.WHITE);
        g.setFont(new Font(null, Font.PLAIN, 16));
        g.drawString("Waiting for a client to connect. Press escape to cancel.", 250, 200);
    }
}