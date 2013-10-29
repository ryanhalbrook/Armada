
public class StatEditor {

	protected Ship ship;
	protected static final int ROWS = 3, COLS=10;
	protected int[][] items= new int [ROWS][COLS];
	
	public StatEditor(Ship s){
		ship=s;
		//items[0][0] = 0;
		
		update();
	}
	
	public void update(){
		//this is where item id's are translated into stat editing
		ship.resetStats();
		for(int i = 0; i< ROWS; i++){
			
			
			for(int j = 0; j < COLS; j++){
				
				//Flat damage upgrade
				if(i==0 && j==0 && items[i][j] > 0){
					for(int h = items[i][j]; h >0; h--){
						ship.incWeaponsFlat(100);
					}
				}
				
				//Flat max hull increase
				if(i==0 && j==1 && items[i][j] > 0){
					for(int h = items[i][j]; h >0; h--){
						System.out.println("Increasing max hull");
						ship.incMaxHullFlat(300);
					}
				}
			}
		}
	}
	
	public void addItem(Item i){
		int h =i.getID();
		addItem(h);
	}
	
	public void addItem(int id){
		int b = id % 1000;
		int a = (id - b) / 1000;
		if(a>ROWS || b>COLS){
			System.out.println("Non-existant item being added");
			return;
		}
		items[a][b]++;
		update();
		//effects upon buying
		switch(id){
		case 000001:
			ship.fullHealHull();
			break;
		}
		
		
	}
}
