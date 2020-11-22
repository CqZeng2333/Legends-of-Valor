package Avatars;

import Equipments.BuyableObject;
import Map.LegendBoard;

public interface HeroSkillsInterface {
	public double castSpell(String spellName);
	
	public void teleport(int col, int row, LegendBoard lb, int index);
	
	public void back(LegendBoard lb, int index);

	public int drinkPotion(String potionName);

	/*
	 * Add a buyable object to the hero
	 * return 0 if success
	 * return -1 if not correct type
	 */
	
	public int buyObject(BuyableObject bo);

	/*
	 * Sell the object from the hero's outfit
	 * Return 0 if success
	 * return -1 if not correct type
	 * return -2 if no such object
	 */
	
	public int sellObject(String type, String name);

	
	/*
	 * According to "type"(weapon/armor) to do exchange
	 * Return 0 if success;
	 * return -1 if no such equipment;
	 * return -2 if "type" not weapon or armor
	 */
	
	public int changeEquipment(String type, String name);
}
