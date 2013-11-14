
public class ItemButton extends Button{

	private ItemList.ItemNames id;
	
	public ItemButton(int inX, int inY, int w, int h, GameController gc, ItemList.ItemNames s) {
		super(inX, inY, w, h, gc, ItemList.getString(s,  ItemList.ItemStats.InGameName) + ": " + ItemList.getInt(s,ItemList.ItemStats.Price) + " gold");
		id=s;
	}
	
	public boolean click(int x, int y){
		if(this.isIn(x, y)){
			
			return true;
		}
		return false;
	}

	public ItemList.ItemNames getId(){
		return id;
	}
	
	
}
