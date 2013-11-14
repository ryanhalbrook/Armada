import java.util.ArrayList;


public enum StatEditor {;

	//protected static final int ROWS = 3, COLS=10;
	//protected int[][] items= new int [ROWS][COLS];
	
	/*public static enum ItemNames {
		//item names
		hull_plates, engine_booster, aux_weapons;

	}*/
	
	public static void update(Ship s){
		ArrayList<Item> items = s.getItems();
		s.resetStats();
		if(items.size() < 1) return;
		for(int j = 0; j < 3; j++){
			for(Item i: items){
				i.update(s,j);
			}	
		}
		s.setHull((int)((double)s.getMaxHull() * s.getPerHull()));
		s.setEngine((int)((double)s.getMaxEngine() * s.getPerEng()));
	}
	
	public static void addItem(Ship s, Item i){
		s.getItems().add(i);
		StatEditor.update(s);
	}
	
	public static void removeItem(Ship s, Item i){
		i.uponRemoval(s);
		s.getItems().remove(i);
		StatEditor.update(s);
	}
}
