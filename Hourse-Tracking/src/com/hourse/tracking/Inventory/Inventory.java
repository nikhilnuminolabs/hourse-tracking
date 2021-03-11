package com.hourse.tracking.Inventory;
import static com.hourse.tracking.constant.Constant.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;
import com.hourse.tracking.model.Denomination;
import com.hourse.tracking.model.Horse;

public class Inventory {

	private Map<Denomination,Integer> denominationInventory;

	private Map<Integer,Horse> hourseInventory;

	private List<Denomination> denominationList;

	public Inventory() {
		this.denominationInventory = new TreeMap<Denomination,Integer>();
		this.hourseInventory = new TreeMap<Integer,Horse>();
		this.denominationList =  new ArrayList<Denomination>();
		init();
	}

	private void init() {

		initializeHorseInventory();

		initializeDenominationInventory();

		denominationList.addAll(denominationInventory.keySet());
		Collections.reverse(denominationList);
	}

	private void initializeHorseInventory() {

		hourseInventory.put(1, new Horse(HORSE_NAME_1,5));
		hourseInventory.put(2, new Horse(HORSE_NAME_2,10));
		hourseInventory.put(3, new Horse(HORSE_NAME_3,9));
		hourseInventory.put(4, new Horse(HORSE_NAME_4,4));
		hourseInventory.put(5, new Horse(HORSE_NAME_5,3));
		hourseInventory.put(6, new Horse(HORSE_NAME_6,5));
		hourseInventory.put(7, new Horse(HORSE_NAME_7,6));

	}

	private void initializeDenominationInventory() {

		denominationInventory.put(new Denomination(1), MAXSTOCK);
		denominationInventory.put(new Denomination(5), MAXSTOCK);
		denominationInventory.put(new Denomination(10), MAXSTOCK);
		denominationInventory.put(new Denomination(20), MAXSTOCK);
		denominationInventory.put(new Denomination(100), MAXSTOCK);
		
	}

	public void restockDenominationInventory() {

		initializeDenominationInventory();
	}

	public void displayHorseInventory(Integer wonHorseNumber) {

		for (Entry<Integer, Horse> horseInventory : hourseInventory.entrySet()) {

			System.out.println(horseInventory.getKey()
					+","+horseInventory.getValue().getName()
					+","+horseInventory.getValue().getOdds()
					+","+(horseInventory.getKey()== wonHorseNumber ? "won":"lost"));
		}
	}

	public void displayDenominationInventory() {

		for (Map.Entry<Denomination ,Integer> denominaionInventory : denominationInventory.entrySet()) {

			System.out.println(denominaionInventory.getKey().toString()+","+denominaionInventory.getValue());
		}
	}

	public void payoutDispense(Integer horseNumber,Integer wonHorseNumber,Integer betAmount) {

		Map<Denomination ,Integer> payoutDenomination = new TreeMap<Denomination, Integer>();		
		Map<Denomination ,Integer> tempDenominationInventory = new TreeMap<Denomination, Integer>();
		tempDenominationInventory.putAll(denominationInventory);

		if(horseNumber == wonHorseNumber) {

			Integer odds = hourseInventory.get(horseNumber).getOdds();
			Integer payout = odds * betAmount;

			for(Denomination denomination : denominationList) {
				Integer denominationStock = denominationInventory.get(denomination);
				Integer denominationCount = 0;
				if(denominationStock > 0 && payout >= denomination.getValue()) {	
					denominationCount = payout/denomination.getValue();
					if(denominationCount <= denominationStock) {
						payout =  payout % denomination.getValue();
					} else {						
						denominationCount = denominationStock ;
						payout = payout - denomination.getValue() * denominationCount;
					}
					denominationInventory.put(denomination,denominationStock-denominationCount);
				}
				payoutDenomination.put(denomination, denominationCount);
			}

			if(payout != 0) {
				denominationInventory.clear();
				denominationInventory.putAll(tempDenominationInventory);
				System.out.println(INSUFFICIENT_FUND+DENOMINATION_UNIT+(odds * betAmount));
			} else {			
				displayPayout(horseNumber,odds * betAmount,payoutDenomination);				
			}

		} else {
			System.out.println(NO_PAYOUT+hourseInventory.get(horseNumber).getName());
		}
	}

	public Boolean isValidHorseNumber(Integer horseNumber) {

		return hourseInventory.containsKey(horseNumber);    
	}

	public void displayPayout(Integer horseNumber,Integer payout, Map<Denomination ,Integer> payoutDenomination ) {

		System.out.println("Payout:"+horseNumber+","+payoutDenomination.keySet().iterator().next().getUnit()+payout);
		System.out.println("Dispensing:");

		for (Entry<Denomination ,Integer> denomination : payoutDenomination.entrySet()) {
			System.out.println(denomination.getKey().toString()+","+denomination.getValue());
		}
	}
}
