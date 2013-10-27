/*
 * mostly serves as a visual.  has an icon, price, description, and item id to be used when given to ship
 */
public class Item {
	
	private int identity;
	
	public Item(int id) {
		identity = id;
	}

	public String getDescription() {
		return "";
	}
	
	public int getPrice() {
		return 0;
	}
	
	public int getIdentity() {
		return identity;
	}
	
}
