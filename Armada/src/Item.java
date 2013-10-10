/*
 * mostly serves as a visual.  has an icon, price, description, and item id to be used when given to ship
 */
public class Item {
	
	private int identity;
	
	public String getDescription() {
		if (identity == 0) return "";
		
		else if (identity == 1) return "";
			
		else if (identity == 2) return "";
		return "";
	}
	
	public int getPrice() {
		if (identity == 0) return 0;
		
		else if (identity == 1) return 0;
		
		else if (identity == 2) return 0;
		return 0;
	}
	
	public int getIdentity() {
		return identity;
	}
	public Item(int id) {
		identity = id;
	}

}
