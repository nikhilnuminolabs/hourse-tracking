package com.hourse.tracking;

import java.util.Scanner;

import com.hourse.tracking.operation.HorseRacing;

public class ApplicationStartup {
	
	public static void main(String[] args) {
		
		HorseRacing horseRacingOperation = new HorseRacing();
;		
		Scanner scan = new Scanner(System.in);

		String inputString = null;

		horseRacingOperation.displayOutput();
		
		do {

			if(inputString != null && !inputString.trim().isEmpty()) {

				horseRacingOperation.displayOutput();
			}

			inputString = scan.nextLine();

			if(inputString != null && !inputString.trim().isEmpty()) {

				inputString = inputString.trim();
										
				if(inputString.equalsIgnoreCase("q"))
				{
					break;
				}	
				else 
				{
					horseRacingOperation.validateInput(inputString);
				}			
			}

		}while(true);

	}

}
