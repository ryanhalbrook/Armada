package item;
import java.util.HashMap;
//This may look very complicated at first, but its actually pretty easy, I've added steps for you


public class ItemList {
	//STEP 0 : Only items that edit ship stats are supported right now.  Proc items are not supported
	public enum ItemNames{//STEP 1 : When adding a new item, add the name here.  This is what an item's id is
		Scout,Flagship,FlagshipPassiveRed,FlagshipPassiveBLue,Boarding,Normal, Blank, Cargo, Juggernaut, HullPlate, WeaponsUpgrade, EnginesUpgrade, SpeedUpgrade, ScalingWeaponsUpgrade, ScalingEnginesUpgrade, ScalingHullUpgrade, ScalingSpeedUpgrade, OverloadHull, OverloadWeapons, OverloadEngines, OverloadSpeed;
	}
	
	public enum ItemStats{//STEP 2 : If the item uses a stat not seen here, you will need to add it to this list.  You will also have to edit the Item class (I also have instructions there) because it does not yet support the stat if it is not here
		CargoPassive/*Just shows that this is a cargo ship.  Will make all items do nothing*/, HullFlat /*Adds a flat value to maxHull*/, WeaponsFlat/*Adds a flat value to weapons (damage)*/, EnginesFlat/*Adds a flat value to engines*/, 
		SpeedFlat/*Adds a flat value to speed*/, WeaponsPercentage/*Adds a percentage-based boost to weapons*/, EnginesPercentage/*Adds a percentage-based boost to engines*/, HullPercentage/*Adds a percentage-based boost to hull*/,
		ImageName/*name of the image for this item*/,SpeedPercentage/*Adds a percentage-based boost to speed*/, HullOverload/*Overloads the hull*/, WeaponsOverload/*Overloads the weapons*/, EnginesOverload/*Overloads the engines*/, SpeedOverload/*Overloads the speed*/, Description/*Item's description*/, Price/*Item's price*/, InGameName/*Item's in game name*/,
		NoBonus/*Items with this prevent any stat changes*/;
	}
	
	private static HashMap<String, Integer> itemVals = new HashMap<String, Integer>();
	private static HashMap<String, String> itemDescriptions = new HashMap<String, String>();
	//private static HashMap<String, String> inGameName = new HashMap<String,String>();

