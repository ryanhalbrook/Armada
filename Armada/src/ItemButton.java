package src; import src.view.*;
public class ItemButton extends Button{

	private ItemList.ItemNames id;
	
	public ItemButton(int inX, int inY, int w, int h, GameController gc, ItemList.ItemNames s) {
		super(inX, inY, w, h, gc, ItemList.getString(s,  ItemList.ItemStats.InGameName));
		id=s;
		setImageName("");
		t.setText(ItemList.getString(s,  ItemList.ItemStats.Description).toString());
	}
	
	public ItemButton(int inX, int inY, int w, int h, GameController gc, ItemList.ItemNames s, boolean showPrice) {
		super(inX, inY, w, h, gc, ItemList.getString(s,  ItemList.ItemStats.InGameName) + ": " + ItemList.getInt(s,ItemList.ItemStats.Price) + " gold","juggernaut_red");
		if(!showPrice){
			this.setTitle(ItemList.getString(s,ItemList.ItemStats.InGameName));
		}
		t.setText(ItemList.getString(s,  ItemList.ItemStats.Description));
		clickable=true;
		assignImage(s);
		id=s;
	}
	
	private void assignImage(ItemList.ItemNames i){
		this.imageName = ItemList.getString(i,ItemList.ItemStats.ImageName);
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
