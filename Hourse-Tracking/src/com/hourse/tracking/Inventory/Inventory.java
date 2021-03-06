package com.hourse.tracking.Inventory;

import static com.hourse.tracking.constant.Constant.MAXSTOCK;

import java.util.Map;
import java.util.TreeMap;

import com.hourse.tracking.model.Denomination;
import com.hourse.tracking.model.Horse;

public class Inventory {

	private Map<Denomination,Integer> denominationInventory;

	private Map<Integer,Horse> hourseInventory;

	public Inventory() {
		this.denominationInventory = new TreeMap<Denomination,Integer>();
		this.hourseInventory = new TreeMap<Integer,Horse>();
		init();
	}

	private void init() {

		denominationInventory.put(new Denomination(1), MAXSTOCK);
		denominationInventory.put(new Denomination(5), MAXSTOCK);
		denominationInventory.put(new Denomination(10), MAXSTOCK);
		denominationInventory.put(new Denomination(20), MAXSTOCK);
		denominationInventory.put(new Denomination(100), MAXSTOCK);
		
		hourseInventory.put(1, new Horse("That Darn Gray Cat",5));
		hourseInventory.put(2, new Horse("Fort Utopia",10));
		hourseInventory.put(3, new Horse("Count Sheep",9));
		hourseInventory.put(4, new Horse("Ms Traitour",4));
		hourseInventory.put(5, new Horse("Real Princess",3));
		hourseInventory.put(6, new Horse("Pa Kettle",5));
		hourseInventory.put(7, new Horse("Gin Stinger",6));
		
	}

	public Map<Denomination, Integer> getDenominationInventory() {
		return denominationInventory;
	}

	public void setDenominationInventory(Map<Denomination, Integer> denominationInventory) {
		this.denominationInventory = denominationInventory;
	}

	public Map<Integer, Horse> getHourseInventory() {
		return hourseInventory;
	}

	public void setHourseInventory(Map<Integer, Horse> hourseInventory) {
		this.hourseInventory = hourseInventory;
	}

}
