/*
 * mostly serves as a visual.  has an icon, price, description, and item id to be used when given to ship
 */
public class Item {
	
	private int identity;//id MUST be in format aaabbbb, so if i wanted an item to take spot [2][13], its id would be 002013
	//Currently, no item above 0020029 will be supported
	
	public Item(int id) {
		identity = id;
	}

	public String getDescription() {
		return "";
	}
	
	public int getPrice() {
		return 0;
	}
	
	public int getID() {
		return identity;
	}
	
}
