import java.util.ArrayList;
import java.awt.Graphics;

/**
Class representing a view layer on screen. Handles clicks and gets drawn. The order that you
add sublayers is important because the first layer added will be the first to be allowed
to respond to mouse clicks and the last to be drawn.
*/
public class ViewLayer {
    /** Enables/Disables debugging statements */
    protected static final boolean DEBUG = false;
    /** The rectangular region that this layer encompasses.*/
    protected BoundingRectangle r;  
    /** A name for this layer. Intended for debugging purposes. */
    protected String name;  
    private boolean drawingEnabled = true;
    
    public void setDrawingEnabled(boolean enabled) {
        this.drawingEnabled = enabled;
    }
    
    ArrayList<ViewLayer> subLayers;
    
    /**
    The only constructor.
    @param frame The rectangular region that this layer encompasses.
    */
    public ViewLayer (BoundingRectangle frame) {
        subLayers = new ArrayList<ViewLayer>();
        this.r = frame;
        name = "View Layer";
    }
    
    /**
        Sets the name for this view layer. Useful for debugging.
    */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
        Get the name for this view layer. Useful for debugging.
    */
    public String getName() {
        return name;
    }
    
    /**
        Handles clicks on this layer. Giving the proper return value is crucial
        as it if true is returned, layers below this layer may not receive clicks.
        @param x The x coordinate of the mouse click.
        @param y The y coordinate of the mouse click.
        @return true if the click was used.
        @return false if the click was not used.
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
    
    /**
        Draws this layer and all sublayers.
        @param g The graphics context to do the drawing into.
    */
    public void draw(Graphics g) {
        if (!drawingEnabled) return;
        int i = 0;
        ViewLayer vl = null;
        for (i=subLayers.size()-1; i>-1; i--) {
            vl = subLayers.get(i);
            vl.draw(g);
        }
    }
    
    public void refresh() {
        
    }
    
    /**
        Adds a sublayer to this layer.
        @param vl The layer to add as a sublayer.
    */
    public void addSublayer(ViewLayer vl) {
        if (vl != null)
        subLayers.add(vl);
    }
    
    /**
        Removes a sublayer from this layer.
        @param vl the layer to remove.
    */
    public void removeSublayer(ViewLayer vl) {
        subLayers.remove(vl);
    }
}
