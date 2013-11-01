import java.util.ArrayList;
import java.awt.Graphics;
/**
Class representing a view layer on screen. Handles clicks and gets drawn. The order that you
add sublayers is important because the first layer added will be the first to be allowed
to respond to mouse clicks. 
*/

public class ViewLayer {

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
        System.out.println("View Layer Clicked: " + name);
        System.out.println("Checking " + subLayers.size() + " SubLayers");
        for (ViewLayer vl : subLayers) {
            System.out.println("Checking Layer: " + vl.getName());
            if (!vl.r.pointInBoundingRectangle(x,y)) {
                System.out.println("Click not in BoundingRectangle");
            }   
            if (vl.r.pointInBoundingRectangle(x,y) && vl.click(x, y)) {
                System.out.println("Sending click to sublayer");
                System.out.println("");
                return true;
            }
        }
        
        return false;
    }
    
    public void draw(Graphics g) {
        for (ViewLayer vl : subLayers) {
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
