package com.hourse.tracking.operation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import static com.hourse.tracking.constant.Constant.*;
import com.hourse.tracking.Inventory.Inventory;
import com.hourse.tracking.exception.InvalidCommandException;
import com.hourse.tracking.model.Denomination;
import com.hourse.tracking.model.Horse;



public class HorseRacing {

	private Integer wonHorseNumber = DEFAULT_WON_HORSE_NUMBER;

	private Inventory inventory;

	public HorseRacing() {
		this.inventory = new Inventory();
	}

	public HorseRacing(Inventory inventory) {
		this.inventory = inventory;
	}

	public void payoutDispense(Integer horseNumber, Integer betAmount) {

		Inventory inventories = this.inventory ;

		Map<Denomination ,Integer> payoutDenomination = new TreeMap<Denomination, Integer>();		
		Map<Denomination ,Integer> denominationInventory = new TreeMap<Denomination, Integer>(inventories.getDenominationInventory());

		if(horseNumber == wonHorseNumber) {

			Integer odds = inventories.getHourseInventory().get(horseNumber).getOdds();
			Integer payout = odds * betAmount;


			List<Denomination> denominationList = new ArrayList<Denomination>(inventories.getDenominationInventory().keySet()); 


			Collections.reverse(denominationList);

			for(Denomination denomination : denominationList) {
				Integer denominationStock = inventories.getDenominationInventory().get(denomination);
				if(denominationStock > 0 && payout >= denomination.getValue()) {	
					Integer denominationCount = payout/denomination.getValue();
					if(denominationCount <= denominationStock) {
						payout =  payout % denomination.getValue();
					} else {						
						denominationCount = denominationStock ;
						payout = payout - denomination.getValue() * denominationCount;
					}
					payoutDenomination.put(denomination, denominationCount);
					inventories.getDenominationInventory().put(denomination,denominationStock-denominationCount);

				}
			}

			if(payout != 0) {
				inventories.getDenominationInventory().clear();
				inventories.getDenominationInventory().putAll(denominationInventory);
				System.out.println("Insufficient fund: "+DENOMINATION_UNIT+(odds * betAmount));
			} else {			
				displayPayout(horseNumber,odds * betAmount,payoutDenomination);				
			}

		} else {
			System.out.println("No Payout: "+inventories.getHourseInventory().get(horseNumber).getName());
		}
	}

	public void validateInput(String inputString)  {

		try {
			String[] strArray = inputString.split("\\s+");

			if(strArray != null && strArray.length > 0 && strArray.length <=2) {
				if(strArray.length == 2) {
					if(strArray[0].equalsIgnoreCase("w")) {
						if(strArray[1].matches("\\d+")) {	

							Integer horseNumer = Integer.parseInt(strArray[1]);
							if(horseNumer > 0 && inventory.getHourseInventory().containsKey(horseNumer)) {
								wonHorseNumber =  horseNumer;
							} else {
								errorMessage(INVALID_HORSE_NUMBER, strArray[1]);
							}
						}else {
							throw new InvalidCommandException(inputString);
						}
					} else if(strArray[0].matches("\\d+")) {

						if(strArray[1].matches("\\d+") || strArray[1].matches("\\d+(\\.\\d+)?")) {			

							Integer horseNumer = Integer.parseInt(strArray[0]);
							if(horseNumer > 0 && inventory.getHourseInventory().containsKey(horseNumer)) {
								Integer betAmount = Integer.parseInt(strArray[1]);
								if(strArray[1].matches("\\d+")) {									
									payoutDispense(horseNumer,betAmount);
								} else {
									errorMessage(INVALID_BET,DENOMINATION_UNIT+betAmount.toString());
								}
							} else {
								errorMessage(INVALID_HORSE_NUMBER, horseNumer.toString());
							}
						}else {
							throw new InvalidCommandException(inputString);
						}
					} else {
						throw new InvalidCommandException(inputString);
					}
				}

				if(strArray.length == 1) {
					if(strArray[0].equalsIgnoreCase("r")) {
						restockDenominationInventory();
					}
					else {
						throw new InvalidCommandException(inputString);
					}
				}
			} else {
				throw new InvalidCommandException(inputString);
			}
		} catch(InvalidCommandException ex) {
			errorMessage(DEFAULT_ERROR_MESSAGE, ex.getMessage());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public void displayOutput() {

		System.out.println("Inventory:");
		displayDenominationInventory();
		System.out.println("Horses:");
		displayHorseInventory();

	}

	public void restockDenominationInventory() {

		for (Map.Entry<Denomination ,Integer> denominaionInventory : inventory.getDenominationInventory().entrySet()) {

			denominaionInventory.setValue(MAXSTOCK);
		}
	}

	public void displayDenominationInventory() {

		for (Map.Entry<Denomination ,Integer> denominaionInventory : inventory.getDenominationInventory().entrySet()) {

			System.out.println(denominaionInventory.getKey().toString()+","+denominaionInventory.getValue());
		}
	}

	public void displayHorseInventory() {

		for (Entry<Integer, Horse> horseInventory : inventory.getHourseInventory().entrySet()) {

			System.out.println(horseInventory.getKey()
					+","+horseInventory.getValue().getName()
					+","+horseInventory.getValue().getOdds()
					+","+(horseInventory.getKey()== wonHorseNumber ? "won":"lost"));
		}
	}

	public void displayPayout(Integer horseNumber,Integer payout, Map<Denomination ,Integer> payoutDenomination ) {

		System.out.println("Payout:"+horseNumber+","+payoutDenomination.keySet().iterator().next().getUnit()+payout);
		System.out.println("Dispensing:");

		for (Entry<Denomination ,Integer> denomination : payoutDenomination.entrySet()) {

			System.out.println(denomination.getKey().toString()+","+denomination.getValue());
		}
	}

	public void errorMessage(String message, String input) {

		System.out.println(message +":"+input);
	}
}
