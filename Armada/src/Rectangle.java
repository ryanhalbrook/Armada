/** Defines a rectangle within the game's "grid" **/

public class Rectangle {
    int x, y, width, height;
    
    public Rectangle(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
    
    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    
    public void setX(int x) { this.x = x; }
    public void setY(int y) { this.y = y;}
    public void setWidth(int width) { this.width = width; }
    public void setHeight(int height) {this.height = height; }
}