	static {//STEP 3 : Add the appropriate stat values here and a description.  The template is itemVals.put( ItemNames.[name you added above].name()+ItemStats.[stat you wish to edit].name(), [stat];
		//STEP 3.5 : If your item does not have a particular stat, don't add it.  
		
		//HullPlate
		itemVals.put(ItemNames.HullPlate.name()+ItemStats.HullFlat.name(), 100);// using .name() converts the name to the string of its name which it is stored by in the HashMap.  There is no reason to worry about this though
		itemVals.put(ItemNames.HullPlate.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.HullPlate.name() + ItemStats.Description, "Increases Hull by " + ItemList.getInt(ItemNames.HullPlate, ItemStats.HullFlat));
		itemDescriptions.put(ItemNames.HullPlate.name() + ItemStats.InGameName, "Hull Reinforcements");
		
		//Cargo
		itemVals.put(ItemNames.Cargo.name()+ItemStats.NoBonus.name(), 1);// using .name() converts the name to the string of its name which it is stored by in the HashMap.  There is no reason to worry about this though
		itemVals.put(ItemNames.Cargo.name()+ItemStats.Price.name(), 700);
		itemDescriptions.put(ItemNames.Cargo.name() + ItemStats.Description, "This ship cannot be upgraded with items");
		itemDescriptions.put(ItemNames.Cargo.name() + ItemStats.InGameName, "Cargo Ship");
		itemDescriptions.put(ItemNames.Cargo.name()+ItemStats.ImageName, "cargoship_red");
		
		//WeaponsUpgrade
		itemVals.put(ItemNames.WeaponsUpgrade.name() + ItemStats.WeaponsFlat, 100);
		itemVals.put(ItemList.ItemNames.WeaponsUpgrade.name() + ItemStats.Price, 100);
		itemDescriptions.put(ItemNames.WeaponsUpgrade.name() + ItemStats.Description, "Increases Weapons by " + ItemList.getInt(ItemNames.WeaponsUpgrade, ItemStats.WeaponsFlat));
		itemDescriptions.put(ItemNames.WeaponsUpgrade.name() + ItemStats.InGameName, "Photon Blasters");
		itemDescriptions.put(ItemNames.WeaponsUpgrade.name() + ItemStats.ImageName, "attackicon_01");
		
		//EngineUpgrade //Please remember to add the comment for the item name
		itemVals.put(ItemNames.EnginesUpgrade.name()+ItemStats.EnginesFlat.name(), 100);//you had ItemStats.EnginesUpgrade - EnginesUpgrade is a name, not a stat
		itemVals.put(ItemNames.EnginesUpgrade.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.EnginesUpgrade.name()+ItemStats.Description, "Increases Engine by " + ItemList.getInt(ItemNames.EnginesUpgrade, ItemStats.EnginesFlat));
		itemDescriptions.put(ItemNames.EnginesUpgrade.name() + ItemStats.InGameName, "Engine Reinforcements");
		itemDescriptions.put(ItemNames.EnginesUpgrade.name() + ItemStats.ImageName, "movementicon_01");
		
		//SpeedUpgrade
		itemVals.put(ItemNames.SpeedUpgrade.name()+ItemStats.SpeedFlat.name(), 100);//you had ItemStats.SpeedUpgrade - SpeedUpgrade is a name, not a stat
		itemVals.put(ItemNames.SpeedUpgrade.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.SpeedUpgrade.name()+ItemStats.Description, "Increases Speed by " + ItemList.getInt(ItemNames.SpeedUpgrade, ItemStats.SpeedFlat));
		itemDescriptions.put(ItemNames.SpeedUpgrade.name() + ItemStats.InGameName, "Rocket Boosters");
		
		//ScalingWeaponsUpgrade
		itemVals.put(ItemNames.ScalingWeaponsUpgrade.name()+ItemStats.WeaponsPercentage.name(), 15);
		itemVals.put(ItemNames.ScalingWeaponsUpgrade.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.ScalingWeaponsUpgrade.name()+ItemStats.Description, "Increases Weapons by " + ItemList.getInt(ItemNames.ScalingWeaponsUpgrade, ItemStats.WeaponsPercentage) +"%");
		itemDescriptions.put(ItemNames.ScalingWeaponsUpgrade.name() + ItemStats.InGameName, "Increased Firepower");
		
		//ScalingHullUpgrade
		itemVals.put(ItemNames.ScalingHullUpgrade.name()+ItemStats.HullPercentage.name(), 5);
		itemVals.put(ItemNames.ScalingHullUpgrade.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.ScalingHullUpgrade.name()+ItemStats.Description, "Increases Hull by " + ItemList.getInt(ItemNames.ScalingHullUpgrade, ItemStats.HullPercentage) +"%");
		itemDescriptions.put(ItemNames.ScalingHullUpgrade.name() + ItemStats.InGameName, "Increased Hull Durability");
		
		//Juggernaut item for bonus
		itemVals.put(ItemNames.Juggernaut.name()+ItemStats.HullPercentage.name(), 10);
		itemVals.put(ItemNames.Juggernaut.name()+ItemStats.Price.name(), 850);
		itemDescriptions.put(ItemNames.Juggernaut.name()+ItemStats.Description, "This ship's Hull is increased by " + ItemList.getInt(ItemNames.Juggernaut, ItemStats.HullPercentage) +"%");
		itemDescriptions.put(ItemNames.Juggernaut.name() + ItemStats.InGameName, "Juggernaut Ship");
		itemDescriptions.put(ItemNames.Juggernaut.name()+ItemStats.ImageName, "juggernaut_red");
		
		//Normal/Basic ship
		itemVals.put(ItemNames.Normal.name()+ItemStats.Price.name(), 700);
		itemDescriptions.put(ItemNames.Normal.name()+ItemStats.Description, "Standard ship with no bonus");
		itemDescriptions.put(ItemNames.Normal.name() + ItemStats.InGameName, "Fighter");
		itemDescriptions.put(ItemNames.Normal.name()+ItemStats.ImageName, "fighter_red");
		
		//Flagship
		itemVals.put(ItemNames.Flagship.name()+ItemStats.NoBonus.name(), 1);
		itemVals.put(ItemNames.Flagship.name()+ItemStats.Price.name(), 2000);
		itemDescriptions.put(ItemNames.Flagship.name()+ItemStats.Description, "This Ship receives no bonus from items");
		itemDescriptions.put(ItemNames.Flagship.name() + ItemStats.InGameName, "Flagship");
		itemDescriptions.put(ItemNames.Flagship.name()+ItemStats.ImageName, "flagship_red");
		
		//Flagship Passive
		itemVals.put(ItemNames.FlagshipPassiveRed.name()+ItemStats.NoBonus.name(), 1);
		itemVals.put(ItemNames.FlagshipPassiveRed.name()+ItemStats.Price.name(), 0);
		itemDescriptions.put(ItemNames.FlagshipPassiveRed.name()+ItemStats.Description, "This Ship receives no bonus from items");
		itemDescriptions.put(ItemNames.FlagshipPassiveRed.name() + ItemStats.InGameName, "Flagship Passive");
		itemDescriptions.put(ItemNames.FlagshipPassiveRed.name()+ItemStats.ImageName, "flagship_red");
		
		//Flagship Passive
		itemVals.put(ItemNames.FlagshipPassiveBLue.name()+ItemStats.NoBonus.name(), 1);
		itemVals.put(ItemNames.FlagshipPassiveBLue.name()+ItemStats.Price.name(), 0);
		itemDescriptions.put(ItemNames.FlagshipPassiveBLue.name()+ItemStats.Description, "This Ship receives no bonus from items");
		itemDescriptions.put(ItemNames.FlagshipPassiveBLue.name() + ItemStats.InGameName, "Flagship Passive");
		itemDescriptions.put(ItemNames.FlagshipPassiveBLue.name()+ItemStats.ImageName, "flagship_blue");
		
		//Boarding ship
		itemVals.put(ItemNames.Boarding.name()+ItemStats.Price.name(), 600);
		itemDescriptions.put(ItemNames.Boarding.name()+ItemStats.Description, "Boarding Ship");
		itemDescriptions.put(ItemNames.Boarding.name() + ItemStats.InGameName, "Boarding Ship");
		itemDescriptions.put(ItemNames.Boarding.name()+ItemStats.ImageName, "boarding_red");
		
		//Scout ship
		itemVals.put(ItemNames.Scout.name()+ItemStats.Price.name(), 500);
		itemDescriptions.put(ItemNames.Scout.name()+ItemStats.Description, "Scout Ship");
		itemDescriptions.put(ItemNames.Scout.name() + ItemStats.InGameName, "Scout Ship");
		itemDescriptions.put(ItemNames.Scout.name()+ItemStats.ImageName, "Scout_red");
		
		//ScalingEnginesUpgrade
		itemVals.put(ItemNames.ScalingEnginesUpgrade.name()+ItemStats.EnginesPercentage.name(), 15);
		itemVals.put(ItemNames.ScalingEnginesUpgrade.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.ScalingEnginesUpgrade.name()+ItemStats.Description, "Increases Engines by " + ItemList.getInt(ItemNames.ScalingEnginesUpgrade, ItemStats.EnginesPercentage) +"%");
		itemDescriptions.put(ItemNames.ScalingEnginesUpgrade.name() + ItemStats.InGameName, "Increased Engines");
		
		//ScalingSpeedUpgrade
		itemVals.put(ItemNames.ScalingSpeedUpgrade.name()+ItemStats.SpeedPercentage.name(), 15);
		itemVals.put(ItemNames.ScalingSpeedUpgrade.name()+ItemStats.Price.name(), 100);
		itemDescriptions.put(ItemNames.ScalingSpeedUpgrade.name()+ItemStats.Description, "Increases Speed by " + ItemList.getInt(ItemNames.ScalingSpeedUpgrade, ItemStats.SpeedPercentage) +"%");
		itemDescriptions.put(ItemNames.ScalingSpeedUpgrade.name() + ItemStats.InGameName, "Increased Rocket Boosters");
		
		//new Overload Mechanic: Engines
		itemVals.put(ItemNames.OverloadEngines.name()+ItemStats.EnginesOverload.name(), 45);
		itemVals.put(ItemNames.OverloadEngines.name()+ItemStats.Price.name(), 150);
		itemDescriptions.put(ItemNames.OverloadEngines.name()+ItemStats.Description, "Temporarily increases Engines by " + ItemList.getInt(ItemNames.OverloadEngines, ItemStats.EnginesOverload) +"%");
		itemDescriptions.put(ItemNames.OverloadEngines.name() + ItemStats.InGameName, "Overloading Engines");
		
		//new Overload Mechanic: Speed
		itemVals.put(ItemNames.OverloadSpeed.name()+ItemStats.SpeedOverload.name(), 45);
		itemVals.put(ItemNames.OverloadSpeed.name()+ItemStats.Price.name(), 150);
		itemDescriptions.put(ItemNames.OverloadSpeed.name()+ItemStats.Description, "Temporarily increases Speed by " + ItemList.getInt(ItemNames.OverloadSpeed, ItemStats.SpeedOverload) +"%");
		itemDescriptions.put(ItemNames.OverloadSpeed.name() + ItemStats.InGameName, "Overloading Speed");
		
		//new Overload Mechanic: Hull
		itemVals.put(ItemNames.OverloadHull.name()+ItemStats.HullOverload.name(), 45);
		itemVals.put(ItemNames.OverloadHull.name()+ItemStats.Price.name(), 150);
		itemDescriptions.put(ItemNames.OverloadHull.name()+ItemStats.Description, "Temporarily increases Hull by " + ItemList.getInt(ItemNames.OverloadHull, ItemStats.HullOverload) +"%");
		itemDescriptions.put(ItemNames.OverloadHull.name() + ItemStats.InGameName, "Overloading Hull");
		
		//new Overload Mechanic: Weapons
		itemVals.put(ItemNames.OverloadWeapons.name()+ItemStats.WeaponsOverload.name(), 45);
		itemVals.put(ItemNames.OverloadWeapons.name()+ItemStats.Price.name(), 150);
		itemDescriptions.put(ItemNames.OverloadWeapons.name()+ItemStats.Description, "Temporarily increases Weapons by " + ItemList.getInt(ItemNames.OverloadWeapons, ItemStats.WeaponsOverload) +"%");
		itemDescriptions.put(ItemNames.OverloadWeapons.name() + ItemStats.InGameName, "Overloading Weapons");
		
		//Blank
		itemDescriptions.put(ItemNames.Blank.name()+ItemStats.Description, "");
		itemDescriptions.put(ItemNames.Blank.name() + ItemStats.InGameName, "");
	};
	
	
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
