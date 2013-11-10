import java.awt.*;
public class InformationPopupLayer extends ViewLayer {
    private String text;
    private boolean fadingIn = false;
    private boolean fadingOut = true;
    private float phase = 1.0f;
    static final int PHASE_TIME = 3000;
    static final Color DEFAULT_COLOR = new Color(0.0f, 0.1f, 0.1f, 1.0f);
    private Color backgroundColor = DEFAULT_COLOR;
    //public static InformationPopupLayer ipl;
    /*
    public static InformationPopupLayer getInstance(){
    	if(ipl == null){
    		ipl = new InformationPopupLayer(new BoundingRectangle(0, 45, 200, 35));
    	}
    	return ipl;
    }
    */
    
    public InformationPopupLayer(BoundingRectangle b) {
        super(b);
    }
    
    public void showPopup(String text) {
        showPopup(text, null);
    }
    
    public void showPopup(String text, Color backgroundColor) {
        if (backgroundColor == null) {
            backgroundColor = DEFAULT_COLOR;
        } else {
            this.backgroundColor = backgroundColor;
        }
        this.text = text;
        phase = 0.0f;
    }
    
    public void refresh(long previousTime, long currentTime) {
        //System.out.println("Refresh");
        int delta = (int)(currentTime - previousTime);
        float step = delta / (PHASE_TIME * 1.0f);
        if (phase < 1.0f) phase += step;
    }
    
    public boolean click(int x, int y) { return false; }
    
    public void draw(Graphics g) {
    	if(text != null){
    		r.width=g.getFontMetrics().stringWidth(text) + 50;
    	}
        if (phase > 0.99f) return;
        float alpha = 1.0f;
        if (phase < 0.2f) {
            alpha = phase * 5.0f;
        } else if (phase > 0.8f) {
            alpha = (1 - phase) * 5.0f;
        }
        g.setColor(new Color(0.0f, 0.1f, 0.1f, alpha));
        g.fillRect(this.r.getX(), this.r.getY(), this.r.getWidth(), this.r.getHeight());
        g.setColor(new Color(1.0f, 1.0f, 1.0f, alpha));
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();
        int startX = this.r.getX() + (int)((this.r.getWidth() - textWidth) / 2.0);
        int startY = this.r.getY() + (int)((this.r.getHeight() - ((this.r.getHeight() - textHeight) / 2.0)));
        g.drawString(text, startX, startY);
    }
}