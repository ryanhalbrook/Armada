import java.util.HashMap;
//This may look very complicated at first, but its actually pretty easy, I've added steps for you


public class ItemList {
	//STEP 0 : Only items that edit ship stats are supported right now.  Proc items are not supported
	public enum ItemNames{//STEP 1 : When adding a new item, add the name here.  This is what an item's id is
		HullPlate, WeaponsUpgrade, EnginesUpgrade, SpeedUpgrade;
	}
	
	public enum ItemStats{//STEP 2 : If the item uses a stat not seen here, you will need to add it to this list.  You will also have to edit the Item class (I also have instructions there) because it does not yet support the stat if it is not here
		HullFlat /*Adds a flat value to maxHull*/, WeaponsFlat/*Adds a flat value to weapons (damage)*/, EnginesFlat/*Adds a flat value to engines*/, SpeedFlat/*Adds a flat value to speed*/, Description/*Item's description*/, Price/*Item's price*/;
	}
	
	private static HashMap<String, Integer> itemVals = new HashMap<String, Integer>();
	private static HashMap<String, String> itemDescriptions = new HashMap<String, String>();

	static {//STEP 3 : Add the appropriate stat values here and a description.  The template is itemVals.put( ItemNames.[name you added above].name()+ItemStats.[stat you wish to edit].name(), [stat];
		//STEP 3.5 : If your item does not have a particular stat, don't add it.  
		
		//HullPlate
		itemVals.put(ItemNames.HullPlate.name()+ItemStats.HullFlat.name(), 100);// using .name() converts the name to the string of its name which it is stored by in the HashMap.  There is no reason to worry about this though
		itemVals.put(ItemNames.HullPlate.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.HullPlate.name() + ItemStats.Description, "Increases Hull by " + ItemList.getInt(ItemNames.HullPlate, ItemStats.HullFlat));
		
		//WeaponsUpgrade
		itemVals.put(ItemNames.WeaponsUpgrade.name() + ItemStats.WeaponsFlat, 100);
		itemVals.put(ItemList.ItemNames.WeaponsUpgrade.name() + ItemStats.Price, 100);
		itemDescriptions.put(ItemNames.WeaponsUpgrade.name() + ItemStats.Description, "Increases Weapons by " + ItemList.getInt(ItemNames.WeaponsUpgrade, ItemStats.WeaponsFlat));
		
		//EngineUpgrade //Please remember to add the comment for the item name
		itemVals.put(ItemNames.EnginesUpgrade.name()+ItemStats.EnginesFlat.name(), 100);//you had ItemStats.EnginesUpgrade - EnginesUpgrade is a name, not a stat
		itemVals.put(ItemNames.EnginesUpgrade.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.EnginesUpgrade.name()+ItemStats.Description, "Increases Engine by " + ItemList.getInt(ItemNames.EnginesUpgrade, ItemStats.EnginesFlat));
		
		//SpeedUpgrade
		itemVals.put(ItemNames.SpeedUpgrade.name()+ItemStats.SpeedFlat.name(), 100);//you had ItemStats.SpeedUpgrade - SpeedUpgrade is a name, not a stat
		itemVals.put(ItemNames.SpeedUpgrade.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.SpeedUpgrade.name()+ItemStats.Description, "Increases Speed by " + ItemList.getInt(ItemNames.SpeedUpgrade, ItemStats.SpeedFlat));
	}
	
	//STEP 4 : Done!  Thats it.  Don't touch anything else
	//Testing : Add this item to NormalShip 2 times and remove it once to make sure the stats are correct
	
	
	//DO NOT TOUCH
	public static int getInt(ItemNames itemName, ItemStats itemStats){
	 	return itemVals.get(itemName.name() + itemStats.name());
	}
	
	public static String getString(ItemNames itemName, ItemStats itemStats){
	 	return itemDescriptions.get(itemName.name() + itemStats.name());
	}
	
	public static boolean keyInt(ItemNames itemName, ItemStats itemStats){
		return itemVals.containsKey(itemName.name() + itemStats.name());
	}
	
	public static boolean keyString(ItemNames itemName, ItemStats itemStats){
		return itemDescriptions.containsKey(itemName.name() + itemStats.name());
	}
	
}
