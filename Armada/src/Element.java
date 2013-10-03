/*
 * DynamicElement is a child class that is for Elements that have stats
 * There should be another child for extending a tree into the realm of visuals such as lasers to show an attack but these 
 * visuals cannot be selected
 */
public class Element {
    
	protected int x,y;
	protected int width, height;
	//orientation
	//image
	
	public Element(){
		
	}
	/*
	 * this method is intended to be a script.  It will change the orientation of the Element and location of the Element over a few
	 * seconds in order to show it moving as an animation 
	 */
	public void move(int Xin, int Yin){
		
	}
	
	public void setLocation(int Xin, int Yin){
		x=Xin;
		y=Yin;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
