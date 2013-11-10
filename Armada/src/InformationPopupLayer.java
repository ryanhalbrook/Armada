import java.awt.*;
public class InformationPopupLayer extends ViewLayer {
    private String text;
    private boolean fadingIn = false;
    private boolean fadingOut = true;
    private float phase = 1.0f;
    static final int PHASE_TIME = 3000;
    public InformationPopupLayer(BoundingRectangle b) {
        super(b);
        
    }
    
    public void showPopup(String text) {
        this.text = text;
        phase = 0.0f;
    }
    
    public void refresh(long previousTime, long currentTime) {
        //System.out.println("Refresh");
        int delta = (int)(currentTime - previousTime);
        float step = delta / (PHASE_TIME * 1.0f);
        if (phase < 1.0f) phase += step;
        System.out.println("Phase: " + phase);
    }
    
    public boolean click(int x, int y) { return false; }
    
    public void draw(Graphics g) {
        if (phase > 0.99f) return;
        float alpha = 1.0f;
        if (phase < 0.2f) {
            alpha = phase * 5.0f;
        } else if (phase > 0.8f) {
            alpha = (1 - phase) * 5.0f;
        }
        g.setColor(new Color(0.0f, 0.9f, 1.0f, alpha));
        g.fillRect(this.r.getX(), this.r.getY(), this.r.getWidth(), this.r.getHeight());
        g.setColor(Color.BLACK);
        g.drawString(text, this.r.getX(), this.r.getY()+ this.r.getHeight());
    }
}