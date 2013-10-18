/** Defines a rectangle within the game's "grid" **/

public class Rectangle {
    protected int x, y, width, height;
    
    public Rectangle(int x, int y, int width, int height) {
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
    
    /**
        Returns true if the two rectangles overlap
    **/
    public boolean overlaps(Rectangle r) {
        return false; // STUB
    }
    
    /**
        Returns the distance between this and another rectangle.
    **/
    public int distance(Rectangle r) {
        return 50; // STUB
    }
    
    public boolean isIn(int inX,int inY){
    	if(inX > x && inX < x + width && inY > y && inY < y + height){
    		return true;
    	}
    	return false;
    }
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y;}
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) {this.height = height; }
}