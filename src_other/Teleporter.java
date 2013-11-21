import java.awt.Graphics;
import java.util.ArrayList;

public class Teleporter extends DynamicElement{
	
	public Teleporter(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public void teleport(Ship s)
	{
		System.out.println("Which planet would you like to teleport to?");
	}

}
