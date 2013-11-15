
public class ItemButton extends Button{

	private ItemList.ItemNames id;
	
	public ItemButton(int inX, int inY, int w, int h, GameController gc, ItemList.ItemNames s) {
		super(inX, inY, w, h, gc, ItemList.getString(s,  ItemList.ItemStats.InGameName));
		id=s;
		t.setText(ItemList.getString(s,  ItemList.ItemStats.Description).toString());
	}
	
	public ItemButton(int inX, int inY, int w, int h, GameController gc, ItemList.ItemNames s, boolean showPrice) {
		super(inX, inY, w, h, gc, ItemList.getString(s,  ItemList.ItemStats.InGameName) + ": " + ItemList.getInt(s,ItemList.ItemStats.Price) + " gold");
		if(!showPrice){
			this.setTitle(ItemList.ItemStats.InGameName.toString());
		}
		t.setText(ItemList.getString(s,  ItemList.ItemStats.Description).toString());
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
