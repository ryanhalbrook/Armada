import java.awt.event.*;
import javax.swing.*;
import java.awt.*;

public class ViewLayerController implements MouseListener, MouseMotionListener {
    protected ViewLayer viewLayer = null;
    public ViewLayerController(ViewLayer vl) {
        this.viewLayer = vl;
    }
    public void refresh() {
        viewLayer.refresh();
    }
    public void setViewLayer(ViewLayer vl) {
        this.viewLayer = vl;
    }
    
    public ViewLayer getViewLayer() {
        return viewLayer;
    }
    
    public void mouseClicked(MouseEvent evt) {
        viewLayer.click(evt.getX(), evt.getY());
    }
	public void mouseEntered(MouseEvent evt) {}
	public void mouseExited(MouseEvent evt) {}
	public void mouseReleased(MouseEvent evt) {}
	public void keyTyped(KeyEvent evt) {}
	public void mouseDragged(MouseEvent evt) {}
	public void mouseMoved(MouseEvent evt) {}
	public void mousePressed(MouseEvent evt) {}
}