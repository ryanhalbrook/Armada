/**
    Defines a rectangular region. Useful for keeping track of the area
    that something takes up on screen.
*/
public class BoundingRectangle {
    protected int x, y, width, height;
    protected DynamicSizeBroadcast dsb;
    
    public BoundingRectangle(int x, int y, int width, int height) {
    
    	if(width<0){
    		this.x=x+width;
    		this.width = Math.abs(width);
    	}
    	else{
    		this.x=x;
    		this.width=width;
    	}
    	if(height < 0){
    		this.y=y+height;
    		this.height = Math.abs(height);
    	}
    	else{
    		this.y=y;
    		this.height=height;
    	}
    	
    }
    
    public BoundingRectangle(int x, int y, DynamicSizeBroadcast dsb) {
    	this.dsb = dsb;
    }
    
    /**
        Returns true if the two BoundingRectangles overlap
    **/
    public boolean overlaps(BoundingRectangle r) {
        return false; // STUB
    }
    
    /**
        Returns the distance between this and another BoundingRectangle.
    **/
    public int distance(BoundingRectangle r) {
        return 50; // STUB
    }
    
    
    public boolean pointInBoundingRectangle(int x, int y) {
        return isIn(x, y);
        /*
        if (x-this.x == 0 || y-this.y == 0) return false;
        if (x+this.x > this.width || y+this.y > this.height) return false;
        return true;
        */
    }
    
    public void setCenter(int inX, int inY){
    	x = inX-width/2;
    	y=inY-height/2;
    	if(y < 0) y=0;
    	if(x< 0) x=0;
    }
    
    public boolean isIn(int inX,int inY){
    	if(inX > x && inX < x + width && inY > y && inY < y + height){
    		return true;
    	}
    	return false;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { 
        if (dsb != null) return dsb.getWidth();
        return width; 
    }
    public int getHeight() { 
        if (dsb != null) return dsb.getHeight();
        return height; 
    }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y;}
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) {this.height = height; }
}