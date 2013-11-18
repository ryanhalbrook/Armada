
public class UpgradeButton extends Button{
	
	public UpgradeButton(int inX, int inY, int w, int h, GameController gc, DynamicElement de) {
		super(inX, inY, w, h, gc, ((Planet) de).upgradeDescription(), "1");
		t.setText("");
	}
	
	public UpgradeButton(int inX, int inY, int w, int h, GameController gc, boolean showPrice, DynamicElement de) {
		super(inX, inY, w, h, gc, ((Planet) de).upgradeDescription() + ": " + ((Planet) de).upgradePrice() + " gold", "1");
		if(!showPrice){
			this.setTitle(((Planet) de).upgradeDescription());
		}
		t.setText("");
	}
	
	public boolean click(int x, int y){
		if(this.isIn(x, y)){
			
			return true;
		}
		return false;
	}
}
