import java.util.ArrayList;
import java.awt.Graphics;
/**
Class representing a view layer on screen. Handles clicks and gets drawn. The order that you
add sublayers is important because the first layer added will be the first to be allowed
to respond to mouse clicks. 
*/

public class ViewLayer {

    private static final boolean DEBUG = false;

    protected BoundingRectangle r;
    protected String name;

    ArrayList<ViewLayer> subLayers;
    /**
        @param x - the x coordinate of the mouse click
        @param y - the y coordinate of the mouse click
        @return - true if the click was used, false otherwise.
    */
    public ViewLayer(ArrayList<ViewLayer> subLayers) {
        this.subLayers = subLayers;
        r = new BoundingRectangle(0, 0, 0, 0);
        name = "View Layer";
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
    
    public ViewLayer (BoundingRectangle frame) {
        subLayers = new ArrayList<ViewLayer>();
        this.r = frame;
        name = "View Layer";
    }
    /*
    public ViewLayer() {
        r = new BoundingRectangle(0, 0,0,0);
        name = "View Layer";
    }
    */
    public boolean click(int x, int y) {
        if (DEBUG) System.out.println("View Layer Clicked: " + name);
        if (DEBUG) System.out.println("Checking " + subLayers.size() + " SubLayers");
        for (ViewLayer vl : subLayers) {
            if (DEBUG) System.out.println("Checking Layer: " + vl.getName());
            if (!vl.r.pointInBoundingRectangle(x,y)) {
                if (DEBUG) System.out.println("Click not in BoundingRectangle");
            }   
            if (vl.r.pointInBoundingRectangle(x,y) && vl.click(x, y)) {
                if (DEBUG) System.out.println("Sending click to sublayer");
                if (DEBUG) System.out.println("");
                return true;
            }
        }
        
        return false;
    }
    
    public void draw(Graphics g) {
        int i = 0;
        ViewLayer vl = null;
        for (i=subLayers.size()-1; i>-1; i--) {
            vl = subLayers.get(i);
            vl.draw(g);
        }
    }
    
    public void addSublayer(ViewLayer vl) {
        if (vl != null)
        subLayers.add(vl);
    }
    
    public void removeSublayer(ViewLayer vl) {
        subLayers.remove(vl);
    }
}
