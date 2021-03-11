package com.hourse.tracking.operation;

import static com.hourse.tracking.constant.Constant.*;
import com.hourse.tracking.Inventory.Inventory;
import com.hourse.tracking.exception.InvalidCommandException;

public class HorseRacing {

	private Integer wonHorseNumber = DEFAULT_WON_HORSE_NUMBER;

	private Inventory inventory;

	public HorseRacing() {
		this.inventory = new Inventory();
	}

	public HorseRacing(Inventory inventory) {
		this.inventory = inventory;
	}


	public void validateInput(String inputString)  {

		try {
			String[] strArray = inputString.split("\\s+");

			if(strArray != null && strArray.length > 0 && strArray.length <=2) {
				if(strArray.length == 2) {
					if(strArray[0].equalsIgnoreCase("w")) {
						if(strArray[1].matches("\\d+")) {	

							Integer horseNumer = Integer.parseInt(strArray[1]);
							if(horseNumer > 0 && inventory.isValidHorseNumber(horseNumer)) {
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
							if(horseNumer > 0 && inventory.isValidHorseNumber(horseNumer)) {
								if(strArray[1].matches("\\d+")) {	
									Integer betAmount = Integer.parseInt(strArray[1]);
									inventory.payoutDispense(horseNumer,wonHorseNumber, betAmount);
								} else {								
									errorMessage(INVALID_BET,DENOMINATION_UNIT+strArray[1]);
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
		inventory.displayDenominationInventory();
		System.out.println("Horses:");
		inventory.displayHorseInventory(wonHorseNumber);

	}

	public void restockDenominationInventory() {

		inventory.restockDenominationInventory();
	}

	public void errorMessage(String message, String input) {

		System.out.println(message+input);
	}
}
