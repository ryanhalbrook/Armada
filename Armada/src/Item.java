
public class Item {
	
	//This class may seem hard to edit (and it may really be) but I'll try to give you steps
	
	protected int priority;//0 most prioritized, 0 for flat edits, 1 for % or multiplication, 2 for based off of other stats, 3 for proc
	protected int price;
	protected ItemList.ItemNames id;
	
	public Item(ItemList.ItemNames id) {
		this.id=id;
	}

	public ItemList.ItemNames getId() {
		return id;
	}

	
	//STEP 0 : If your stat does not edit the stats of the ship, skip to the bottom
	
	/*STEP 1 : Add the update code.  This is what the item, in general, does.  Since the order that the items are calculated is sensitive (I may go from a + b + c to a + d(b+c)) every time we 
	* add an item we need to make sure the stats are recalculated and done so in the right order.  So make sure you put it in the right priority
	**/ 
	public void update(Ship s, int priority){
		if(ItemList.keyInt(id,ItemList.ItemStats.CargoPassive)){//checks if HullFlat (the stat) exists for the id
			return;
		}
		if(priority == 0){//For flat increases/decreases.  ie a + b
			/*
			 * Template: 
			 * if(ItemList.keyInt(id, ItemList.ItemStats.[stat])){    //this checks if the stat exists for this item.  DO NOT specify an id, just use "id".  This way multiple items may edit this stat
			 * 		s.[method used to edit stats]([appropriate calculations]);   // [appropriate calculations] will likely use ItemList.getInt(id, ItemList.ItemStats.[stat to use])  the [stat to use] should have been added to ItemList.ItemStats
			 * }
			 * I have provided commentary for my first one to show what each line is doing in context 
			 */
			if(ItemList.keyInt(id,ItemList.ItemStats.HullFlat)){//checks if HullFlat (the stat) exists for the id
				s.incMaxHullFlat(ItemList.getInt(id,ItemList.ItemStats.HullFlat));// increases the maxHull of the ship by the item's HullFlat stat.  ItemList.getInt will get the value stored in HullFlat for the particular item.  You may want to use ItemList.getString if you are getting a string
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.WeaponsFlat)){
				s.incWeaponsFlat(ItemList.getInt(id, ItemList.ItemStats.WeaponsFlat));
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.EnginesFlat)){
				s.incMaxEngineFlat(ItemList.getInt(id, ItemList.ItemStats.EnginesFlat));//you are using a method that does not exist.  Therefore, you need to implement it in Ship.  I have done so and fixed this line. Also, you needed to edit maxEngine, not engine.  And upon purchase, you should have added the value to engine, since engine is somewhat dependant on the maxEngine.  remember, engine and hull are both part of the healthbar and have a max 
			}	
			if(ItemList.keyInt(id, ItemList.ItemStats.SpeedFlat)){
				s.incSpeedFlat(ItemList.getInt(id, ItemList.ItemStats.SpeedFlat));
			}	
		}
		if(priority == 1){// for non-flat calculations.  ie a*b
			if(ItemList.keyInt(id, ItemList.ItemStats.WeaponsPercentage)){
				s.weaponsPerInc(ItemList.getInt(id, ItemList.ItemStats.WeaponsPercentage));
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.EnginesPercentage)){
				s.maxEnginesPerInc(ItemList.getInt(id, ItemList.ItemStats.EnginesPercentage));
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.HullPercentage)){
				s.maxHullPerInc(ItemList.getInt(id, ItemList.ItemStats.HullPercentage));
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.SpeedPercentage)){
				s.speedPerInc(ItemList.getInt(id, ItemList.ItemStats.SpeedPercentage));
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.SpeedOverload)){
				s.speedPerInc(ItemList.getInt(id, ItemList.ItemStats.SpeedOverload));
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.WeaponsOverload)){
				s.weaponsPerInc(ItemList.getInt(id, ItemList.ItemStats.WeaponsOverload));
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.EnginesOverload)){
				s.enginesPerInc(ItemList.getInt(id, ItemList.ItemStats.EnginesOverload));
			}
			if(ItemList.keyInt(id, ItemList.ItemStats.HullOverload)){
				s.hullPerInc(ItemList.getInt(id, ItemList.ItemStats.HullOverload));
			}
		}
		if(priority == 2){// for calculations based on other stats.  ie damage += hull * 0.2
			//nothing here yet
		}
	}
	//STEP 2 IS NO LONGER SUPPORTED / NEEDED
	//STEP 2 : This is for only very specific items that have an affect upon purchase that is NOT its normal update (as in, a ship stat that needs to be updated one time due to the change made in update()) .  You most likely will skip this
	public void uponPurchase(Ship s){//immediate one time actions, such as adding some hull because of addition to maxHull
		/*if(ItemList.keyInt(id, ItemList.ItemStats.HullFlat)){
			s.setHull(s.getHull() + ItemList.getInt(id, ItemList.ItemStats.HullFlat));//Upon buying some extra maxHull the ship is also healed by the added maxHull so that you DON'T have 100/100 hull then upgrade to have 100/120.  In this case, 20 is also added so that the hull will be 120/120
		}
		if(ItemList.keyInt(id, ItemList.ItemStats.EnginesFlat)){//This needed to be added for EnginesFlat
			s.setEngine(s.getEngine() + ItemList.getInt(id, ItemList.ItemStats.EnginesFlat));//Upon buying some extra maxEngine the ship is also healed by the added maxEngine so that you DON'T have 100/100 engine then upgrade to have 100/120.  In this case, 20 is also added so that the engine will be 120/120
		}
		if(ItemList.keyInt(id, ItemList.ItemStats.HullPercentage)){//This needed to be added for EnginesFlat
			int max = s.getMaxHull();
			double per = (double)ItemList.getInt(id, ItemList.ItemStats.HullPercentage)/100.0;
			int sub = (int)Math.round(max/(1+per));
			s.setHull(s.getHull() + max - sub);
		}*/
	}
	//STEP 3 IS NO LONGER SUPPORTED / NEEDED
	//STEP 3 : This is the opposite as the above.  Cautious steps that may have to be taken upon removal.  Remember, the item has NOT been removed yet, this is called immediately before removal.  It is likely you wont need this
	public void uponRemoval(Ship s){// immediate one time actions, such as making sure that the current hull is not above the new max after removal
		/*if(ItemList.keyInt(id, ItemList.ItemStats.HullFlat)){//if item has HullFlat -- if I'm removing maxHull from the ship
			if(s.getHull() > (s.getMaxHull() - ItemList.getInt(id, ItemList.ItemStats.HullFlat))){//checks if the hull is greater than the max should be after removal.  Remember, it has not been removed yet
				s.setHull(s.getMaxHull()  - ItemList.getInt(id, ItemList.ItemStats.HullFlat));//if the hull will be greater than its to-be max, reduce it to its to-be max
			}
		}
		if(ItemList.keyInt(id, ItemList.ItemStats.EnginesFlat)){//This needed to be added for EnginesFlat
			if(s.getEngine() > (s.getMaxEngine() - ItemList.getInt(id, ItemList.ItemStats.EnginesFlat))){
				s.setEngine(s.getMaxEngine()  - ItemList.getInt(id, ItemList.ItemStats.EnginesFlat));
			}
		}*/
	}
	
	//STEP 4 : Done here! Go over to ItemList and make sure your item's stats have been specified for it
	//Testing : Add this item to NormalShip 2 times and remove it once to make sure the stats are correct
	
	
	//STEP 0 : These are for asking for specific stats such as description and price.  Anything that doesn't edit the Ship.  Add your get method here
	public int getPrice() {
		if(!ItemList.keyInt(id, ItemList.ItemStats.Price)) return 0;
		return ItemList.getInt(id, ItemList.ItemStats.Price);
	}
	
	public String getDescription() {
		return ItemList.getString(id, ItemList.ItemStats.Description);
	}
	
	public String getInGameName() {
		return ItemList.getString(id, ItemList.ItemStats.InGameName);
	}
	
	public String getImageString(){
		if(!ItemList.keyString(id,ItemList.ItemStats.ImageName)){
			return "";
		}
		return ItemList.getString(id,ItemList.ItemStats.ImageName);
	}
	
}
